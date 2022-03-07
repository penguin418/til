package com.github.penguin418.practice04_codec;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;

import static com.github.penguin418.practice04_codec.LaunchWithConfig.SUBSCRIBER_ADDRESS1;


@Slf4j
public class SubscriberVerticle extends AbstractVerticle {
    private ExamplePOJO1Codec examplePOJO1Codec = new ExamplePOJO1Codec();
    private ExamplePOJO2Codec examplePOJO2Codec = new ExamplePOJO2Codec();

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        EventBus eventBus = vertx.eventBus();

        // 코덱은 한번만 등록. publisher 에서 등록했으므로 다시 할 필요없음.
        // 다시 등록하면 이름이 중복되서 찾지 못함.
        eventBus.consumer(SUBSCRIBER_ADDRESS1, event -> {
            String codec = event.headers().get("codec");
            log.info("codec is " + codec);
            ExamplePOJO1 data = (ExamplePOJO1) event.body();
            log.info("received message: " + data);
        });
        startPromise.complete();
    }
}
