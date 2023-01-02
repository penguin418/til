package com.github.penguin418.config;

import com.github.penguin418.model.Tnd;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import lombok.Getter;
import lombok.Setter;

public class RestClient {
    @Getter
    private static class Instance{
        private static final RestClient singleton = new RestClient();
    }

    public static RestClient getInstance(){
        return Instance.singleton;
    }

    RestClient(){
    }

    @Setter
    private String baseUrl;

    @Setter
    private int timeout = 30;

    public HttpResponse<? extends Tnd> getAsObject(String path, Class<? extends Tnd> classType) throws UnirestException {
        return Unirest
                .get(this.baseUrl + path)
                .socketTimeout(timeout)
                .asObject(classType);
    }
}
