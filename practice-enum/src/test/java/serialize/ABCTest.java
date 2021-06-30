package serialize;

import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ABCTest {
    @Test
    public void test(){
        Gson gson = new Gson();
        ABC abc = ABC.A;
        String abcJson = gson.toJson(abc);

        ABC abc1 = gson.fromJson(abcJson, ABC.class);

        Assertions.assertEquals(abc, abc1);
    }
}