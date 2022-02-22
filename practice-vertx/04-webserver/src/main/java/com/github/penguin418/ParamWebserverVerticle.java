package com.github.penguin418;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.templ.thymeleaf.ThymeleafTemplateEngine;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ParamWebserverVerticle extends AbstractVerticle {
    private final String CUSTOM_WEBROOT = "param-webroot";
    private final EventRepository eventRepository = new EventRepository();
    private ThymeleafTemplateEngine engine = null;
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        log.info("start");

        Router router = Router.router(vertx);
        router.route().handler(StaticHandler.create().setWebRoot("param-webroot").setCachingEnabled(false));

        router.route().handler(ctx->{
            log.info("request: " + ctx.request().absoluteURI());
            ctx.next();
        });

        // 타임리프
        engine = ThymeleafTemplateEngine.create(vertx);

        // 상품 페이지
        // path: productId
        // query: promoCode
        router.get("/products/:productId").handler(ctx -> {
            final String productId = ctx.pathParam("productId");
            final String promoCode = ctx.queryParams().get("promoCode");
            log.info("productId: " + productId + "promoCode? : " + (promoCode != null));

            // 제품 아이디 기반으로 내용 생성
            JsonObject data = new JsonObject()
                    .put("title", "product " + productId)
                    .put("content", "this is product " + productId + (promoCode==null?"":" you have promoCode["+promoCode+"]"));

            render(ctx, data, "products");
        });

        // 이벤트 페이지
        // path: eventId
        router.get("/events/:eventId").handler(ctx->{
            final String eventId = ctx.pathParam("eventId");

            log.info("event id: " + eventId);
            JsonObject data = new JsonObject()
                    .put("title", "event " + eventId)
                    .put("content", "this is event " + eventId);

            EventEntity entity = eventRepository.get(eventId);
            if (entity == null || entity.finished) {
                // 존재하지 않거나, 이벤트 종료 시 홈페이지로 리턴
                log.info("not exists");
                ctx.reroute("/");
            }else
                render(ctx, data, "events");
        });

        // 인덱스 페이지
        router.get("/").handler(ctx->ctx.response().sendFile(CUSTOM_WEBROOT +"/index.html"));

        vertx.createHttpServer().requestHandler(router).listen(8081);
        super.start(startPromise);
    }

    // 렌더링. 반복되는 예외처리로 메서드화하면 좋음
    private void render(RoutingContext ctx, JsonObject data, String template) {
        engine.render(data, CUSTOM_WEBROOT+"/"+template+".html").onSuccess(result->{
            ctx.response().end(result);
        }).onFailure(ctx::fail);
    }

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new ParamWebserverVerticle());
    }
}

@AllArgsConstructor
class EventEntity{
    public int id;
    public String title;
    public String productId;
    public boolean finished;
}
class EventRepository{
    private final Map<String, EventEntity> dataSource = new HashMap<String, EventEntity>()
    {{
        put("1",new EventEntity(1, "hello. this is first event.","1fD3s1df", false));
        put("2",new EventEntity(2, "hello. this is second event.","33rFgK4d", true));
        put("3",new EventEntity(2, "hello. this is second event.","33rFgK4d", true));
    }};

    public EventEntity get(String id){
        return dataSource.get(id);
    }
}
