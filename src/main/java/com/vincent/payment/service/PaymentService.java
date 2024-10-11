package com.vincent.payment.service;

import com.vincent.payment.dto.CreatePaymentDto;
import com.vincent.payment.entity.Payment;
import com.vincent.payment.utils.Result;
import com.vincent.payment.vo.CreatePaymentVO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 支付相关接口
 * 1.0
 * */

public interface PaymentService {

    /**
     * 订单创建
     *
     * @param dto 订单信息
     */
    Mono<Result<CreatePaymentVO>> tradePreCreate(CreatePaymentDto dto);

    /**
     * 订单查询
     *
     * @param order 订单号
     */
    Mono<Result<String>> queryOrder(String order,String payType);

    /**
     * 订单表查询
     *
     * @param postId 文章唯一标识
     * @param userId 用户名
     * @param status 订单状态
     */
    Flux<Payment> queryPayment(String postId,String userId, Integer status);

    /**
     * 订单表查询
     *
     * @param orderId 订单号
     */
    Flux<Payment> queryPayment(String orderId);
}
