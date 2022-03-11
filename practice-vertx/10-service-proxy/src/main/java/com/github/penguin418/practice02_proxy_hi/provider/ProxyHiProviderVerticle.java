package com.github.penguin418.practice02_proxy_hi.provider;

import com.github.penguin418.practice02_proxy_hi.common.HiService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ServiceBinder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProxyHiProviderVerticle extends AbstractVerticle {

    private final String HI_SERVICE_ADDRESS = "services.hi";

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        // 등록이 완료되면 다른 곳에서도 사용 가능함.
        log.info("register to address");
        HiService hiServiceImpl = new HiServiceImpl(vertx);
        MessageConsumer<JsonObject> consumer =
        new ServiceBinder(vertx).setAddress(HI_SERVICE_ADDRESS)
                .register(HiService.class, hiServiceImpl);
        log.info("register completed");
        startPromise.complete();
    }
}
