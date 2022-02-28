package com.github.penguin418.exception;

import lombok.Getter;

@Getter
public class JsonPlaceHolderException extends RuntimeException{
    private final int errorCode;
    private final String detail;

    public JsonPlaceHolderException(JsonPlaceHolderError error, String detail){
        super(error.getMessage());
        this.errorCode = error.getErrorCode();
        this.detail = detail;
    }
}
