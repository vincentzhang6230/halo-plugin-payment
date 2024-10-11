package com.vincent.payment.dto;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 订单创建入参
 * 1.0
 * */
@Data
public class CreatePaymentDto {

    @Schema(title = "支付方式",requiredMode = Schema.RequiredMode.REQUIRED)
    private String payType;

    @Schema(title = "商品类型",requiredMode = Schema.RequiredMode.REQUIRED)
    private String productType;

    @Schema(title = "文章id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String postId;

    @Schema(title = "用户id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userId;

    @Schema(title = "标题", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @Schema(title = "金额", requiredMode = Schema.RequiredMode.REQUIRED)
    private String price;
}
