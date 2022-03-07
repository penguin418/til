package com.github.penguin418.practice04_codec;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ExamplePOJO1 {
    public Integer id1;
    public String name1;

    // 복사본을 만들 때 사용
    public ExamplePOJO1 copy(){
        return new ExamplePOJO1(id1,name1);
    }
}
