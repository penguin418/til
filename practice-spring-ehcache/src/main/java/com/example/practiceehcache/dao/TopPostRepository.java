package com.example.practiceehcache.dao;

import com.example.practiceehcache.model.entity.TopPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopPostRepository extends JpaRepository<TopPost, Long> {
}
