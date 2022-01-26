import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletionStage;

@Slf4j
public class ChainingFutures {
    public static void main(String[] args) {
        new ChainingFutures().simpleChaining();
    }

    public void simpleChaining() {
        Future<String> task3Finished = task1(Promise.promise())
                .compose(s -> {
                    log.info("compose1: " + s); // task1의 결과
                    return task2(Promise.promise());
                }).compose(s->{
                    log.info("compose2: " + s); // task2의 결과
                    return task3(Promise.promise());
                }).compose(s->{
                    log.info("compose3: " + s); // task3의 결과 (실패했으므로 실행되지 않음)
                    return Future.succeededFuture(s);
                });
        // 모두 성공해야 성공
        log.info("all succeed: " + task3Finished.succeeded());
        // 마지막 결과, fail 일 경우 null
        log.info("final result: " + task3Finished.result());
    }

    public void complexChaining() {
        Future<String> task3Finished = task1(Promise.promise())
                .compose(s -> {
                    log.info(s); // task1의 결과
                    return task2(Promise.promise());
                }).compose(s->{
                    log.info(s); // task2의 결과
                    return task3(Promise.promise());
                }).recover(t->{
                    // task3의 결과 (실패할 경우)
                    log.error("task3 failed");
                    if (t.getMessage().equals("use-default"))
                        return Future.succeededFuture("default");
                    else
                        return Future.failedFuture(t);
                });
        // 모두 성공해야 성공
        log.info("all succeed: " + task3Finished.succeeded());
        // 마지막 결과, fail 일 경우 null
        log.info("final result: " + task3Finished.result());
    }

    public void newSkills(){
        // 어떤 async 메서드가 "hello"를 반환한다고 해보자
        Promise<String> promise = Promise.promise();
        promise.complete("hello");
        // java 11에 추가된 CompletableFuture 는 CompletionStage 를 상속하는데,
        // java 11의 CompletableFuture와 io.vertx.core.Future 를 함께 쓰는 것을
        // 돕기 위해 아래의 메서드가 존재한다
        CompletionStage<String> cs = promise.future().toCompletionStage();
        // 처리도 가능하다
        cs.thenApply(String::toUpperCase)
                .whenComplete((res,err)->{
                    if (err == null) {
                        log.info(""+res);
                    } else {
                        log.error(err.getMessage());
                    }
                });
        // 반대로 바꿀 수도 있다.
        Future<String> f = Future.fromCompletionStage(cs);
    }

    public Future<String> task1(Promise<String> promise) {
        log.info("task1");
        promise.complete("task1 finished");
        return promise.future();
    }

    public Future<String> task2(Promise<String> promise) {
        log.info("task2");
        promise.complete("task2 finished");
        return promise.future();
    }

    public Future<String> task3(Promise<String> promise) {
        log.info("task3");
        promise.fail("use-default");
        promise.tryComplete("task3 finished");
        return promise.future();
    }
    public Future<String> task4(Promise<String> promise, String input) {
        log.info("task4. input was=" + input);
        promise.complete("task3 finished");
        return promise.future();
    }
}
