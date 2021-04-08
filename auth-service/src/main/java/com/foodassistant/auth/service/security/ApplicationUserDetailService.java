package com.foodassistant.auth.service.security;

import com.foodassistant.auth.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public ApplicationUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository.findById(s).orElseThrow(() -> new UsernameNotFoundException(s));
    }
}
