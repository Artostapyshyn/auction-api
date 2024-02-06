package com.artostapyshyn.auction.service.impl;

import com.artostapyshyn.auction.model.User;
import com.artostapyshyn.auction.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            User existingUser = user.get();
            return new org.springframework.security.core.userdetails.User(
                    existingUser.getEmail(), existingUser.getPassword(), Set.of(existingUser.getRole()));
        } else {
            throw new UsernameNotFoundException("User doesn't exists");
        }
    }

}