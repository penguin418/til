package com.github.penguin418;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.bridge.BridgeOptions;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeEvent;
import io.vertx.ext.web.handler.sockjs.SockJSBridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.logging.SocketHandler;

@Slf4j
public class SockJSWebserverVerticle extends AbstractVerticle {

    public static final String CUSTOM_WEBROOT = "sockjs-webroot";

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        Router router = Router.router(vertx);

        StaticHandler.create(CUSTOM_WEBROOT).setCachingEnabled(false);
        router.get("/").handler(ctx -> {
            ctx.response().sendFile(CUSTOM_WEBROOT + "/index.html");
        });

        // 이벤트 버스 등록
        SockJSHandler socketHandler = SockJSHandler.create(vertx);
        SockJSBridgeOptions options = new SockJSBridgeOptions();
        registerSocketEvent1(options);
        router.mountSubRouter("/eventbus", socketHandler.bridge(options));

        vertx.createHttpServer().requestHandler(router).listen(8085);
    }

    // 원하는 만큼 이벤트 추가
    public void registerSocketEvent1(SockJSBridgeOptions options) {
        final String EVENT1_INCOMING = "msg.to.server";
        final String EVENT1_OUTGOING = "msg.to.client";
        options.addInboundPermitted(new PermittedOptions().setAddressRegex(EVENT1_INCOMING))
                .addOutboundPermitted(new PermittedOptions().setAddressRegex(EVENT1_OUTGOING));

        vertx.eventBus().consumer(EVENT1_INCOMING).handler(event -> {
            assert Objects.equals(event.address(), EVENT1_INCOMING);
            final String received = event.body().toString();
            log.info("incoming msg: " + received);
            vertx.eventBus().publish(EVENT1_OUTGOING, received);
        });
    }

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new SockJSWebserverVerticle());
    }
}
