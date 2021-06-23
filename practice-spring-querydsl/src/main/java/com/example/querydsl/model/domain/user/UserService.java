package com.example.querydsl.model.domain.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(User user){
        userRepository.save(user);
        return user;
    }

    public List<User> findUserMatchConditions(String usernameLike, Integer minimumPower) {
        return userRepository.findByConditions(usernameLike, minimumPower);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
