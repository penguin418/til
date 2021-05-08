import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

import java.util.List;

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
    public static synchronized RedisUtil initialize(String host, int port) {
        return initialize(host, port, 1000);
    }

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

    public long del(String key){
        Jedis jedis = jedisPool.getResource();
        long count = jedis.del(key);
        jedis.close();
        return count;
    }
    // ... 여러 메서드

    public void set(String key, String value, int seconds){
        Jedis jedis = jedisPool.getResource();
        jedis.setex(key, seconds, value);
        jedis.close();
    }

    // 리스트
    public void lpush(String key, List<String> list){
        Jedis jedis = jedisPool.getResource();
        list.forEach(value->jedis.lpush(key,value));
        jedis.close();
    }
    public void lset(String key, int index, String value){
        Jedis jedis = jedisPool.getResource();
        jedis.lset(key,index,value);
        jedis.close();
    }
    public List<String> lrange(String key, int start, int stop){
        Jedis jedis = jedisPool.getResource();
        List<String> list = jedis.lrange(key,start, stop);
        jedis.close();
        return list;
    }

    // 큐
    public void lpush(String key, String value){
        Jedis jedis = jedisPool.getResource();
        jedis.lpush(key, value);
        jedis.close();
    }
    public void rpush(String key, String value){
        Jedis jedis = jedisPool.getResource();
        jedis.rpush(key, value);
        jedis.close();
    }
    public String lpop(String key){
        Jedis jedis = jedisPool.getResource();
        String value = jedis.lpop(key);
        jedis.close();
        return value;
    }

    // hset
    // 맵 구조
    public void hset(String key, String field, String value){
        Jedis jedis = jedisPool.getResource();
        jedis.hset(key,field,value);
        jedis.close();
    }

    public String hget(String key, String field){
        Jedis jedis = jedisPool.getResource();
        String value = jedis.hget(key,field);
        jedis.close();
        return value;
    }
}
