package com.github.penguin418.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor // for jackson data-bind
@Setter // for jackson data-bind
@ToString
public class Todo {
    private Integer id;
    private Integer userId;
    private String title;
    private boolean completed;
}
