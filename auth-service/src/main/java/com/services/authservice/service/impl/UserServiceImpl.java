package com.services.authservice.service.impl;

import com.services.authservice.exception.AttemptsLimitException;
import com.services.authservice.exception.LoginException;
import com.services.authservice.exception.RegistrationException;
import com.services.authservice.model.Attempt;
import com.services.authservice.model.User;
import com.services.authservice.repository.AttemptRepository;
import com.services.authservice.repository.UserRepository;
import com.services.authservice.service.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static com.services.authservice.exception.ExceptionMessageConstant.PASSWORD_INCORRECT_MESSAGE;
import static com.services.authservice.exception.ExceptionMessageConstant.TOO_MANY_SIGN_ATTEMPTS_MESSAGE;
import static com.services.authservice.exception.ExceptionMessageConstant.USERNAME_ALREADY_IN_USE_MESSAGE;
import static com.services.authservice.exception.ExceptionMessageConstant.USERNAME_NOT_FOUND_MESSAGE;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AttemptRepository attemptRepository;

    @Value("${max-tries}")
    private int maxAttempts;

    @Value("${ban-time}")
    private Long expireTime;

    public UserServiceImpl(UserRepository userRepository, AttemptRepository attemptRepository) {
        this.userRepository = userRepository;
        this.attemptRepository = attemptRepository;
    }

    @Override
    public void register(String username, String userSecret) {

        if (userRepository.findByUsername(username).isPresent()) {
            throw new RegistrationException(String.format(USERNAME_ALREADY_IN_USE_MESSAGE, username));
        }

        String hash = BCrypt.hashpw(userSecret, BCrypt.gensalt());
        User user = new User();
        user.setUsername(username);
        user.setHash(hash);
        userRepository.save(user);
    }

    @Override
    public void checkCredentials(String username, String userSecret) {

        int counter = attemptRepository.countFailAttempts(LocalDateTime.now().minus(expireTime, ChronoUnit.SECONDS), username);
        if (counter >= maxAttempts) {
            throw new AttemptsLimitException(String.format(TOO_MANY_SIGN_ATTEMPTS_MESSAGE, expireTime / 60));
        }

        User user = userRepository.findByUsername(username).orElseThrow(() -> new LoginException(String.format(USERNAME_NOT_FOUND_MESSAGE, username)));

        if (!BCrypt.checkpw(userSecret, user.getHash())) {
            Attempt attempt = new Attempt();
            attempt.setUsername(username);
            LocalDateTime creationDateTime = LocalDateTime.now();
            attempt.setCreationDateTime(creationDateTime);
            attemptRepository.save(attempt);

            throw new LoginException(PASSWORD_INCORRECT_MESSAGE);
        } else {
            attemptRepository.deleteAttemptsByUsername(username);
        }
    }

    @Override
    public void checkUsername(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RegistrationException(String.format(USERNAME_ALREADY_IN_USE_MESSAGE, username));
        }
    }
}
