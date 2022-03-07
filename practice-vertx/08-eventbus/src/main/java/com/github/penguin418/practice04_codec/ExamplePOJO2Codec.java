package com.github.penguin418.practice04_codec;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.json.JsonObject;

public class ExamplePOJO2Codec implements MessageCodec<ExamplePOJO2,ExamplePOJO2> {
    @Override
    public void encodeToWire(Buffer buffer, ExamplePOJO2 ExamplePOJO2) {
        final JsonObject data = new JsonObject();
        data.put("id2", ExamplePOJO2.id2);
        data.put("name2", ExamplePOJO2.name2);

        final String dataStr = data.encode();
        final int dataLength = dataStr.getBytes().length;

        buffer.appendInt(dataLength);
        buffer.appendString(dataStr);
    }

    @Override
    public ExamplePOJO2 decodeFromWire(int pos, Buffer buffer) {
        final int dataLength = buffer.getInt(pos);
        final String dataStr = buffer.getString(pos+Integer.BYTES, pos+Integer.BYTES+dataLength);

        JsonObject data = new JsonObject(dataStr);

        // We can finally create custom message object
        return new ExamplePOJO2(data.getInteger("id2"), data.getString("name2"));
    }

    @Override
    public ExamplePOJO2 transform(ExamplePOJO2 examplePOJO2) {
        // 복사를 제공하는 이유는 서로 다른 verticle 간에 메모리 공유가 이뤄질 수 있기 때문
        return examplePOJO2.copy();
    }

    @Override
    public String name() {
        return ExamplePOJO2Codec.class.getSimpleName();
    }

    @Override
    public byte systemCodecID() {
        return -1; // 유저가 생성한 코덱의 경우 -1 (인터페이스 참조)
    }
}
