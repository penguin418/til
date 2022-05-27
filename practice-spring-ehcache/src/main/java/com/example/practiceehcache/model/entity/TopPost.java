package com.example.practiceehcache.model.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * 베스트 포스팅
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @ToString
public class TopPost {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 아이디

    private String title; // 제목

    private String author; // 작성자

    @CreationTimestamp
    private LocalDateTime regDtm; // 등록일

    private String slug; // url 슬러그
}
