package com.vincent.payment.listener;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincent.payment.config.PaymentConfig;
import com.vincent.payment.entity.VmqPaySetting;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import run.halo.app.plugin.PluginConfigUpdatedEvent;
import com.vincent.payment.entity.AliPaymentSetting;

@Component
@RequiredArgsConstructor
public class PaymentListener {
    private final PaymentConfig paymentConfig;

    ObjectMapper objectMapper = new ObjectMapper();

    @EventListener
    public void onConfigUpdated(PluginConfigUpdatedEvent event) {
        //新的数据中多了个 slots{} 字段,配置objectMapper忽略未知字段
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);

        if (event.getNewConfig().containsKey(AliPaymentSetting.GROUP)) {
             paymentConfig.alipayClient().flatMap(alipayClient -> {

                 AliPaymentSetting aliPaymentSetting = objectMapper.convertValue(event.getNewConfig().get(AliPaymentSetting.GROUP), AliPaymentSetting.class);

                AlipayConfig alipayConfig = new AlipayConfig();
                alipayConfig.setServerUrl("https://openapi.alipay.com/gateway.do");
                alipayConfig.setAppId(aliPaymentSetting.getAppId());
                alipayConfig.setPrivateKey(aliPaymentSetting.getPrivateKey());
                alipayConfig.setFormat("json");
                alipayConfig.setCharset("UTF-8");
                alipayConfig.setAlipayPublicKey(aliPaymentSetting.getAlipayPublicKey());
                alipayConfig.setSignType("RSA2");
                try {
                    alipayClient = new DefaultAlipayClient(alipayConfig);
                    return Mono.just(alipayClient);
                } catch (AlipayApiException e) {
                    return Mono.error(new RuntimeException(e));
                }
            }).subscribe();
             //因为当前Mono未被引用(可以这样说吧),flatMap()会被挂起,因此需要使用subscribe()来立即处理
        }
        if (event.getNewConfig().containsKey(VmqPaySetting.GROUP)) {
            paymentConfig.vmqPaySetting().flatMap(vmqPaySetting -> {
                vmqPaySetting = objectMapper.convertValue(event.getNewConfig().get(VmqPaySetting.GROUP), VmqPaySetting.class);
                return Mono.just(vmqPaySetting);
            }).subscribe();
        }
    }
}

