public class Main {
    public static void main(String[] args) {
        RedisUtil redisUtil = RedisUtil.initialize("127.0.0.1", 6379, 1000);
        String worldSet = "world";
        redisUtil.set("hello", worldSet);
        String worldGet = redisUtil.get("hello");

        assert worldSet.equals(worldGet);

    }
}
