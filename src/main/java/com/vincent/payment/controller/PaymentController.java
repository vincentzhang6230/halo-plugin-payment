package com.vincent.payment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import run.halo.app.plugin.ApiVersion;
import com.vincent.payment.dto.CreatePaymentDto;
import com.vincent.payment.entity.Payment;
import com.vincent.payment.service.PaymentService;
import com.vincent.payment.utils.Result;
import com.vincent.payment.vo.CreatePaymentVO;

/**
 * 支付相关接口
 */

@ApiVersion("payment.plugin.halo.run/v1alpha1")
@RequestMapping("plugins/pay")
@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("trade-precreate")
    public Mono<Result<CreatePaymentVO>> tradePreCreate(@RequestBody CreatePaymentDto dto) {
        return paymentService.tradePreCreate(dto);
    }

    @GetMapping("query-order")
    public Mono<Result<String>> queryOrder(@RequestParam(name = "order") String order, @RequestParam(name = "payType") String payType) {
        return paymentService.queryOrder(order,payType);
    }

    @GetMapping("order")
    public Mono<Payment> test(@RequestParam(name = "order") String order) {
        return paymentService.queryPayment(order).shareNext();
    }
}
