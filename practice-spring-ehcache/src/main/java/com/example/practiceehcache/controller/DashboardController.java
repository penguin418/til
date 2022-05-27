package com.example.practiceehcache.controller;

import com.example.practiceehcache.model.entity.TopPost;
import com.example.practiceehcache.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping("/cached-top-post")
    public @ResponseBody ResponseEntity<Page<TopPost>> getCachedTopPost(Pageable pageable){
        return ResponseEntity.ok(dashboardService.findCachedTopPost(pageable));
    }

    @GetMapping("/uncached-top-post")
    public @ResponseBody ResponseEntity<Page<TopPost>> getUncachedTopPost(Pageable pageable){
        return ResponseEntity.ok(dashboardService.findUncachedTopPost(pageable));
    }

    @GetMapping("/update-top-post")
    public void updateTopPost(){
        TopPost topPost = new TopPost();
        topPost.setId(1L);
        topPost.setTitle("new title");
        topPost.setAuthor("new author");
        topPost.setSlug("/newSlug");
        dashboardService.updateTopPost(topPost);
    }
}
