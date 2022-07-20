package com.services.animalservice.service.impl;

import com.services.animalservice.model.User;
import com.services.animalservice.repository.UserRepository;
import com.services.animalservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Long getIdByUsername(String userName) {
//        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(userName).get().getId();
    }

    @Override
    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with username " + username + " doesn't exist"));
        return user;
    }

    @Override
    public User getUserById(Long id) {
        userRepository.findById(id).orElseThrow();
        return userRepository.findById(id).orElseThrow();
    }
}
