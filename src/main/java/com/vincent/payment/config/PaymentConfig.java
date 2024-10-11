package com.vincent.payment.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.vincent.payment.entity.VmqPaySetting;
import com.vincent.payment.entity.WxPaySetting;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;
import run.halo.app.plugin.ReactiveSettingFetcher;
import com.vincent.payment.entity.AliPaymentSetting;
/**
 * 支付全局配置
 */
@Configuration
@RequiredArgsConstructor
public class PaymentConfig {
    private final ReactiveSettingFetcher reactiveSettingFetcher;

    @Bean
    public Mono<AlipayClient> alipayClient() {
        return reactiveSettingFetcher.fetch(AliPaymentSetting.GROUP, AliPaymentSetting.class)
            .defaultIfEmpty(new AliPaymentSetting())
            .map(item -> {
                AlipayConfig alipayConfig = new AlipayConfig();
                alipayConfig.setServerUrl("https://openapi.alipay.com/gateway.do");
                alipayConfig.setAppId(item.getAppId());
                alipayConfig.setPrivateKey(item.getPrivateKey());
                alipayConfig.setFormat("json");
                alipayConfig.setCharset("UTF-8");
                alipayConfig.setAlipayPublicKey(item.getAlipayPublicKey());
                alipayConfig.setSignType("RSA2");
                return alipayConfig;
            }).flatMap(alipayConfig -> {
                try {
                    return Mono.just(new DefaultAlipayClient(alipayConfig));
                } catch (AlipayApiException e) {
                    return Mono.error(new RuntimeException(e));
                }
            });
    }

    @Bean
    public Mono<VmqPaySetting> vmqPaySetting() {
        return reactiveSettingFetcher.fetch(VmqPaySetting.GROUP, VmqPaySetting.class)
            .defaultIfEmpty(new VmqPaySetting())
            .flatMap(item -> {
                VmqPaySetting vmqPaySetting = new VmqPaySetting();
                vmqPaySetting.setKey(item.getKey());
                vmqPaySetting.setCreateUrl(item.getCreateUrl());
                vmqPaySetting.setNotifyUrl(item.getNotifyUrl());
                return Mono.just(vmqPaySetting);
            });
    }

    @Bean
    public Mono<WxPaySetting> wxPaySetting() {
        return reactiveSettingFetcher.fetch(WxPaySetting.GROUP, WxPaySetting.class)
            .defaultIfEmpty(new WxPaySetting())
            .flatMap(item -> {
                WxPaySetting wxPaySetting = new WxPaySetting();
                wxPaySetting.setMchId(item.getMchId());
                wxPaySetting.setAppId(item.getAppId());
                wxPaySetting.setKey(item.getKey());
                wxPaySetting.setKeyPath(item.getKeyPath());
                return Mono.just(wxPaySetting);
            });
    }
}
