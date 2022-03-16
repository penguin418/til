package com.github.penguin418.practice04_exception_handling.common;

import com.github.penguin418.practice04_exception_handling.provider.LibraryServiceImpl;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.serviceproxy.ServiceProxyBuilder;

// @VertxGen // Vert.x 가 지원하는 다른 언어에서 해당 프록시를 사용하려면 추가
@ProxyGen // proxy 서비스를 만들려면, ProxyGen 어노테이션을 넣은 인터페이스를 생성하면 됨
public interface LibraryService {
    static LibraryService create(Vertx vertx){
        return new LibraryServiceImpl(vertx);
    }
    static LibraryService createProxy(Vertx vertx, String address){
        return new ServiceProxyBuilder(vertx)
                .setAddress(address)
                .build(LibraryService.class);
    }

    void checkOut(String book, Handler<AsyncResult<String>> resultHandler);

    void checkIn(String book, Handler<AsyncResult<String>> resultHandler);
}
