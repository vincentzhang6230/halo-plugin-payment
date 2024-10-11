package com.vincent.payment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincent.payment.config.PaymentConfig;
import com.vincent.payment.dto.CreatePaymentDto;
import com.vincent.payment.entity.Payment;
import com.vincent.payment.entity.VmqEntity;
import com.vincent.payment.utils.PaysEnum;
import com.vincent.payment.utils.QrCodeUtils;
import com.vincent.payment.utils.Result;
import com.vincent.payment.vo.CreatePaymentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import run.halo.app.extension.ListOptions;
import run.halo.app.extension.Metadata;
import run.halo.app.extension.ReactiveExtensionClient;
import run.halo.app.extension.router.selector.FieldSelector;
import com.vincent.payment.service.PaymentService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static run.halo.app.extension.index.query.QueryFactory.and;
import static run.halo.app.extension.index.query.QueryFactory.equal;

/**
 * 支付相关接口实现
 * 1.0
 * */

@Component
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final ReactiveExtensionClient client;
    private final PaymentConfig paymentConfig;

    @Override
    public Mono<Result<CreatePaymentVO>> tradePreCreate(CreatePaymentDto dto) {
        return ReactiveSecurityContextHolder.getContext()
            .flatMap(securityContext ->{

                Payment payment = new Payment();
                //自动生成
                payment.setMetadata(new Metadata());
                //自动生成name
                payment.getMetadata().setGenerateName("zfb-");

                payment.setSpec(new Payment.PaymentSpec());
                //设置用户名称
                payment.getSpec().setUserId(dto.getUserId());
                //设置购买的商品类型
                payment.getSpec().setProductType(dto.getProductType());

                //设置价格
                payment.getSpec().setPrice(dto.getPrice());
                payment.getSpec().setProductName(dto.getTitle());
                payment.getSpec().setStatus(0);
                //根据当前时间生成16位订单号
                Date date = DateUtil.date();
                String format = DateUtil.format(date, "yyyyMMddHHmmssSSS");

                payment.getSpec().setOrderId(format);
                payment.getSpec().setPostId(dto.getPostId());
                // 获取支付方式
                payment.getSpec().setPayType(dto.getPayType());

                return Mono.just(payment);

            }).flatMap(payment -> switch (PaysEnum.getByValue(dto.getPayType())) {
                case ALIPAY -> aliPay(payment);
                case VMQ_WXPAY, VMQ_ALIPAY -> vmqPay(payment);
                case WXPAY -> wxPay(payment);
            });
    }

    @Override
    public Mono<Result<String>> queryOrder(String order,String payType) {
        return switch (PaysEnum.getByValue(payType)) {
            case ALIPAY -> aliPayOrder(order);
            case VMQ_WXPAY, VMQ_ALIPAY -> vmqOrder(order);
            case WXPAY -> null;
        };
    }

    @Override
    public Flux<Payment> queryPayment(String postId, String userId,Integer status) {
        var listOptions = new ListOptions();
        var query =and(equal("spec.postId", postId),equal("spec.userId", userId),equal("spec.status",
            String.valueOf(status)));
        listOptions.setFieldSelector(FieldSelector.of(query));
        return client.listAll(Payment.class,listOptions, Sort.unsorted());
    }

    @Override
    public Flux<Payment> queryPayment(String orderId) {
        var listOptions = new ListOptions();
        var query = equal("spec.orderId", orderId);
        listOptions.setFieldSelector(FieldSelector.of(query));
        return client.listAll(Payment.class,listOptions,Sort.unsorted());
    }

    private Mono<Result<CreatePaymentVO>> aliPay(Payment payment) {
        return paymentConfig.alipayClient().flatMap(alipayClient -> {
            //创建 Request并设置Request参数
            AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
            AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();

            model.setTotalAmount(payment.getSpec().getPrice());
            model.setSubject(payment.getSpec().getProductName());
            request.setBizModel(model);
            model.setOutTradeNo(payment.getSpec().getOrderId());

            try {
                AlipayTradePrecreateResponse response;
                response = alipayClient.execute(request);
                if (response.isSuccess()) {
                    //创建一条订单信息
                    client.create(payment).subscribe();
                    CreatePaymentVO vo = new CreatePaymentVO();
                    BeanUtil.copyProperties(payment.getSpec(),vo);
                    //将二维码地址转换为base64图片
                    vo.setQrCode(QrCodeUtils.crateQRCode(response.getQrCode()));
                    return Mono.just(Result.ok(vo));
                }else {
                    return Mono.just(Result.error());
                }
            } catch (AlipayApiException e) {
                return Mono.just(Result.error());
            }
        });
    }

    private Mono<Result<CreatePaymentVO>> vmqPay(Payment payment) {
        return paymentConfig.vmqPaySetting().flatMap(vmqPaySetting -> {
            Payment.PaymentSpec spec = payment.getSpec();
            int type = 2;
            if (Objects.equals(spec.getPayType(), PaysEnum.VMQ_WXPAY.getValue())) {
                type = 1;
            } else if (Objects.equals(spec.getProductName(), PaysEnum.VMQ_ALIPAY.getValue())) {
                type = 2;
            }
            String notifyUrl = vmqPaySetting.getNotifyUrl() + "/mqpay/notifyUrl";
            String returnUrl = vmqPaySetting.getNotifyUrl() + "/mqpay/returnUrl";

            String jsSign = spec.getOrderId() + spec.getProductName() + type + spec.getPrice() + vmqPaySetting.getKey();
            String encodeStr = DigestUtils.md5DigestAsHex(jsSign.getBytes());
            CreatePaymentVO vo = new CreatePaymentVO();
            BeanUtil.copyProperties(payment.getSpec(),vo);
            String url = vmqPaySetting.getCreateUrl() + "/createOrder?payId=" + spec.getOrderId() + "&type=" + type + "&price=" + spec.getPrice() + "&notifyUrl=" + notifyUrl + "&returnUrl=" + returnUrl + "&sign=" + encodeStr + "&param=" + spec.getProductName();
            vo.setPayUrl(url);

            WebClient webClient = WebClient.create();
            return webClient.get().uri(url).retrieve().bodyToMono(String.class).flatMap(entity -> {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    Result<VmqEntity> result = objectMapper.readValue(entity,
                        new TypeReference<>() {
                        });
                    if (result.getCode() == 1) {
                        VmqEntity vmqEntity = result.getData();
                        vo.setQrCode(QrCodeUtils.crateQRCode(vmqEntity.getPayUrl()));
                        // 将订单号改为vmq生成的
                        vo.setOrderId(vmqEntity.getOrderId());
                        client.create(payment).subscribe();
                        return Mono.just(Result.ok(vo));
                    } else {
                        return Mono.just(Result.error());
                    }
                } catch (JsonProcessingException e) {
                    return Mono.error(new RuntimeException(e));
                }

            });
        });
    }

    private Mono<Result<CreatePaymentVO>> wxPay(Payment payment) {
        return Mono.just(Result.ok());
    }

    private Mono<Result<String>> aliPayOrder(String order) {
        // 构造请求参数以调用接口
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();

        // 设置订单支付时传入的商户订单号
        model.setOutTradeNo(order);

        // 设置查询选项
        List<String> queryOptions = new ArrayList<>();
        queryOptions.add("trade_settle_info");
        model.setQueryOptions(queryOptions);

        request.setBizModel(model);
        //调用订单查询接口查询订单状态
        return paymentConfig.alipayClient().flatMap(alipayClient -> {
            AlipayTradeQueryResponse response;
            try {
                response = alipayClient.execute(request);
                if (response.isSuccess()) {
                    if (Objects.equals(response.getTradeStatus(), "TRADE_SUCCESS")){
                        // 更新订单信息
                        return queryPayment(order).flatMap(payment -> {
                            if (ObjUtil.isNotEmpty(payment)) {
                                payment.getSpec().setStatus(1);
                                client.update(payment).subscribe();
                            }
                            return Mono.just(Result.ok(response.getTradeStatus()));
                        }).shareNext();
                    }
                    return Mono.just(Result.ok(response.getTradeStatus()));
                }else {
                    return Mono.just(Result.ok(response.getMsg()));
                }
            } catch (AlipayApiException e) {
                return Mono.error(e);
            }
        });
    }

    private Mono<Result<String>> vmqOrder(String order) {
        return paymentConfig.vmqPaySetting().flatMap(vmqPaySetting -> {
            String url = vmqPaySetting.getCreateUrl() + "/checkOrder?orderId=" + order;
            WebClient webClient = WebClient.create();
            return webClient.get().uri(url).retrieve().bodyToMono(String.class).flatMap(entity -> {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    Result<VmqEntity> result = objectMapper.readValue(entity,
                        new TypeReference<>() {
                        });
                    if (result.getCode() == 1) {
                        // 更新订单信息
                        return queryPayment(order).flatMap(payment -> {
                            if (ObjUtil.isNotEmpty(payment)) {
                                payment.getSpec().setStatus(1);
                                client.update(payment).subscribe();
                            }
                            return Mono.just(Result.ok("Success"));
                        }).shareNext();
                    } else {
                        return Mono.just(Result.ok(result.getMsg()));
                    }
                } catch (JsonProcessingException e) {
                    return Mono.error(new RuntimeException(e));
                }

            });
        });
    }
}
