package com.vincent.payment;

import org.springframework.stereotype.Component;
import run.halo.app.extension.Scheme;
import run.halo.app.extension.SchemeManager;
import run.halo.app.extension.index.IndexSpec;
import run.halo.app.plugin.BasePlugin;
import run.halo.app.plugin.PluginContext;
import com.vincent.payment.entity.Payment;

import static run.halo.app.extension.index.IndexAttributeFactory.simpleAttribute;

/**
 * <p>Plugin main class to manage the lifecycle of the plugin.</p>
 * <p>This class must be public and have a public constructor.</p>
 * <p>Only one main class extending {@link BasePlugin} is allowed per plugin.</p>
 *
 * @author guqing
 * @since 1.0.0
 */
@Component
public class StarterPlugin extends BasePlugin {
    private final SchemeManager schemaManager;

    public StarterPlugin(PluginContext pluginContext,SchemeManager schemaManager) {
        super(pluginContext);
        this.schemaManager = schemaManager;
    }

    @Override
    public void start() {
        schemaManager.register(Payment.class, indexSpecs -> {
            //为payment表添加索引
            indexSpecs.add(new IndexSpec()
                .setName("spec.postId")
                .setIndexFunc(simpleAttribute(Payment.class,payment -> payment.getSpec().getPostId())));
            indexSpecs.add(new IndexSpec()
                .setName("spec.userId")
                .setIndexFunc(simpleAttribute(Payment.class,payment -> payment.getSpec().getUserId())));
            indexSpecs.add(new IndexSpec()
                .setName("spec.orderId")
                .setIndexFunc(simpleAttribute(Payment.class,payment -> payment.getSpec().getOrderId())));
            indexSpecs.add(new IndexSpec()
                .setName("spec.status")
                .setIndexFunc(simpleAttribute(Payment.class,payment -> String.valueOf(
                    payment.getSpec().getStatus()))));
        });
    }

    @Override
    public void stop() {
        Scheme paymentScheme = schemaManager.get(Payment.class);
        schemaManager.unregister(paymentScheme);
    }
}
