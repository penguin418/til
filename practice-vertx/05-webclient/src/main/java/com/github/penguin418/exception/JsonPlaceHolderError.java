package com.github.penguin418.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
public enum JsonPlaceHolderError {
    BAD_PARSING(100, "파싱 실패함"),
    INVALID_URL(101,"존재하지 않는 url"),
    INVALID_REQUEST_MISSING_REQUIRED_INFORMATION(103, "잘못된 요청: 필수정보 누락"),
    INVALID_REQUEST_VALIDATION_FAILED(103, "잘못된 요청: 입력 문제"),
    INVALID_REQUEST_TRY_TO_CREATE_OBJECT_ALREADY_EXISTS(104, "잘못된 요청: 이미 존재하는 객체를 생성하여 함"),
    INVALID_PERMISSION_MISSING_AUTHORIZATION(201, "잘못된 권한: 권한 정보 누락"),
    INVALID_PERMISSION_INSUFFICIENT_PERMISSION(202, "잘못된 권한: 권한이 부족함"),
    OBJECT_NOT_FOUND(301, "대상을 찾을 수 없음"),
    SERVER_BUSY(401, "서버가 바쁨"),
    SERVER_DOWN(402, "서버 죽음"),
    UNKNOWN(999,"모름");

    private final int errorCode;
    private final String message;

    public JsonPlaceHolderException exception(String detail){
        return new JsonPlaceHolderException(this, detail);
    }

    public JsonPlaceHolderException exception(){
        return new JsonPlaceHolderException(this, "");
    }
}
