package com.github.penguin418.practice01_mysql;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.Tuple;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class MysqlVerticle extends AbstractVerticle {
    private MySQLPool databaseClient;

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        log.info("start");
        setDatabaseClient();

        getRole(1).onSuccess(role->{
            log.info("role " + role);
        });

        getRoles().onSuccess(roles->{
            log.info("roles " + Arrays.toString(roles));
        });

        startPromise.complete();
    }

    // vert.x 의 mysql 은 비동기로 동작하지만 매우 불편하다.
    // 모두 비동기는 아니고 트랜잭션도 존재하나,
    // 근본적으로 블로킹으로 실행하는 것은 vert.x의 취지와 맞지 않은 바. nosql 이 더 적합.
    private Future<String[]> getRoles(){
        Promise<String[]> promise = Promise.promise();
        // 단일 쿼리 시에는 connection 얻을 필요가 없다
        databaseClient.preparedQuery("SELECT * FROM roles")
                .execute()
                .onSuccess(rows -> {
                    // 하나씩 빼야함
                    List<String> roles = new ArrayList<>();
                    rows.iterator().forEachRemaining(row-> {
                        roles.add(row.getString("role"));
                    });
                    // arr 로 반환
                    String[] arr = new String[roles.size()];roles.toArray(arr);
                    promise.complete(arr);
                })
                .onFailure(fail -> {
                    log.warn("failed in connection");
                });
        return promise.future();
    }

    private Future<String> getRole(Integer id){
        Promise<String> promise = Promise.promise();
        // 단일 쿼리 시에는 connection 얻을 필요가 없다
        databaseClient.preparedQuery("SELECT * FROM roles where id=(?)")
                .execute(Tuple.of(id))
                .onSuccess(rows -> {
                    final String role = rows.iterator().next().getString("role");
                    promise.complete(role);
                })
                .onFailure(fail -> {
                    log.warn("failed in connection");
                });
        return promise.future();
    }

    private void setDatabaseClient() {
        MySQLConnectOptions connectOptions = new MySQLConnectOptions()
                .setPort(3306)
                .setHost("localhost")
                .setDatabase("vertx-sample")
                .setUser("username")
                .setPassword("password")
                .setCharset("utf8")
                .setCollation("utf8_general_ci");


        PoolOptions poolOptions = new PoolOptions()
                .setMaxSize(5);

        databaseClient = MySQLPool.pool(vertx, connectOptions, poolOptions);
    }

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new MysqlVerticle());
    }
}
