package job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.MyBackupService;

// 방법1, 복구시 스케줄러 순서까지 보장됨
public class SyncJob2 implements Job {
    private static Logger logger = LoggerFactory.getLogger(SyncJob2.class);
    private MyBackupService couponService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        synchronized (couponService) {
            logger.info("execute");
            couponService.startBackup();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setCouponService(MyBackupService couponService) {
        this.couponService = couponService;
    }
}
