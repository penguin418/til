package com.example.practiceehcache.model.entity;

import lombok.AllArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * 베스트 포스팅
 */
@Entity
@AllArgsConstructor
public class Post {
    @Id
    private Long id; // 아이디

    private String title; // 제목

    private String author; // 작성자

    @Column(columnDefinition = "TEXT")
    private String content; // 내용

    private String slug; // url 슬러그

    private LocalDateTime regDtm; // 등록일

    private LocalDateTime modDtm; // 등록일
}
