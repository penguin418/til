package com.github.penguin418;

import com.github.penguin418.config.RestClient;
import com.github.penguin418.model.Tnd;
import com.google.gson.reflect.TypeToken;
import kong.unirest.HttpResponse;

import java.util.List;

public class TndService {
    public final static String GENERATE_TND_COPY_URI = "/api/v1/generate_tnd";

    public Tnd generateTndCopy(String product, String target){
        HttpResponse<? extends Tnd> response = RestClient.getInstance().getAsObject(GENERATE_TND_COPY_URI + "?product="+product + "&target=" + target, Tnd.class);
        return response.isSuccess() ? response.getBody() : null;
    }
    public Tnd generateTndCopy(String product, String target, String audience){
        HttpResponse<? extends Tnd> response = RestClient.getInstance().getAsObject(GENERATE_TND_COPY_URI + "?product="+product + "&target=" + target + "&audience="+audience, Tnd.class);
        return response.isSuccess() ? response.getBody() : null;
    }
}
