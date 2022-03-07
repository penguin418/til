package com.github.penguin418.practice04_codec;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ExamplePOJO2 {
    public Integer id2;
    public String name2;

    // 복사본을 만들 때 사용
    public ExamplePOJO2 copy(){
        return new ExamplePOJO2(id2,name2);
    }
}
