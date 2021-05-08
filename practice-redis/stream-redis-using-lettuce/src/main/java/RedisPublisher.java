import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;

import java.util.Map;

public class RedisPublisher {
    // 싱글턴
    private static RedisPublisher instance = null;
    // 클라이언트
    private static RedisClient redisClient;

    // 단일 작업 커넥션
    private StatefulRedisConnection<String, String> connection;
    private RedisCommands<String, String> commands; // sync용 커맨드
    private RedisAsyncCommands<String, String> asyncCommands; // async용 커맨드
    // 메시지 바디
    private Map<String, String> messageBody;

    // 싱글턴
    public static synchronized RedisPublisher getInstance() throws IllegalAccessException {
        if (instance == null)
            throw new IllegalStateException("초기화되지 않은 객체를 호출하고 있습니다");

        return instance;
    }

    // 초기화
    public static synchronized RedisPublisher initialize(String host, int port, int timeout) {
        if (instance != null) {
            throw new IllegalStateException("이미 초기화된 객체입니다");
        }

        redisClient = RedisClient.create("redis://"+host+":"+port+"/0?timeout="+timeout);
        instance = new RedisPublisher();
        return instance;
    }

    public StatefulRedisPubSubConnection<String, String> getPublisher(){
        return redisClient.connectPubSub();
    }

    public void publish(String channel, String value){
        connection = redisClient.connect();
        commands = connection.sync();
        commands.publish(channel, value);
        connection.close();
    }
}
