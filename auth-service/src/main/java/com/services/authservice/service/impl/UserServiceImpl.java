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
        User user = new User(username, hash);
        userRepository.save(user);
    }

    @Override
    public void checkCredentials(String clientId, String clientSecret) {
        Optional<User> optionalUserEntity = userRepository.findById(clientId);
        if (optionalUserEntity.isEmpty())
            throw new LoginException("Client with id: " + clientId + " not found");

        User user = optionalUserEntity.get();

        if (!BCrypt.checkpw(clientSecret, user.getHash()))
            throw new LoginException("Secret is incorrect");
    }
}
