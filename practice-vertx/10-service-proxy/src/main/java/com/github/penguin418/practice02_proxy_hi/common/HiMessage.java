package com.github.penguin418.practice02_proxy_hi.common;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
@DataObject // code gen 위해 필수
public class HiMessage {
    private String username;
    private String message;
    private String responder;

    // @DataObject 위해 필수
    public HiMessage(JsonObject data){
        this.username = data.getString("username");
        this.message = data.getString("message");
        this.responder = data.getString("responder");
    }

    // @DataObject 위해 필수
    public JsonObject toJson(){
        return new JsonObject().put("username", username).put("message", message).put("responder", responder);
    }
}
