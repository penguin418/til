package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 백업 서비스
 */
public class MyBackupService implements BackupService {
    private static Logger logger = LoggerFactory.getLogger(MyBackupService.class);
    /**
     * 전체 백업 횟수
     */
    private static int backupCounter = 0;

    public void startBackup(){
        logger.info("backup count = {}", backupCounter);
        System.out.println(String.format("backup started (no=%d)", backupCounter));
        backupCounter += 1;
    }
}
