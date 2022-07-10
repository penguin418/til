package com.github.penguin418.model.entity;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(indexes=@Index(columnList="author"))
public class Post {

    public Post(){}

    @Id
    @GeneratedValue
    private Long id;
    /**
     * 제목
     */
    private String title;
    
    /**
     * 작성자
     */
    private String author;
    
    /**
     * 내용
     */
    private String content;
    
    /**
     * 등록일
     */
    private LocalDateTime regDtm;
    
    /**
     * 수정일
     */
    private LocalDateTime modDtm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getRegDtm() {
        return regDtm;
    }

    public void setRegDtm(LocalDateTime regDtm) {
        this.regDtm = regDtm;
    }

    public LocalDateTime getModDtm() {
        return modDtm;
    }

    public void setModDtm(LocalDateTime modDtm) {
        this.modDtm = modDtm;
    }
}