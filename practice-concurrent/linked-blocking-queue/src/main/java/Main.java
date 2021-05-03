import lombok.AllArgsConstructor;

import java.util.concurrent.*;

public class Main {
    @AllArgsConstructor
    private static class Producer implements Runnable {
        private final BlockingQueue<Integer> queue;

        @Override
        public void run() {
            try {
                String[] messages = {"안녕하세요"};
                for (int i = 0; i < 5; i++) queue.put(i);
            } catch (InterruptedException ignored) {
            }
        }
    }
    @AllArgsConstructor
    private static class Consumer implements Runnable {
        private final BlockingQueue<Integer> queue;

        @Override
        public void run() {
            try {
                int i=0;
                while(i != 4){
                    i = queue.take();
                    System.out.println(i);
                }
            } catch (InterruptedException ignored) {
            }
        }
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        BlockingQueue<Integer> bq = new LinkedBlockingDeque();
        executorService.submit(new Producer(bq));
        executorService.submit(new Consumer(bq));
        executorService.shutdown();
    }
}
