package com.epam.userservice.security;

import com.epam.userservice.entity.User;
import com.epam.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> OptionalUser = userRepository.findUserByUsername(username);
        return OptionalUser.map(UserDetailsImpl::new).orElse(null);
    }
}
