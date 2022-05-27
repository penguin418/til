package com.example.practiceehcache.service;

import com.example.practiceehcache.dao.TopPostRepository;
import com.example.practiceehcache.model.entity.TopPost;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardService {
    private final TopPostRepository topPostRepository;

    @Cacheable(value="findCachedTopPost", key = "#pageable.pageNumber") // 해당 캐시 사용
    public Page<TopPost> findCachedTopPost(Pageable pageable){
        log.info("findCachedTopPost: page={}", pageable);
        return topPostRepository.findAll(pageable);
    }

    public Page<TopPost> findUncachedTopPost(Pageable pageable){
        log.info("findUncachedTopPost: page={}", pageable);
        return topPostRepository.findAll(pageable);
    }

    @CachePut(value = "findCachedTopPost", key = "#topPost.id")//해당 캐시 추가
    public void addTopPost(TopPost topPost){
        log.info("addTopPost: topPost={}", topPost);
        TopPost newTopPost = topPostRepository.save(topPost);
    }

    @CacheEvict(value = "findCachedTopPost", allEntries = true) //해당 캐시 삭제
    public void updateTopPost(TopPost topPost){
        log.info("updateTopPost");
        topPostRepository.save(topPost);
    }
}
