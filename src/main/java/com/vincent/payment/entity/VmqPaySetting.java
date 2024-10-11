package com.vincent.payment.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * V免签支付配置信息
 * 与settings.yaml中的字段匹配
 * */
@Data
public class VmqPaySetting {
    public static final String GROUP = "Vmqpay_configuration";
    @Schema(title = "密钥")
    private String key;
    @Schema(title = "创建地址")
    private String createUrl;
    @Schema(title = "回调地址")
    private String notifyUrl;
}
