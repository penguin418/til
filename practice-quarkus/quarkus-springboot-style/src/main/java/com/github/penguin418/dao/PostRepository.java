package com.github.penguin418.dao;

import com.github.penguin418.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public interface PostRepository extends JpaRepository<Post, Long> {
    public Page<Post> findByAuthor(String author, Pageable pageable);
}
