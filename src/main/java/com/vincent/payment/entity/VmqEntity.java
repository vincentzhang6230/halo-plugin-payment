package com.vincent.payment.entity;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class VmqEntity {
    private String payId;

    private String orderId;

    private Integer payType;

    private BigDecimal price;

    private BigDecimal reallyPrice;

    private String payUrl;

    private Integer isAuto;

    private Integer state;

    private Integer timeOut;

    private Long date;

}
