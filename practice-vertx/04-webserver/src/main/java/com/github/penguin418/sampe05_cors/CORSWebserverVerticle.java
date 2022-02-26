package com.github.penguin418.sampe05_cors;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.StaticHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
public class CORSWebserverVerticle extends AbstractVerticle {
    private static final String PORT = "port";
    private final String CUSTOM_WEBROOT = "cors-webroot";
    private final ItemRepository itemRepository = new ItemRepository();
    private final List<String> allowedHost = new ArrayList<String>() {{
        add("http://localhost:8085");
    }};
    private int port;

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        // 설정값들을 가져온다
        getConfigs();

        StaticHandler.create(CUSTOM_WEBROOT).setCachingEnabled(false);

        // 라우터 세팅
        Router router = Router.router(vertx);

        // 로깅용 핸들러
        router.route().handler(ctx -> {
            log.info("request: " + ctx.request().absoluteURI());
            ctx.next();
        });
        router.route().handler(BodyHandler.create());


        // cors 핸들러.
        // 8085, 8086만을 허용.
        addCorsHandler(router);

        router.get("/").handler(ctx -> {
            log.info("index");
            ctx.response().sendFile(CUSTOM_WEBROOT + "/index.html");
        });
        addItemHandlers(router);

        vertx.createHttpServer().requestHandler(router).listen(port);
        startPromise.complete();
    }

    private void getConfigs(){
        port = config().getInteger(PORT);
    }

    private void addItemHandlers(Router router) {
        router.post("/items").handler(ctx -> {
            log.info("postItem");
            String itemName = ctx.getBodyAsJson().getString("itemName");

            vertx.executeBlocking(p->{
                Item newItem = itemRepository.addItem(itemName);
                ctx.response().send(JsonObject.mapFrom(newItem).encode());
                p.complete();
            });
        });

        router.route("/items/:itemId").handler(ctx -> {
            log.info("requests containing itemId");
            try {
                int itemId = Integer.parseInt(ctx.pathParam("itemId"));
                if (itemId < 0 || itemRepository.getItem(itemId) == null)
                    throw new IndexOutOfBoundsException();
                ctx.next();
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                ctx.fail(404, e);
            }
        });

        router.get("/items/:itemId").handler(ctx -> {
            log.info("getItem");
            int itemId = Integer.parseInt(ctx.pathParam("itemId"));

            vertx.executeBlocking(p->{
                Item newItem = itemRepository.getItem(itemId);
                ctx.response().send(JsonObject.mapFrom(newItem).encode());
                p.complete();
            });
        });

        // 도달하지 못함
        router.put("/items/:itemId").handler(ctx -> {
            log.info("putItem");
            int itemId = Integer.parseInt(ctx.pathParam("itemId"));
            String itemName = ctx.getBodyAsJson().getString("itemName");

            vertx.executeBlocking(p->{
                Item newItem = itemRepository.putItem(itemId,itemName);
                ctx.response().send(JsonObject.mapFrom(newItem).encode());
                p.complete();
            });
        });

        // 도달하지 못함
        router.delete("/items/:itemId").handler(ctx -> {
            log.info("deleteItem");
            int itemId = Integer.parseInt(ctx.pathParam("itemId"));

            vertx.executeBlocking(p->{
                Item newItem = itemRepository.deleteItem(itemId);
                ctx.response().send(JsonObject.mapFrom(newItem).encode());
                p.complete();
            });
        });

        router.get("/items").handler(ctx -> {
            log.info("getItems");

            JsonObject data = ctx.getBodyAsJson();
            vertx.executeBlocking(p->{
                List<Item> items = itemRepository.getItems();
                JsonArray jsonArray = new JsonArray();
                items.stream().map(JsonObject::mapFrom).forEach(jsonArray::add);
                ctx.response().send(jsonArray.encode());
                p.complete();
            });
        });
    }

    private void addCorsHandler(Router router) {
        CorsHandler corsHandler = CorsHandler.create()
                .addOrigins(allowedHost)
                .allowedMethod(HttpMethod.GET) // 스프링과 달리, 기본값 없으므로, GET, POST, OPTIONS  추가 필요
                .allowedMethod(HttpMethod.POST)
                .allowedMethod(HttpMethod.OPTIONS)
                .allowCredentials(true)
                .allowedHeader("Access-Control-Allow-Headers")
                .allowedHeader("Access-Control-Allow-Method")
                .allowedHeader("Access-Control-Allow-Origin")
                .allowedHeader("Access-Control-Allow-Credentials")
                .allowedHeader("Content-Type")
                .allowedHeader("Authorization");
        router.route().handler(corsHandler);
    }

    public static void main(String[] args) {
        JsonObject port8085 = new JsonObject().put(PORT, 8085);
        JsonObject port8086 = new JsonObject().put(PORT, 8086);

        Vertx.vertx().deployVerticle(new CORSWebserverVerticle(), new DeploymentOptions().setConfig(port8085));
        Vertx.vertx().deployVerticle(new CORSWebserverVerticle(), new DeploymentOptions().setConfig(port8086));
    }
}

@AllArgsConstructor
@Getter
class Item implements Serializable {
    @JsonProperty
    private int id;
    @JsonProperty
    private String itemName;
}

class ItemRepository {
    private final Map<Integer, Item> items = new HashMap<Integer, Item>() {{
        put(1, new Item(1, "item1"));
        put(2, new Item(2, "item2"));
    }};
    private int maxId = 2;

    public synchronized Item addItem(String itemName) {
        maxId += 1;
        Item newItem = new Item(maxId, itemName);
        items.put(maxId, newItem);
        return newItem;
    }

    public Item getItem(int id) {
        return items.get(id);
    }

    public List<Item> getItems() {
        return new ArrayList<>(items.values());
    }

    public Item deleteItem(int id) {
        return items.remove(id);
    }

    public Item putItem(int id, String itemName) {
        if (items.containsKey(id))
            return items.put(id, new Item(id, itemName));
        else return null;
    }
}
