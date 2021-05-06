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