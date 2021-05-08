import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.pubsub.RedisPubSubAdapter;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.async.RedisPubSubAsyncCommands;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;

public class Main {
    public static void main(String[] args) {
        RedisPublisher redisUtil = RedisPublisher.initialize("127.0.0.1", 6379, 1000);
        String worldChannel = "world";
        // Subscribe channel
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
    }
}
