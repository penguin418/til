package com.github.penguin418.practice02_proxy_hi.common;

import com.github.penguin418.practice02_proxy_hi.provider.HiServiceImpl;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.serviceproxy.ServiceProxyBuilder;

// @VertxGen // Vert.x 가 지원하는 다른 언어에서 해당 프록시를 사용하려면 추가
@ProxyGen // proxy 서비스를 만들려면, ProxyGen 어노테이션을 넣은 인터페이스를 생성하면 됨
public interface HiService {
    static HiService create(Vertx vertx){
        return new HiServiceImpl(vertx);
    }
    static HiService createProxy(Vertx vertx, String address){
        return new ServiceProxyBuilder(vertx)
                .setAddress(address)
                .build(HiService.class);
    }

    // 프록시를 위해 지켜야할 패턴들
    // 4.0 까지는Future<> 를 반환. void도 Future<Void>로만 가능했음
    // 4.1 부터는 Handler를 사용가능
    void hi_1(String username, Handler<AsyncResult<String>> resultHandler);
    // 4.1 부터는 Handler를 사용가능
    // 또는 Future<ReturnType> 대신, @Fluent 어노테이션 추가하면 ReturnType 을 리턴가능
    void hi_2(String username, Handler<AsyncResult<HiMessage>> resultHandler);
}
