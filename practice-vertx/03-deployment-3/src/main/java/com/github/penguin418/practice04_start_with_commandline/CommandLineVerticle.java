package com.github.penguin418.practice04_start_with_commandline;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommandLineVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        log.info("start");
        startPromise.complete();
    }

    @Override
    public void stop(Promise<Void> stopPromise) throws Exception {
        log.info("stop");
        // 1초 정도의 시간을 사용 가능
        stopPromise.complete();
    }

    public static void main(String[] args) {
        Vertx vertxOnMainThread = Vertx.vertx();
        vertxOnMainThread.deployVerticle(new CommandLineVerticle()).onSuccess(id -> {
            log.info("succeed to deploy. id=" + id);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                log.info("main stop");
                // 이러면 vertx 내부의 close가 호출된다
                vertxOnMainThread.close();
//                // 아래 방법도 동작한다. 대신 위에서 close 하지 말것
//                vertxOnMainThread.undeploy(id).onSuccess((void1) -> {
//                    log.info("succeed to undeploy. id=" + id);
//                }).onFailure(e -> {
//                    log.error("failed to undeploy.");
//                });
            }));
        }).onFailure(e -> {
            log.info("failed to deploy", e);
        });
        // build.gradle 참고
    }
}