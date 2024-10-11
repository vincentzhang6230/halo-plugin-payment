package com.vincent.payment.handler;

import org.springframework.stereotype.Component;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.processor.element.IElementModelStructureHandler;
import reactor.core.publisher.Mono;
import run.halo.app.theme.dialect.TemplateHeadProcessor;

/**
 * 向 HTML 头添加资源文件
 * 1.0
 * */

@Component
public class PayTemplateHeadProcessor implements TemplateHeadProcessor {

    @Override
    public Mono<Void> process(ITemplateContext iTemplateContext, IModel iModel,
        IElementModelStructureHandler iElementModelStructureHandler) {

        iModel.add(iTemplateContext.getModelFactory().createText("<script src=\"/plugins/Payment/assets/static/payment-lit.iife.js?version=1.0.0\"></script>"));
        return Mono.empty();
    }
}
