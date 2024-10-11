package com.vincent.payment.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 支付宝当面付配置信息
 * 与settings.yaml中的字段匹配
 * */
@Data
public class AliPaymentSetting {

    public static final String GROUP = "Alipay_configuration";
    @Schema(title = "应用ID")
    private String appId;
    @Schema(title = "应用私钥")
    private String privateKey;
    @Schema(title = "支付宝公钥")
    private String alipayPublicKey;
    @Schema(title = "回调地址")
    private String notifyUrl;
}
