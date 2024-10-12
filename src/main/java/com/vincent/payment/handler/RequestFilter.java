package com.vincent.payment.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;
import run.halo.app.security.AdditionalWebFilter;
import java.nio.charset.StandardCharsets;

/**
 * 配置拦截器,防止公开接口读取付费资源信息
 * 1.0
 * */
@Component
@Slf4j
public class RequestFilter implements AdditionalWebFilter {
    final ServerWebExchangeMatcher
        requiresMatcher = ServerWebExchangeMatchers.pathMatchers(HttpMethod.GET, "/apis/api.content.halo.run/v1alpha1/posts");

    @Override
    @NonNull
    public Mono<Void> filter(@NonNull ServerWebExchange exchange,@NonNull WebFilterChain chain) {
        return this.requiresMatcher.matches(exchange)
            .filter(ServerWebExchangeMatcher.MatchResult::isMatch)
            .flatMap(matchResult -> {
                ServerHttpResponse originalResponse = exchange.getResponse();

                // 装饰原始ServerHttpResponse
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    @Override
                    @NonNull
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                            Mono<? extends DataBuffer> fluxBody = (Mono<? extends DataBuffer>) body;
                            return super.writeWith(fluxBody.handle((dataBuffer, sink) -> {
                                //将请求响应转换为字符串
                                String content = dataBuffer.toString(StandardCharsets.UTF_8);
                                ObjectMapper objectMapper = new ObjectMapper();
                                //对响应进行重写,当存在paymentMode时,删除resourcesAddress值
                                try {
                                    JsonNode rootNode = objectMapper.readTree(content);
                                    JsonNode itemsNode = rootNode.path("items");
                                    for (JsonNode itemNode : itemsNode) {
                                        JsonNode metadataNode = itemNode.path("metadata");
                                        JsonNode annotationsNode = metadataNode.path("annotations");

                                        if (annotationsNode.has("paymentMode")) {
                                            ((ObjectNode) annotationsNode).remove("resourcesAddress");
                                        }
                                    }
                                    String modifiedJsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);

                                    sink.next(originalResponse.bufferFactory()
                                        .wrap(modifiedJsonStr.getBytes(StandardCharsets.UTF_8)));
                                } catch (JsonProcessingException e) {
                                    sink.error(new RuntimeException(e));
                                }
                            }));
                    }
                };
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            });
    }
}
