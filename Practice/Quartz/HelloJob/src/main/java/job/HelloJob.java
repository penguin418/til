package job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.HelloService;

/**
 * 기본 job
 */
public class HelloJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(HelloJob.class);
    private HelloService helloService;
    private String subject;

    public HelloJob(){}

    // scheduler 의 worker thread 로 부터 실행됨
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("execute()");
        helloService.hello(subject);
    }

    // job factory 가 값을 주입해줌
    public void setHelloService(HelloService helloService){
        this.helloService = helloService;
    }

    // job factory 가 값을 주입해줌
    public void setSubject(String subject) {
        this.subject = subject;
    }
}
