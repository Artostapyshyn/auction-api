package com.artostapyshyn.auction.service.impl;

import com.artostapyshyn.auction.dto.UserEditDto;
import com.artostapyshyn.auction.exception.UserNotFoundException;
import com.artostapyshyn.auction.model.User;
import com.artostapyshyn.auction.repository.UserRepository;
import com.artostapyshyn.auction.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getAuthenticatedPerson() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElseThrow(null);
    }

    @Override
    public void updateUser(User existingUser, UserEditDto updatedUser) {
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(updatedUser, existingUser);
        userRepository.save(existingUser);
    }

}
