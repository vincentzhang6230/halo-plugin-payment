package com.vincent.payment;

/**
 * HTML 片段
 * 1.0
 * */

public class PaymentHtml {
    public static final String GO_LOGIN_HTML = "<payment-content-card payType=\"alipay\" postId=\"{}\" userId=\"{}\" resourceTitle=\"{}\" amount=\"{}\" ></payment-contentCard>";

    public static final String GO_PAYMENT_HTML = "<payment-content-card payType=\"alipay\" postId=\"{}\" userId=\"{}\" resourceTitle=\"{}\" amount=\"{}\" ></payment-contentCard>";

    public static final String INIT_PAYMENT_HTML = "<payment-init payType=\"alipay\" postId=\"{}\" userId=\"{}\" resourceTitle=\"{}\" amount=\"{}\" ></payment-init>";
    public static final String RESOURCE_CARD_HTML = "<payment-resource-card payType=\"alipay\" postId=\"{}\" userId=\"{}\" resourceTitle=\"{}\" amount=\"{}\" resourcesAddress=\"{}\" ></payment-resource-card>";
}