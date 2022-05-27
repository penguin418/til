package com.example.practiceehcache;

import com.example.practiceehcache.model.entity.TopPost;
import com.example.practiceehcache.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartListener implements ApplicationListener<ContextRefreshedEvent> {
    private final DashboardService dashboardService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        TopPost topPost = new TopPost();
        topPost.setSlug("/1");
        topPost.setTitle("title");
        topPost.setAuthor("author");

        dashboardService.addTopPost(topPost);
    }
}
