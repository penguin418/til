import io.vertx.core.*;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(VertxExtension.class)
public class CompositeFutureTest {
    private final Logger log = LoggerFactory.getLogger(CompositeFutureTest.class.getName());

    public Future<String> retry(Vertx vertx) {
        Promise<String> promise = Promise.promise();

        Promise<String> timer1 = Promise.promise();
        Promise<String> timer3 = Promise.promise();
        vertx.setTimer(1, (timerId)->{
            log.info("1 end");
            timer1.tryComplete("1");
            timer3.tryFail("fail");
        });
        vertx.setTimer(3, (timerId)->{

            log.info("3 end");
            timer3.complete("3");
            timer1.tryFail("fail");
        });


        CompositeFuture.any(timer1.future(), timer3.future()).onComplete(cf->{
            if (timer1.future().isComplete()){
                log.info("1 completed");
            }
            if (timer3.future().isComplete()){
                log.info("3 completed");
            }

            promise.complete();
        });

        return promise.future();
    }
    @Test
    public void test(Vertx vertx, VertxTestContext testContext){
        retry(vertx).onComplete((id)->{testContext.completeNow();});
    }
}
