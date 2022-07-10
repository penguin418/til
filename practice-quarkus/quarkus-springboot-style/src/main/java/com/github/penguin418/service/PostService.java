package com.github.penguin418.service;

import org.jboss.logging.Logger;
import com.github.penguin418.dao.PostRepository;
import com.github.penguin418.model.entity.Post;
import lombok.RequiredArgsConstructor;
import org.jboss.logging.annotations.Pos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.enterprise.context.ApplicationScoped;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class PostService {
    final Logger log = Logger.getLogger(PostService.class);
    final PostRepository postRepository;

    public Page<Post> pagePost(Pageable pageable){
        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> postList = posts.stream().toList();
        for(Post p : postList){
            log.info("post" +  p.toString());
        }
        log.info("page post request: " +  pageable);
        return posts;
    }

    public Page<Post> pagePostByAuthor(String author, Pageable pageable){
        return postRepository.findByAuthor(author, pageable);
    }

    public List<Post> listAll(){
        return postRepository.findAll();
    }
}