package com.vincent.payment.handler;

import cn.hutool.core.util.StrUtil;
import com.vincent.payment.PaymentHtml;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import run.halo.app.theme.ReactivePostContentHandler;
import com.vincent.payment.service.PaymentService;
import java.util.Collection;
import java.util.Objects;

/**
 * 文章内容处理器
 * 1.0
 * */

@RequiredArgsConstructor
@Component
public class PayReadPostHandler implements ReactivePostContentHandler {
    private final PaymentService paymentService;
    @Override
    public Mono<PostContentContext> handle(@NotNull PostContentContext postContent) {
        return ReactiveSecurityContextHolder.getContext()
            .flatMap(securityContext -> {
                String username;
                boolean isAdmin = false;
                Authentication authentication = securityContext.getAuthentication();
                //未登录时 authentication.getPrincipal() 返回字符串,登录用户返回User对象
                if (!(authentication.getPrincipal() instanceof String)) {
                    username = authentication.getName();
                    // 获取权限列表
                    Collection<? extends GrantedAuthority>
                        authorities = authentication.getAuthorities();
                    // 遍历权限列表判断是否是超级管理员
                    for (GrantedAuthority authority : authorities) {
                        if (Objects.equals(authority.getAuthority(), "ROLE_super-role")) {
                            isAdmin = true;
                        }
                    }
                } else {
                    username = null;
                }
                //获取文章唯一标识
                String postId = postContent.getPost().getMetadata().getName();
                //获取收费模式
                String paymentMode = postContent.getPost().getMetadata().getAnnotations().get("paymentMode");
                //获取价格
                String price = postContent.getPost().getMetadata().getAnnotations().get("price");
                // 拿到资源标题+地址
                String resourcesTitle = postContent.getPost().getMetadata().getAnnotations().get("resourcesTitle");
                String resourcesAddress = postContent.getPost().getMetadata().getAnnotations().get("resourcesAddress");
                String initEL = StrUtil.format(PaymentHtml.INIT_PAYMENT_HTML,
                    postId,
                    username,
                    Objects.equals(paymentMode, "resources") ? resourcesTitle : postContent.getPost().getSpec().getTitle(),
                    price
                );
                //是否主题适配
                boolean isAdapter = Boolean.parseBoolean(
                    postContent.getPost().getMetadata().getAnnotations().get("isAdapter"));

                if (Objects.equals(paymentMode, "post")) {
                    if (isAdmin) {
                        return Mono.just(postContent);
                    }
                    if (StrUtil.isNotEmpty(username)){
                        //根据userid + 文章id 查询订单表
                        return paymentService.queryPayment(postId,username,1)
                            .flatMap(payments -> Mono.just(postContent)).switchIfEmpty(Flux.defer(() -> {
                                //字符串拼接
                                String html = StrUtil.format(PaymentHtml.GO_PAYMENT_HTML,
                                    postId,
                                    username,
                                    postContent.getPost().getSpec().getTitle(),
                                    price
                                );
                                postContent.setRaw(html+initEL);
                                postContent.setContent(html + initEL);
                                return Mono.just(postContent);
                            })).shareNext();
                    }
                    String html = StrUtil.format(PaymentHtml.GO_PAYMENT_HTML,
                        postId,
                        username,
                        postContent.getPost().getSpec().getTitle(),
                        price
                    );
                    postContent.setRaw(html+initEL);
                    postContent.setContent(html+initEL);
                    return Mono.just(postContent);

                } else if (Objects.equals(paymentMode, "resources")) {
                    if (isAdmin) {
                        String resourceCardEL = StrUtil.format(PaymentHtml.RESOURCE_CARD_HTML,
                            postId,
                            username,
                            resourcesTitle,
                            price,
                            resourcesAddress
                        );
                        postContent.setContent(postContent.getContent() + resourceCardEL);
                        return Mono.just(postContent);
                    }
                    if (StrUtil.isNotEmpty(username)){
                        //根据userid + 文章id 查询订单表
                        return paymentService.queryPayment(postId,username,1)
                            .flatMap(payments -> {
                                if (!isAdapter) {
                                    // 插件适配
                                    String resourceCardEL = StrUtil.format(PaymentHtml.RESOURCE_CARD_HTML,
                                        postId,
                                        username,
                                        resourcesTitle,
                                        price,
                                        resourcesAddress
                                    );
                                    postContent.setRaw(postContent.getRaw() + resourceCardEL+initEL);
                                    postContent.setContent(postContent.getContent() + resourceCardEL+initEL);
                                }
                                return Mono.just(postContent);

                            }).switchIfEmpty(Flux.defer(() -> {
                                if(!isAdapter) {
                                    // 插件适配
                                    String resourceCardEL = StrUtil.format(PaymentHtml.RESOURCE_CARD_HTML,
                                        postId,
                                        username,
                                        resourcesTitle,
                                        price,
                                        null
                                    );
                                    postContent.setRaw(postContent.getRaw() + resourceCardEL+  initEL);
                                    postContent.setContent(postContent.getContent()  + resourceCardEL+  initEL);
                                }
                                //删除资源元数据
                                postContent.getPost().getMetadata().getAnnotations().remove("resourcesAddress");
                                return Mono.just(postContent);
                            }))
                            .shareNext();
                    }
                    String resourceCardEL = StrUtil.format(PaymentHtml.RESOURCE_CARD_HTML,
                        postId,
                        username,
                        resourcesTitle,
                        price,
                        null
                    );
                    postContent.setRaw(postContent.getRaw() + resourceCardEL+ initEL);
                    postContent.setContent(postContent.getContent() + resourceCardEL+ initEL);
                    //删除资源元数据
                    postContent.getPost().getMetadata().getAnnotations().remove("resourcesAddress");
                    return Mono.just(postContent);
                }
                return Mono.just(postContent);
            });
    }
}
