# 자바 라이브러리

# Quartz

[https://examples.javacodegeeks.com/wp-content/uploads/2019/05/quartz-architecture.jpg.webp](https://examples.javacodegeeks.com/wp-content/uploads/2019/05/quartz-architecture.jpg.webp)

그림 출처: [https://examples.javacodegeeks.com/enterprise-java/quartz/java-quartz-architecture-example/](https://examples.javacodegeeks.com/enterprise-java/quartz/java-quartz-architecture-example/)

글 출처 및 참고: [http://www.quartz-scheduler.org/documentation/quartz-2.3.0/tutorials/tutorial-lesson-03.html](http://www.quartz-scheduler.org/documentation/quartz-2.3.0/tutorials/tutorial-lesson-03.html)

### 개요

스케줄링 라이브러리

### 특징

다양한 스케줄 기능 제공

### 주요 모듈

- `Job`

    작업 수행 객체

    `execute`메소드에 수행 로직을 구현

- `Trigger`

    스케줄을 정의한 객체

    시간, 주기, 횟수를 정의할 수 있다

    Job에 대해 여러개의 트리거 설정 가능하다

- `Job Listener` & `Trigger Listener`

    작업, 트리거와 관련된 이벤트를 수신하는 객체

### 아키텍쳐

위의 그림처럼 동작한다

`Scheduler`에 `Job` Listener와 `Trigger` Listener를 등록하고 시작하면 주기적으로 트리거된다

### 시작하기

- 의존성

    ```java
    compile group: 'org.quartz-scheduler:quartz:2.3.1'
    ```

- `Job` 인터페이스 구현 ( execute메소드 )

    ```java
    public class SimpleJob implements Job {
        public void execute(JobExecutionContext context) throws JobExecutionException {
            // 여기에 수행할 작업을 구현하자
        }
    }
    ```

- `Job`(Job Definition)을 생성한다

    ```java
    JobDetail job = JobBuilder.newJob(SimpleJob.class)
                    .withIdentity("myJob", "myGroup")
                    .build();
    ```

    클래스이름을 지정해서 쓴다 → 재컴파일 필요ㅠㅠ

- `Trigger`를 생성한다

    ```java
    Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("myTrigger", "myGroup")
    		.forJob("myJob")
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(3) // 3 초마다
                            .repeatForever()) // 계속 반복됨 
                    .build();
    ```

- 스케줄러에 등록하고 시작한다

    ```java
    public class QuartzPractice{
        public void main(String arg[] ) throw Exception{
    	SchedulerFactory factory= new org.quartz.impl.StdSchedulerFactory();
    	Scheduler scheduler = factory.getScheduler();

    	// job 생성
    	JobDetail job = ...

    	// trigger 생성
    	Trigger trigger = ...

    	scheduler.start(); // 스케줄 시작
    	schedueler.scheduleJob(job, trigger); // 작업 인스턴스, 트리거 등록
    	Thread.sleep(10000L); // 10초 
        }
    }

    ```

### 자세히

- `Job` 인터페이스와 `JobDetail`

    `jobDetail`은 'Job Definition'라는 이름으로 불린다

    각 인스턴스별로 추가적인 데이터를 넣어줄 수 있다

    ```java
    // 방법1
    Map<String, Serializable> jobData1 = Maps.newHashMap();
    jobData1.put("KEY_1", "VALUE");

    JobDetail job = JobBuilder.newJob(ComplexJob.class)
                    .withIdentity("myJob", "myGroup")
    		.usingJobData(jobData1)
    		// 방법2
    		.usingJobData("KEY_2", "VALUE_2")
                    .build();
    // 방법3
    JobDetail jobData2 = job.getJobDataMap();
    jobData2.put("KEY_3", 1);

    // ...
    // 작업, 트리거 등록
    ```

    `Job` 인터페이스의 `execute`메소드는 JobExecutionContext만 제공하지만, `JobDataMap`을 사용하여 파라미터를 받을 수 있다

    ```java
    public class ComplexJob implements Job {
        public void execute(JobExecutionContext context) throws JobExecutionException {
    	JobDataMap dataMap = context.getJobDetail().getJobData();
    	String value = dataMap.getString("KEY_1");
    	Int i = dataMap.getIntValue("KEY_3");
        }
    ㅓ}
    ```

    `setter`를 구현하면 JobFactory가 자동으로 해당 setter를 호출해 값을 주입해주므로 context~ 작업을 생략해도 된다

    ```java
    public class MyJob implements Job {
        String myValue; // JobFactory에 의해 주입되는 값
        
        public MyJob(){}

        public void execute(JobExecutionContext context) throws JobExecutionException {
    	String value = myValue;
        }

        public void setMyValue(String myValue){ this.myValue = myValue; }
    }
    ```

    `Job`으로 데이터를 넘겨주는 작업은 직렬화를 사용하므로 클래스 버전 차이로 호환성이 깨질 경우, 직렬화 문제가 발생한다. 따라서 클래스 버전을 잘 관리해 주어야 한다

- `Trigger`

    용도에 따라 몇가지 트리거가 있지만 다음 트리거를 가장 많이 쓴다

    - `SimpleTrigger`는 n번 실행하는 경우에 유용하다
    - `CronTrigger`는 주기 별 반복실행 하는 경우 유용하다

    `Simple Trigger`는 특정 시점에 정확히 한 번 실행, 혹은 특정 간격으로 반복 실행이 필요한 경우 사용되며 SimpleSchedulerBuilder를 사용하여 빌드된다

    ```java
    import org.quartz.DateBuilder;
    SimpleTrigger trigger = (SimpleTrigger) newTrigger()
        .withIdentity("trigger1", "group1")
        .startAt(DateBuilder
    	     .FutureDate(10, IntervalUnit.SECOND)) // 10초 뒤 시작
        .forJob("job1", "group1")
        .build();
    ```

    참고로 `Trigger Builder`를 사용하는 경우 (SimpleTrigger)처럼 캐스팅이 필요없고 명시하지 않은 값에 대해서는 기본값이 존재한다

    - startAt : 없는 경우 즉시
    - withIdentity: 없는 경우 임의의 이름

    `Cron Trigger`는 CronSchedulerBuilder를 사용하여 빌드된다

    ```java
    import org.quartz.CronScheduleBuilder;

    trigger = newTrigger()
        .withIdentity("trigger3", "group1")
        .withSchedule(CronScheduleBuilder
    		  .cronSchedule("0 0 13 * * WED")) // 수요일 13시 정각
        .forJob("myJob", "group1")
        .build();
    ```

    `Quartz Calendar`는 트리거의 실행 일정에서 특정 시간을 제외하는 데 유용한 객체이다

    `Quartz Calendar`는 util의 Calendar가 아니다!

    `Quartz Calendar`는 다음 인터페이스를 구현하여 만든다

    ```java
    package org.quartz;

    public interface Calendar {

      public boolean isTimeIncluded(long timeStamp);
      public long getNextIncludedTime(long timeStamp);
    }
    ```

    하지만 보통 하루 이상의 기간을 차단하는 방법이 많이 쓰이기 때문에 이미 `HolidayCalendar`를 제공하고 있다

    예시

    ```java
    import org.quartz.impl.HolidayCalendar
    ...
    HolidayCalendar cal = new HolidayCalendar();
    cal.addExcludedDate( someDate );
    cal.addExcludedDate( someOtherDate );
    sched.addCalendar("myHolidays", cal, false); // 제외

    Trigger t = newTrigger()
        .withIdentity("myTrigger")
        .forJob("myJob")
        .withSchedule(dailyAtHourAndMinute(9, 30)) // 매일 9시 30분에 실행
        .modifiedByCalendar("myHolidays") // 지정한 날짜 제외
        .build();
    ```

    예외 처리(`Misfire Instruction`)

    어떤 경우엔 트리거가 실행되지 못할 수 있다

    - 트리거가 너무 많음
    - Quartz 스레드 풀에 스레드가 부족함
    - 특정 시간에 예약된 트리거를 모두 실행시키기에 리소스가 부족

    트리거가 실행되지 못한 경우 이에대한 처리를 해줄 수 있다

    다음은 `Misfire Instruction`이다

    ```
    MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY
    MISFIRE_INSTRUCTION_FIRE_NOW
    MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_EXISTING_REPEAT_COUNT
    MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_REMAINING_REPEAT_COUNT
    MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT
    MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_EXISTING_COUNT
    ```

    아래 방법으로 사용한다

    ```java
    trigger = newTrigger()
        .withIdentity("trigger7", "group1")
        .withSchedule(simpleSchedule()
    	...
            .withMisfireHandlingInstructionNextWithExistingCount())
        .build();
    ```

    우선순위

    우선순위를 지정하면 중요한 트리거를 보호할 수 있다

    - 양수, 음수 상관없이 지정가능하며 클수록 우선순위는 높아진다
    - 지정하지 않는 경우 기본 값은 5이다
    - 시간이 동일할때만 효과가 있다
    (priority=1인 10시 59분 작업이 priority=1억인 11시 작업보다 우선이다)

    ```java
    Trigger t = ....
    t.setPriority(1); // 1이 제일 높다
    ```

- `Job Listener`는 작업과 관련된 이벤트를 수신한다

    ```java
    scheduler.getListenerManager()
    	 .addJobListener(myJobListener, 
                             KeyMatcher.jobKeyEquals(new JobKey("myJobName",
                                                                "myJobGroup")));
    ```

    이건 아직 써본적이 없다

    [http://www.quartz-scheduler.org/documentation/quartz-2.3.0/tutorials/tutorial-lesson-07.html](http://www.quartz-scheduler.org/documentation/quartz-2.3.0/tutorials/tutorial-lesson-07.html)

- `Trigger Listener`는 트리거와 관련된 이벤트를 수신한다
- Scheduler

    스케줄러에는 `Worker` 쓰레드가 `Job`의 `execute` 메소드를 호출한다

- `Scheduler Listener`는 스케줄 이벤트를 수신한다

    [http://www.quartz-scheduler.org/documentation/quartz-2.3.0/tutorials/tutorial-lesson-08.html](http://www.quartz-scheduler.org/documentation/quartz-2.3.0/tutorials/tutorial-lesson-08.html)

- `JobStore`는 작업, 트리거, 캘린더 모두를 추적한다

    ! 코드로 `JobStore`를 직접 사용하면 안된다

    `RAMJobStore`는 가장 간단한 `JobStore`로 성능이 가장 좋다

    - RAM에 모든 데이터를 보관한다
    - ↔ 종료 시 모든 스케줄링 정보가 손실된다
    - 설정이 간편하다

        ```java
        org.quartz.jobStore.class = org.quartz.simpl.RAMJobStore
        ```

        끝

    `JDBCJobStore`는 JDBC를 통해 모든 데이터를 DB에 저장한다

    - 설정이 조금 덜 간편하다

        quartz가 JobStoreTx를 사용하여 트랜잭션을 관리하도록 한다

        ```java
        org.quartz.jobStore.class = org.quartz.impl.jdbcjobstore.JobStoreTX
        ```

        테이블 접두사로 QRTZ_를 추가한다

        ```java
        org.quartz.jobStore.tablePrefix = QRTZ_
        ```

        사용할 데이터 소스의 이름으로 JDBCJobStore 구성한다

        ```java
        org.quartz.jobStore.dataSource = myDS
        ```

- 구성, 리소스, 성능

    Quartz는 모든 로깅에 SLF4J를 사용해야한다