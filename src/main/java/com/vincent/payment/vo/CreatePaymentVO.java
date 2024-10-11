package com.vincent.payment.vo;

import lombok.Data;

/**
 * 订单创建出参
 * 1.0
 * */
@Data
public class CreatePaymentVO {

    private String orderId;

    private String postId;

    private String userId;

    private String title;

    private String price;

    private String qrCode;

    private String payUrl;

}
