package com.services.animalservice.service.impl;

import com.services.animalservice.model.User;
import com.services.animalservice.repository.UserRepository;
import com.services.animalservice.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @Override
//    public Long getIdByUsername(String userName) {
//        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
//        return userRepository.findUserByUsername(userName).get().getId();
//    }

    @Override
    public User getUserById(Long id) {
        userRepository.findById(id).orElseThrow();
        return userRepository.findById(id).orElseThrow();
    }
}
