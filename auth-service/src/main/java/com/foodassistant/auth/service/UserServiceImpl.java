package com.foodassistant.auth.service;

import com.foodassistant.auth.entity.User;
import com.foodassistant.auth.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void create(User user) {
        Optional<User> existing = this.userRepository.findById(user.getUsername());
        existing.ifPresent(it -> {throw new IllegalArgumentException("User already exist: " + it.getUsername());});

        String hash = encode.encode(user.getPassword());
        user.setPassword(hash);

        this.userRepository.save(user);

        this.logger.info("New user has been created: {}", user.getUsername());
    }
}