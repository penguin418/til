# 레디스 사용방법

### Basic Redis using Jedis

Jedis는 과거부터 사용되던 거의 표준에 가가운 redis 드라이버로, 재사용가능한 쓰레드풀인 Jedis-Pool 을 사용한다

- 재사용가능한 쓰레드풀을 관리해주는 Redis Util을 만들어 보았다

    ```java
    public class RedisUtil {
        // 싱글턴
        private static RedisUtil instance = null;
        // 여러 쓰레드 간 재사용 가능한 커넥션
        private static JedisPool jedisPool;

        // 싱글턴
        public static synchronized RedisUtil getInstance() throws IllegalAccessException {
            if (instance == null)
                throw new IllegalStateException("초기화되지 않은 객체를 호출하고 있습니다");

            return instance;
        }

        // 초기화
        public static synchronized RedisUtil initialize(String host, int port, int timeout) {
            if (instance != null) {
                throw new IllegalStateException("이미 초기화된 객체입니다");
            }

            JedisPoolConfig poolConfig = new JedisPoolConfig();
            jedisPool = new JedisPool(poolConfig, host, port, timeout);
            instance = new RedisUtil();
            return instance;
        }

        public void set(String key, String value) {
            Jedis jedis = jedisPool.getResource();
            jedis.set(key, value);
            jedis.close();
        }

        public String get(String key) {
            Jedis jedis = jedisPool.getResource();
            String value = jedis.get(key);
            jedis.close();
            return value;
        }

        // ... 여러 메서드
    }
    ```

### Streaming Redis using Lettuce

Lettuce는 netty와 Reactor 기반으로 설계되어 Non-Block 하게 동작하는 쓰레드 세이프한 동작을 지원한다

사용법은 좀더 복잡하지만 성능은 더 좋다

- lettuce

    ```java
    public class RedisUtil {
        // 싱글턴
        private static RedisUtil instance = null;
        // 클라이언트
        private static RedisClient redisClient;

        // 단일 작업 커넥션
        private StatefulRedisConnection<String, String> connection;
        private RedisCommands<String, String> commands; // sync용 커맨드
        private RedisAsyncCommands<String, String> asyncCommands; // async용 커맨드
        // 메시지 바디
        private Map<String, String> messageBody;

        // 싱글턴
        public static synchronized RedisUtil getInstance() throws IllegalAccessException {
            if (instance == null)
                throw new IllegalStateException("초기화되지 않은 객체를 호출하고 있습니다");

            return instance;
        }

        // 초기화
        public static synchronized RedisUtil initialize(String host, int port, int timeout) {
            if (instance != null) {
                throw new IllegalStateException("이미 초기화된 객체입니다");
            }

            redisClient = RedisClient.create("redis://"+host+":"+port+"/0?timeout="+timeout);
            instance = new RedisUtil();
            return instance;
        }

        public void set(String key, String value) {
            connection = redisClient.connect();
            commands = connection.sync();
            commands.set(key, value);
            connection.close();
        }

        public String get(String key) {
            connection = redisClient.connect();
            commands = connection.sync();
            String value = commands.get(key);
            connection.close();
            return value;
        }

        public long del(String key){
            connection = redisClient.connect();
            commands = connection.sync();
            long count = commands.del(key);
            connection.close();
            return count;
        }
        // ... 여러 메서드
    }
    ```

- lettuce를 사용한 redis stream

    ```java
    // RedisPublisher.java
        public StatefulRedisPubSubConnection<String, String> getPublisher(){
            return redisClient.connectPubSub();
        }

        public void publish(String channel, String value){
            connection = redisClient.connect();
            commands = connection.sync();
            commands.publish(channel, value);
            connection.close();
        }
    ```

    ```java
    // Main.java의 main메서드
      StatefulRedisPubSubConnection<String, String> publisherConn = redisUtil.getPublisher();
      publisherConn.addListener(new RedisPubSubAdapter<String, String>() {
          @Override
          public void message(String channel, String message) {
              System.out.println(channel + ":" + message);
          }
      });
      RedisPubSubCommands<String, String> sync = publisherConn.sync();
      sync.subscribe(worldChannel);
      redisUtil.publish(worldChannel, "hello");
      redisUtil.publish(worldChannel, "world");
    ```

  출처: [https://redislabs.com/blog/getting-started-with-redis-streams-and-java/](https://redislabs.com/blog/getting-started-with-redis-streams-and-java/)