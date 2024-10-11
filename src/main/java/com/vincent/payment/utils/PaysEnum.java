package com.vincent.payment.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付 enum
 *
 */

@Getter
@AllArgsConstructor
public enum PaysEnum {
    VMQ_ALIPAY("vmq_alipay"),
    VMQ_WXPAY("vmq_wxpay"),
    ALIPAY("alipay"),
    WXPAY("wxpay"),
    ;
    private final String value;

    public static PaysEnum getByValue(String value) {
        for (PaysEnum paysEnum : values()) {
            if (paysEnum.value.equals(value)) {
                return paysEnum;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
