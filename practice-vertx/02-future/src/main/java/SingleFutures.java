import io.vertx.core.Future;
import io.vertx.core.Promise;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SingleFutures {

    public static void main(String[] args) {
        new SingleFutures().callFuture();
    }

    public void callFuture() {
        doAsyncOutside().onSuccess(result->{
            log.info("success");
        }).onFailure(e->{
            log.error("e:"+ e.toString());
        });
    }

    public Future<Void> doAsyncInside(Promise<Void> promise) {
        log.info("do async Inside");
        log.info("do async Inside finished");
        promise.complete();
        return promise.future();
    }

    public Future<Void> doAsyncOutside() {
        Promise<Void> promise = Promise.promise();
        log.info("do async Outside");
        Future<Void> future = doAsyncInside(promise);
        // promise.complete(); // <- already completed inside `doAsyncInside`
        log.info("do async Outside finished");
        return future;
    }


}
