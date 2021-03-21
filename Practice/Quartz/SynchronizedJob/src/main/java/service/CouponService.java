package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 백업 서비스
 */
public class CouponService {
    private static Logger logger = LoggerFactory.getLogger(CouponService.class);
    /**
     * 전체 백업 횟수
     */
    private static int totalCoupon;

    public void issueCoupon(){
        logger.info("total coupon = {}", totalCoupon);
        System.out.println("issuing coupon ...");
        totalCoupon += 1;
    }
}
