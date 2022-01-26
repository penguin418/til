package com.github.penguin418;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.impl.Deployment;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class StartAndStop extends AbstractVerticle {
    /**
     * deploy 되었을 때 호출되는 메서드
     * @param startPromise
     * @throws Exception
     */
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        log.info("start");
        startPromise.complete();
    }

    /**
     * undeploy 혹은 close 되었을 때 호출되는 메서드
     * @param stopPromise
     */
    @Override
    public void stop(Promise<Void> stopPromise) {
        // stop 이 호출되지 않는다면, start 에서 startPromise 를 complete 하지 않았을 것이다
        log.info("stop");
        stopPromise.complete();
    }

    public static void main(String[] args) {
        // Vert.x 객체 생성
        Vertx vertx = Vertx.vertx();
        // 배포 결과는 배포 아이디
        Future<String> fDeploymentId = vertx.deployVerticle(StartAndStop.class.getName());
        // 주의: startPromise 를 complete 하지 않으면 아래 구문은 실행되지 않는다
        fDeploymentId.onComplete(fResult->{
            if (fResult.succeeded()){
                // 성공 시 성공 로그
                log.info("started");
            }else{
                // 실패 시 실패 로그
                log.error("failed to start");
            }
        }).compose(deploymentId->{
            // 성공 시 처리
            Promise<String> promise = Promise.promise();
            vertx.setTimer(3_000, timerId->{
                // 3초 후 정지
                log.info("time to goodbye");
                try {
                    // 배포한 verticle 을 하나만 정지할 수도 있다.
                    // vertx.undeploy(deploymentId);

                    // 현재 vertx 컨텍스트를 정지
                    // verticle 내부에서는 getVerticle().close() 로 호출할 수 있다
                    vertx.close();
                    promise.complete();
                } catch (Exception e) {
                    e.printStackTrace();
                    promise.fail(e);
                }
            });
            return promise.future();
        });



    }
}
