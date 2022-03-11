package com.github.penguin418.practice02_proxy_hi.consumer;

import com.github.penguin418.practice02_proxy_hi.common.HiService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ServiceBinder;
import io.vertx.serviceproxy.ServiceProxyBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProxyHiVerticle extends AbstractVerticle {

    private final String HI_SERVICE_ADDRESS = "services.hi";

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        log.info("create proxy");
        // 사용하는 쪽의 프록시 생성
        HiService hiServiceProxy = HiService.createProxy(vertx, HI_SERVICE_ADDRESS);
        log.info("elice hi to HelloService");
        hiServiceProxy.hi_1("elice", msg->{
            log.info("HiService responded as " + msg.result());
        });

        log.info("bob hi to HiService_1");
        hiServiceProxy.hi_2("bob", msg->{
            log.info("HiService_1 responded as " + msg.result().toJson());
        });

        // 다시 선언해도, 등록된 곳에 연결됨을 알 수 있다
        log.info("bob hi to HiService_2");
        HiService.createProxy(vertx, HI_SERVICE_ADDRESS).hi_2("bob", msg->{
            log.info("HiService_2 responded as " + msg.result().toJson());
        });

        startPromise.complete();
    }

    public static void main(String[] args) {
        DeploymentOptions dOptions = new DeploymentOptions();
        Vertx.vertx().deployVerticle(ProxyHiVerticle.class.getName(),dOptions);
    }
}
