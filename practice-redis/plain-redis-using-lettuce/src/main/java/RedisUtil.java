import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    public void set(String key, String value, int seconds){
        connection = redisClient.connect();
        commands = connection.sync();
        // args 를 사용함
        // nx: 삽입만 허용
        // xx: update만 허용
        // ex: n초 이후 삭제됨
        // px: n milli초 이후 삭제됨
        commands.set(key, value, new SetArgs().ex(seconds));
        connection.close();
    }

    // 리스트
    public void lpush(String key, List<String> list){
        connection = redisClient.connect();
        commands = connection.sync();
        commands.lpush(key, list.toArray(new String[list.size()]));
        connection.close();
    }
    public void lset(String key, int index, String value){
        connection = redisClient.connect();
        commands = connection.sync();
        commands.lset(key, index, value);
        connection.close();
    }
    public List<String> lrange(String key, int start, int stop){
        connection = redisClient.connect();
        commands = connection.sync();
        List<String> list = commands.lrange(key, start, stop);
        connection.close();
        return list;
    }

    // 큐
    public void lpush(String key, String value){
        connection = redisClient.connect();
        commands = connection.sync();
        commands.lpush(key, value);
        connection.close();
    }
    public void rpush(String key, String value){
        connection = redisClient.connect();
        commands = connection.sync();
        commands.rpush(key, value);
        connection.close();
    }
    public String lpop(String key){
        connection = redisClient.connect();
        commands = connection.sync();
        String value = commands.lpop(key);
        connection.close();
        return value;
    }

    // hset
    // 맵 구조
    public void hset(String key, String field, String value){
        connection = redisClient.connect();
        commands = connection.sync();
        commands.hset(key, field, value);
        connection.close();
    }

    public String hget(String key, String field){
        connection = redisClient.connect();
        commands = connection.sync();
        String value = commands.hget(key, field);
        connection.close();
        return value;
    }
}
