package com.github.penguin418.practice07_csrf;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CSRFHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.templ.thymeleaf.ThymeleafTemplateEngine;
import jdk.jfr.Frequency;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CSRFWebserverVerticle extends AbstractVerticle {

    public static final String CUSTOM_WEBROOT = "csrf-webroot";
    public static final String CSRF_TOKEN = "practice-csrf";
    private ThymeleafTemplateEngine engine = null;

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        Router router = Router.router(vertx);

        StaticHandler.create(CUSTOM_WEBROOT).setCachingEnabled(false);

        // csrf 활성화
        // 기본으로 쿠키로 추가해준다
        router.post().handler(BodyHandler.create());
        router.post().handler(ctx->{
            MultiMap attributes = ctx.request().formAttributes();
            log.info(attributes.entries().toString());
            ctx.next();
        });
        router.route().handler(CSRFHandler.create(vertx, CSRF_TOKEN));

        // 하지만, 쿠키에 들어간 csrf로는 form 사용이 불편하므로,,
        // form에 csrf 값을 넣어주기 위해서 템플릿 엔진을 사용한다
        engine = ThymeleafTemplateEngine.create(vertx);

        router.get("/").handler(ctx -> {
            render(ctx, new JsonObject(), "index");
        });

        router.post("/post").handler(ctx->{
            JsonObject data = new JsonObject()
                    .put("response", "valid request");
            ctx.response().send(data.encode());
        });

        vertx.createHttpServer().requestHandler(router).listen(8085);
    }

    // 렌더링. 반복되는 예외처리로 메서드화하면 좋음
    private void render(RoutingContext ctx, JsonObject data, String template) {
        // form에서 바로 사용하기 위해 템플릿으로 전달한다
        // vertx csrf 의 기본 헤더 이름은 CSRFHandler 클래스에 있다
        final String CSRF_HEADER = "X-XSRF-TOKEN";
        final String CSRF = ctx.data().get(CSRF_HEADER).toString();
        data.put("csrf", CSRF);
        data.put("csrf_header", CSRF_HEADER);
        engine.render(data, CUSTOM_WEBROOT+"/"+template+".html").onSuccess(result->{
            ctx.response().end(result);
        }).onFailure(ctx::fail);
    }

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new CSRFWebserverVerticle());
    }
}
