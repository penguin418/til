package com.github.penguin418.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class JsonPlaceHolderErrorTest {
    @Test
    public void ERROR_CODE_DUP_TEST(){
        List<Integer> errorCodes = Arrays.stream(JsonPlaceHolderError.values()).map(JsonPlaceHolderError::getErrorCode).collect(Collectors.toList());
        Assertions.assertEquals(errorCodes.size(), errorCodes.stream().distinct().count());
    }
    @Test
    public void ERROR_MSG_DUP_TEST(){
        List<String> errorCodes = Arrays.stream(JsonPlaceHolderError.values()).map(JsonPlaceHolderError::getMessage).collect(Collectors.toList());
        Assertions.assertEquals(errorCodes.size(), errorCodes.stream().distinct().count());
    }
}