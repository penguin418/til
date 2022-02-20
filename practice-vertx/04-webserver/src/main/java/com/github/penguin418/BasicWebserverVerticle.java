package com.github.penguin418;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BasicWebserverVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        log.info("start");

        Router router = Router.router(vertx);
        // default 폴더는 webroot
        // setWebRoot() 메서드로 재 지정 가능하다
        router.route().handler(StaticHandler.create().setCachingEnabled(false));
        // 다음 패턴을 사용해서 모든 요청을 검사할 수 있다.
        router.route().handler(ctx->{
            log.info("request: " + ctx.request().absoluteURI());
            ctx.next();
        });
        // default port는 8888
        vertx.createHttpServer().requestHandler(router).listen(8081);

        super.start(startPromise);
    }

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new BasicWebserverVerticle());
    }
}
