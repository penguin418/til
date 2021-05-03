import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.Date;

public class Main {
    public static class DelayEvent implements Delayed {
          private final long executionTime;
          private String message = "";
          
          // delayTime 만큼 미룸
          public DelayEvent(String message, long delayTime, TimeUnit timeUnit){
              this.message = message;
              this.executionTime = System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(delayTime, timeUnit);

          }
          public String getMessage(){
              return this.message;
          }

          // executionTime 과의 차이를 반환
        public long getDelay(TimeUnit timeUnit) {
          return timeUnit.convert(
                  executionTime - System.currentTimeMillis(), 
                  TimeUnit.MILLISECONDS
              );
        }

        @Override
        public int compareTo(Delayed o) {
            return (int)(getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
        }
    }
    @AllArgsConstructor
    private static class Consumer implements Runnable {
        private final DelayQueue<DelayEvent> queue;

        @Override
        public void run() {
            try {
                while(true){
                    DelayEvent event = queue.take();
                    System.out.println(event.getMessage());
                }
            } catch (InterruptedException ignored) {
            }
        }
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        DelayQueue<DelayEvent> bq = new DelayQueue<>();
        executorService.submit(new Consumer(bq));
        bq.put(new DelayEvent("고구마 심기", 1, TimeUnit.SECONDS));
        bq.put(new DelayEvent("친구랑 만나기", 1, TimeUnit.HOURS));
        bq.put(new DelayEvent("양파 2개, 토마토소스 1개 구매", 2, TimeUnit.SECONDS));

        executorService.shutdown();
        if (!executorService.awaitTermination(1, TimeUnit.SECONDS)){
            List<DelayEvent> finished = new ArrayList<>();
            bq.drainTo(finished);
            System.out.println("먼 미래의 스케줄 " + bq.size());
            executorService.shutdownNow();
        }
    }
}
