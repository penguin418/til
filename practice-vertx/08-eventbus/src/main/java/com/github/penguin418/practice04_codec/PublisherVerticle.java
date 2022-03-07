package com.github.penguin418.practice04_codec;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

import static com.github.penguin418.practice04_codec.LaunchWithConfig.*;

@Slf4j
public class PublisherVerticle extends AbstractVerticle {
    private ExamplePOJO1Codec examplePOJO1Codec = new ExamplePOJO1Codec();
    private ExamplePOJO2Codec examplePOJO2Codec = new ExamplePOJO2Codec();

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        EventBus eventBus = vertx.eventBus();
        // 코덱 등록
        eventBus.registerDefaultCodec(ExamplePOJO1.class, examplePOJO1Codec);
        eventBus.registerDefaultCodec(ExamplePOJO2.class, examplePOJO2Codec);
        // 1초마다 이벤트 발신
        AtomicInteger messageId = new AtomicInteger(0);
        vertx.setPeriodic(1000, timerId -> {
            ExamplePOJO1 data = new ExamplePOJO1(messageId.incrementAndGet(), "pojo1");
            DeliveryOptions dOptions = new DeliveryOptions()
                    .addHeader("codec", examplePOJO1Codec.name())
                    .setCodecName(examplePOJO1Codec.name());

            log.info("publish message. " + data);
            eventBus.publish(SUBSCRIBER_ADDRESS1, data, dOptions);
        });
        vertx.setPeriodic(1000, timerId -> {
            ExamplePOJO2 data = new ExamplePOJO2(messageId.incrementAndGet(), "pojo2");
            DeliveryOptions dOptions = new DeliveryOptions()
                    .addHeader("codec", examplePOJO2Codec.name())
                    .setCodecName(examplePOJO2Codec.name()); // 보내기 전용

            log.info("publish message. " + data);
            eventBus.publish(SUBSCRIBER_ADDRESS2, data, dOptions);
        });

        // 10초 후 종료
        vertx.setTimer(10_000, timerId -> {
            vertx.close();
        });
        startPromise.complete();
    }
}
