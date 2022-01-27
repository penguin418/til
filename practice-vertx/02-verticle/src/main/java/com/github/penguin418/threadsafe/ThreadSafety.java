package com.github.penguin418.threadsafe;

import com.sun.corba.se.spi.copyobject.CopierManager;
import io.vertx.core.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadSafety{
    public static void main(String[] args) {


        Vertx vertx = Vertx.vertx();
        // 비동기 이벤트를 생성해 n 초를 세는 메서드를 2번 호출하였다.
        // 로그를 보면 두 메서드 모두 `vert.x-eventloop-thread-0` 쓰레드에서 실행된다.
        //[vert.x-eventloop-thread-0] INFO com.github.penguin418.threadsafe.ThreadSafety - [counting 3] tick (timerId=0)
        //[vert.x-eventloop-thread-0] INFO com.github.penguin418.threadsafe.ThreadSafety - [counting 2] tick (timerId=2)
        //[vert.x-eventloop-thread-0] INFO com.github.penguin418.threadsafe.ThreadSafety - [counting 3] tick (timerId=0)
        //[vert.x-eventloop-thread-0] INFO com.github.penguin418.threadsafe.ThreadSafety - [counting 2] tick (timerId=2)
        //[vert.x-eventloop-thread-0] INFO com.github.penguin418.threadsafe.ThreadSafety - [counting 2] end (timerId=3)
        //[vert.x-eventloop-thread-0] INFO com.github.penguin418.threadsafe.ThreadSafety - [counting 3] tick (timerId=0)
        //[vert.x-eventloop-thread-0] INFO com.github.penguin418.threadsafe.ThreadSafety - [counting 3] end (timerId=1)
        //[vert.x-eventloop-thread-0] INFO com.github.penguin418.threadsafe.ThreadSafety - succeed

        // 즉, Vertx 클래스의 메서드는 하나의 쓰레드를 공유하여 쓰레드 세이프하게 동작한다.
        // 이를 ELP 라고 한당
        Future<Void> finished1 = countSeconds(vertx, 3);
        Future<Void> finished2 = countSeconds(vertx, 2);

        CompositeFuture.all(finished1, finished2).onSuccess(result->{
            if (result.succeeded())
                log.info("succeed");
           vertx.close();
        });
    }

    /**
     * 비동기 이벤트를 생성하여 숫자를 세는 메서드
     * @param v
     * @param n
     * @return
     */
    public static Future<Void> countSeconds(Vertx v, int n){
        Promise<Void> promise = Promise.promise();

        // Vertx 클래스의 메서드 는 쓰레드세이프하므로 객체 생성없이도 메서드 사용이 가능

        long periodicTimerId = v.setPeriodic(1_000, timerId->{
            log.info(String.format("[counting %d] tick (timerId=%d)", n, timerId));
        });

        // 비동기 메서드를 사용해서 이벤트를 생성한다
        v.setTimer(n* 1000L, timerId->{
            log.info(String.format("[counting %d] end (timerId=%d)", n, timerId));
            v.cancelTimer(periodicTimerId);
            promise.complete();
        });

        return promise.future();
    }
}
