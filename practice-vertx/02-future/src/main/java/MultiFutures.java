import io.vertx.core.*;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

import static io.vertx.core.Promise.promise;

@Slf4j
public class MultiFutures extends AbstractVerticle {
    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(MultiFutures.class.getName());
    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        startPromise.complete();
        callAnyFutures();
        callAllFutures();
    }

    public void callAllFutures() throws InterruptedException {
        Future<Void> f1 = task1000ms("all");
        Future<Void> f2 = task200ms("all");
        CompositeFuture.all(f1,f2).onSuccess(cf -> {
            // CompositeFuture의 all의 onSuccess는 모든 job이 성공했음(succeed)을 의미합니다.
            assert f1.isComplete() && f2.isComplete();
            log.info(String.format("(all)Jobs onSuccess \ntask1000ms completed?:%s \ntask200ms completed?:%s", f1.isComplete(), f2.isComplete()));
        }).onFailure(cf->{
            // CompositeFuture의 all의 onSuccess는 하나의 job이라도 실패했음(failed)을 의미합니다.
            assert f1.isComplete() || f2.isComplete();
            log.info(String.format("(all)Jobs onComplete \ntask1000ms completed?:%s \ntask200ms completed?:%s", f1.isComplete(), f2.isComplete()));
        }).onComplete(cf->{
            // onComplete 는 Futures가 끝나면 항상 호출되지만, CompositeFuture에서는 하나라도 성공한 경우 호출됩니다.
            assert f1.isComplete() || f2.isComplete();
            log.info(String.format("(all)Jobs onComplete \ntask1000ms completed?:%s \ntask200ms completed?:%s", f1.isComplete(), f2.isComplete()));
        });
    }

    public void callAnyFutures() throws InterruptedException {
        Future<Void> f1 = task1000ms("any");
        Future<Void> f2 = task200ms("any");
        CompositeFuture.any(f1,f2).onSuccess(cf -> {
            // CompositeFuture의 all의 onSuccess는 하나의 job이라도 성공했음(succeed)을 의미합니다.
            assert f1.succeeded() || f2.succeeded();
            log.info(String.format("(any)Jobs onSuccess \ntask1000ms completed?:%s \ntask200ms completed?:%s", f1.isComplete(), f2.isComplete()));
        }).onFailure(e->{
            // CompositeFuture의 all의 onFailure는 모든 job이 실패했음(failed)을 의미합니다.
            assert f1.failed() && f2.failed();
        });
    }

    public Future<Void> task1000ms(String testContext) throws InterruptedException {
        Promise<Void> promise = promise();
        log.info(String.format("do Job(%s) In 1000ms", testContext));
        vertx.timerStream(1000).handler(r->{
            log.info(String.format("do Job(%s) In 1000ms finished", testContext));
            promise.complete();
        });
        return promise.future();
    }

    public Future<Void> task200ms(String testContext) throws InterruptedException {
        Promise<Void> promise = promise();
        log.info(String.format("do Job(%s) In 200ms", testContext));
        // Thread.sleep(200); // one verticle instance use one thread loop.
        vertx.timerStream(200).handler(r->{
            log.info(String.format("do Job(%s) In 200ms finished", testContext));
            promise.fail("failed");
        });
        return promise.future();
    }


}
