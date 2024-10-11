package com.vincent.payment.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import run.halo.app.extension.AbstractExtension;
import run.halo.app.extension.GVK;
import java.util.Date;

/**
 * 订单管理模型
 * 1.0
 * */
@Data
@EqualsAndHashCode(callSuper = true)
@GVK(kind = "Payment", group = "payment.plugin.halo.run",
version = "v1alpha1", singular = "payment", plural = "payments")
public class Payment extends AbstractExtension {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private PaymentSpec spec;

    @Data
    public static class PaymentSpec {

        /**
         * 订单编号
         * */
        private String orderId;

        /**
         * 支付方式
         * */
        private String PayType;

        /**
         * 订单状态
         * */
        private Integer status;

        /**
         * 文章id
         * */
        private String postId;

        /**
         * 商品名称
         * */
        private String productName;

        /**
         * 商品类型
         * */
        private String productType;

        /**
         * 购买用户id
         * */
        private String userId;

        /**
         * 价格
         * */
        private String price;

    }
}
