package com.artostapyshyn.auction.service.impl;

import com.artostapyshyn.auction.dto.LoginDto;
import com.artostapyshyn.auction.dto.SignUpDto;
import com.artostapyshyn.auction.dto.UserEditDto;
import com.artostapyshyn.auction.exception.UserNotFoundException;
import com.artostapyshyn.auction.jwt.JwtTokenUtil;
import com.artostapyshyn.auction.model.User;
import com.artostapyshyn.auction.repository.UserRepository;
import com.artostapyshyn.auction.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsServiceImpl userDetailsService;

    private final JwtTokenUtil jwtTokenUtil;

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

    @Override
    public String signUpUserAndGenerateToken(SignUpDto signUpDto) {
        if (userRepository.findByEmail(signUpDto.email()).isPresent()) {
            throw new RuntimeException("User with this email already exists");
        }
        User user = modelMapper.map(signUpDto, User.class);
        user.setPassword(encodePassword(signUpDto.password()));
        userRepository.save(user);
        UserDetails userDetails = userDetailsService.loadUserByUsername(signUpDto.email());
        return jwtTokenUtil.generateToken(userDetails);
    }


    @Override
    public String loginAndGenerateToken(LoginDto loginDto) {
        userRepository.findByEmail(loginDto.email()).orElseThrow(UserNotFoundException::new);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.email());
        return jwtTokenUtil.generateToken(userDetails);
    }

    private String encodePassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
