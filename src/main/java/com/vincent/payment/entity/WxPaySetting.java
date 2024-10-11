package com.vincent.payment.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 微信支付配置信息
 * 与settings.yaml中的字段匹配
 * */
@Data
public class WxPaySetting {

    public static final String GROUP = "Wxpay_configuration";
    @Schema(title = "应用ID")
    private String mchId;
    @Schema(title = "应用ID")
    private String appId;
    @Schema(title = "APIv3密钥")
    private String key;
    @Schema(title = "API证书串")
    private String keyPath;
}
