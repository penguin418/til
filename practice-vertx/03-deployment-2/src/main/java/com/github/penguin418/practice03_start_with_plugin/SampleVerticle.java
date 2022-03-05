package com.github.penguin418.practice03_start_with_plugin;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.Promise;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SampleVerticle extends AbstractVerticle {
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
        final String[] commands = {
                "run",
                SampleVerticle.class.getName()
        };
        Launcher.main(commands);
        // kill -int pid 로 종료하면 vertx 내부의 stop 이 호출된다.

        // 주의사항. 아래 방법은 디버그 종료를 눌러서 끄면 실행되지 않는다. 아래 방법 사용.
        // 프로세스 확인
        // ps -ax | grep java | grep CommandLineVerticle | awk '{print $1}'
        // 종료 (주의! -9 옵션으로 종료 시 main stop 은 실행안됨)
        // kill -INT 프로세스번호

        // 단점은 옵션을 모두 string 으로 줘야 한다는 점.
        // 추가로, deploymentId 획득 불가. 음 이건 단점일까..?

        // 배포 방법은 build.gradle 추가 설정 존재
    }
}