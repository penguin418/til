package com.github.penguin418.practice04_codec;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class ExamplePOJO1Codec implements MessageCodec<ExamplePOJO1,ExamplePOJO1> {
    @Override
    public void encodeToWire(Buffer buffer, ExamplePOJO1 examplePOJO1) {
        final JsonObject data = new JsonObject();
        data.put("id1", examplePOJO1.id1);
        data.put("name1", examplePOJO1.name1);

        final String dataStr = data.encode();
        final int dataLength = dataStr.getBytes().length;

        buffer.appendInt(dataLength);
        buffer.appendString(dataStr);
    }

    @Override
    public ExamplePOJO1 decodeFromWire(int pos, Buffer buffer) {
        final int dataLength = buffer.getInt(pos);
        final String dataStr = buffer.getString(pos+Integer.BYTES, pos+Integer.BYTES+dataLength);

        JsonObject data = new JsonObject(dataStr);

        // We can finally create custom message object
        return new ExamplePOJO1(data.getInteger("id1"), data.getString("name1"));
    }

    @Override
    public ExamplePOJO1 transform(ExamplePOJO1 examplePOJO1) {
        // 복사를 제공하는 이유는 서로 다른 verticle 간에 메모리 공유가 이뤄질 수 있기 때문
        return examplePOJO1.copy();
    }

    @Override
    public String name() {
        return ExamplePOJO1Codec.class.getSimpleName();
    }

    @Override
    public byte systemCodecID() {
        return -1; // 유저가 생성한 코덱의 경우 -1 (인터페이스 참조)
    }
}
