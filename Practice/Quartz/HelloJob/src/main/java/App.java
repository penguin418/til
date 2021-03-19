import job.HelloJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.HelloService;

public class App {
    private static Logger logger = LoggerFactory.getLogger(App.class);
    private static HelloService helloService;

    public static void main(String[] args){
        logger.info("시작");
        helloService = new HelloService();

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        try{
            JobDetail helloJohn = JobBuilder
                    .newJob(HelloJob.class)
                    .withIdentity("helloJob001", "group001")
                    .usingJobData("subject", "John") // John 에게 인사하기
                    .build();

            Trigger helloJohnTrigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("trigger001", "group001")
                    .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever())
                    .build();

            JobDetail helloJake = JobBuilder
                    .newJob(HelloJob.class)
                    .withIdentity("helloJob002", "group001")
                    .usingJobData("subject", "Jake") // Jake 에게 인사하기
                    .build();

            Trigger helloJakeTrigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("trigger002", "group001")
                    .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever())
                    .build();

            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.getContext().put("helloService", helloService); // HelloService 전달
            scheduler.start();
            scheduler.scheduleJob(helloJohn, helloJohnTrigger);
            scheduler.scheduleJob(helloJake, helloJakeTrigger);
        } catch (SchedulerException e) {
            logger.info("종료");
            e.printStackTrace();
        }
    }
}
