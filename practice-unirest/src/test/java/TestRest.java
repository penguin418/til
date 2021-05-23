import kong.unirest.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.Future;


public class TestRest {

    private static String BASE_URL = "http://httpbin.org/";

    @Test
    public void testGet() {
        final HttpResponse<String> response = Unirest.get(BASE_URL + "/get").asString();
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    public void testHeader(){
        final HttpResponse<String> response = Unirest.head(BASE_URL + "/get").asString();
        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals("", response.getBody());
    }

    @Test
    public void testPost(){
        final HttpResponse<String> response = Unirest.post(BASE_URL + "/post").asString();
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    public void testPut(){
        final HttpResponse<String> response = Unirest.put(BASE_URL + "/put").asString();
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    public void testDelete(){
        final HttpResponse<String> response = Unirest.delete(BASE_URL + "/delete").asString();
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    public void testRouteParam(){
        final HttpResponse<String> response =
                Unirest.get(BASE_URL + "/status/{code}")
                        .routeParam("code", "200")
                        .asString();
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    public void testAsJson(){
        final HttpResponse<JsonNode> response =
                Unirest.get(BASE_URL + "/anything")
                        .accept("application/json")
                        .asJson();
        Assertions.assertEquals("application/json", response.getHeaders().get("content-type").get(0));
    }
}
