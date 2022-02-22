package com.github.penguin418;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.Cookie;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

import static io.vertx.ext.web.handler.StaticHandler.DEFAULT_WEB_ROOT;

@Slf4j
public class CustomWebserverVerticle extends AbstractVerticle {
    private final String CUSTOM_WEBROOT = "custom-webroot";
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        log.info("start");

        Router router = Router.router(vertx);
        // webroot 를 지정하고, 자동 라우팅하지 않을 수도 있다.
        StaticHandler.create(CUSTOM_WEBROOT).setCachingEnabled(false);
        // 다음 패턴을 사용해서 모든 요청을 검사할 수 있다.
        router.route().handler(ctx->{
            log.info("request: " + ctx.request().absoluteURI());
            ctx.next();
        });

        // 수동 라우팅
        router.get("/easter-egg").handler(ctx->{
           log.info("easter egg!");
           ctx.response().sendFile(CUSTOM_WEBROOT + "/easter-egg.html");
        });

        // 수동 라우팅 - 인증 페이지
        router.get("/auth").handler(ctx->{
            log.info("auth");
            ctx.response().sendFile(CUSTOM_WEBROOT + "/auth.html");
        });

        // 수동 라우팅 - 중요 페이지
        router.get("/secure").handler(ctx->{
            log.info("secure");
            // Authorization 헤더 확인
            Set<Cookie> cookies = ctx.request().cookies();
            final String cookieAuthorization = ctx.request().getCookie("Authorization") != null ? ctx.request().getCookie("Authorization").getValue() : null;
            final String headerAuthorization = ctx.request().headers().get("Authorization");

            if (cookieAuthorization != null || headerAuthorization != null){
                log.info("has Authorization header");
                final String authorization = cookieAuthorization != null ? cookieAuthorization : headerAuthorization;
                if (authorization.equals("Bearer%20HelloWorld")){
                    // 헤더에 값이 제대로 들어있다면 페이지 보여줌
                    log.info("has Authorization header and right Authentication");
                    ctx.response().sendFile(CUSTOM_WEBROOT + "/secure.html");
                    return;
                }
            }
            // 아니면 401에러 추가
            ctx.fail(401);
            ctx.next();
        });

        // last는 마지막 라우팅이며, 여기로 오는 경우 페이지를 못 찾았다는 뜻임.
        // 근데, 자동으로 404를 해주지는 않으니, 수동으로 넣어야 함 ㅠㅠ
        router.get().last().handler(ctx->{
            log.info("no matched page");
            // 404 에러 추가
            ctx.fail(404);
            ctx.next();
        });

        // 위에서 fail 또는 exception 던진 response는 모두 여기서 걸러짐
        router.get().failureHandler(ctx->{
            final int statusCode = ctx.statusCode();
            final HttpServerResponse response = ctx.response();
            log.info("failed. statusCode=" + statusCode);

            response.setStatusCode(statusCode);
                switch (ctx.statusCode()) {
                    case 401:
                        response.putHeader("WWW-Authenticate", "Bearer")
                                .sendFile(CUSTOM_WEBROOT + "/401.html");
                        break;
                    case 404:
                        response.sendFile(CUSTOM_WEBROOT + "/404.html");
                        break;
                    default:
                        response.sendFile(CUSTOM_WEBROOT + "/500.html");
                }
        });

        vertx.createHttpServer()
                .requestHandler(router)
                .connectionHandler(con->{
                    log.info("create connection" + con);
                    con.closeHandler(closedConnection->{
                       log.info("close connection" + con);
                    });
                })
                .listen(8082);

        log.info("click link -> http://localhost:8082/easter-egg");
        super.start(startPromise);
    }

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new CustomWebserverVerticle());
    }
}
