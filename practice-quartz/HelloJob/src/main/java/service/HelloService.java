package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello Service
 */
public class HelloService {
    private static final Logger logger = LoggerFactory.getLogger(HelloService.class);
    /**
     * Say Hello to subject
     * @param subject { String }
     */
    public void hello(String subject){
        logger.info("hello()");
        System.out.println(String.format("Hello %s!!", subject));
    }
}
