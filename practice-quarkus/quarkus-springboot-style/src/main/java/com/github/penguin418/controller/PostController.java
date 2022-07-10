package com.github.penguin418.controller;

import org.jboss.logging.Logger;
import com.github.penguin418.model.entity.Post;
import com.github.penguin418.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
public class PostController{
    final PostService postService;
    final Logger log = Logger.getLogger(PostController.class);

    @GetMapping()
    public ResponseEntity<Page<Post>> pagePost(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(postService.pagePost(PageRequest.of(page, size)));
    }

    @GetMapping()
    @RequestMapping("/all")
    public List<Post> all(){
        return postService.listAll();
    }
}