package com.services.authservice.service.impl;

import com.services.authservice.exception.LoginException;
import com.services.authservice.exception.RegistrationException;
import com.services.authservice.model.User;
import com.services.authservice.repository.UserRepository;
import com.services.authservice.service.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(String username, String userSecret) {
        if(userRepository.findByUsername(username)!= null) {
            throw new RegistrationException("User with id: " + username + " already registered");
        }
        String hash = BCrypt.hashpw(userSecret, BCrypt.gensalt());
        User user = new User();
        user.setUsername(username);
        user.setHash(hash);
        userRepository.save(user);
    }

    @Override
    public void checkCredentials(String username, String userSecret) {
        Optional<User> optionalUserEntity = userRepository.findByUsername(username);
        if (optionalUserEntity.isEmpty())
            throw new LoginException("User with id: " + username + " not found");

        User user = optionalUserEntity.get();

        if (!BCrypt.checkpw(userSecret, user.getHash()))
            throw new LoginException("Secret is incorrect");
    }
}
