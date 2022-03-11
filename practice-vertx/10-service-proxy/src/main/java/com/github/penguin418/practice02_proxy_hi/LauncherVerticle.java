package com.github.penguin418.practice02_proxy_hi;

import com.github.penguin418.practice02_proxy_hi.common.HiService;
import com.github.penguin418.practice02_proxy_hi.consumer.ProxyHiVerticle;
import com.github.penguin418.practice02_proxy_hi.provider.ProxyHiProviderVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ServiceBinder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LauncherVerticle extends AbstractVerticle {
    public static void main(String[] args) {
        DeploymentOptions dOptions = new DeploymentOptions();
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(ProxyHiProviderVerticle.class.getName(),dOptions);
        vertx.deployVerticle(ProxyHiVerticle.class.getName(),dOptions);
    }
}
