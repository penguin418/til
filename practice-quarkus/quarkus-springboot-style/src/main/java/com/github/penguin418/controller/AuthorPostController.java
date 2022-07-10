package com.github.penguin418.controller;

import com.github.penguin418.model.entity.Post;
import com.github.penguin418.service.PostService;
import lombok.RequiredArgsConstructor;
import org.jboss.logging.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.PathParam;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/author-post")
public class AuthorPostController {
    final PostService postService;
    final Logger log = Logger.getLogger(AuthorPostController.class);

    @GetMapping("/{author}")
    public ResponseEntity<Page<Post>> pagePost(
            @PathParam("author") String author,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(postService.pagePostByAuthor(author, PageRequest.of(page, size)));
    }
}