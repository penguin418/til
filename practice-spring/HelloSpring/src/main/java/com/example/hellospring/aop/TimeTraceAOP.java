package com.example.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect // AOP
@Component
public class TimeTraceAOP {
    @Around("execution(* com.example.hellospring..*(..))") // 적용 대상
    public Object execute(ProceedingJoinPoint joinPoint) throws  Throwable{
         long start = System.currentTimeMillis();
         System.out.println("START: " + joinPoint.toString());
         try{
             return joinPoint.proceed();
         }finally {
             long finish = System.currentTimeMillis();
             long timeMs = finish - start;
             System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");
         }
    }
}

// 만들고 SpringBean 에 등록 (Spring Config