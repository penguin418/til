package serialize;

import com.google.gson.*;
import com.google.gson.annotations.JsonAdapter;

import java.lang.reflect.Type;

@JsonAdapter(ABC.Serializer.class)
public enum ABC {
    A(0),

    B(1),
    C(2);

    private final Integer number;

    ABC(Integer number) {
        this.number = number;
    }

    static class Serializer implements JsonSerializer<ABC>, JsonDeserializer<ABC>{

        @Override
        public ABC deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            switch (json.getAsNumber().intValue()){
                case 0: return A;
                case 1: return B;
                case 2: return C;
                default: return A;
            }
        }

        @Override
        public JsonElement serialize(ABC src, Type typeOfSrc, JsonSerializationContext context) {
            return context.serialize(src.number);
        }
    }
}
