package com.services.animalservice.service;

import com.services.animalservice.model.User;

public interface UserService {

//    Long getIdByUsername(String userName);

    User getUserById(Long id);

    public Long getIdByUsername(String userName);

    User getUserByUsername(String username);
}
