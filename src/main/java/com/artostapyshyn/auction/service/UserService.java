package com.artostapyshyn.auction.service;

import com.artostapyshyn.auction.dto.LoginDto;
import com.artostapyshyn.auction.dto.SignUpDto;
import com.artostapyshyn.auction.dto.UserEditDto;
import com.artostapyshyn.auction.model.User;

public interface UserService {
    User findById(Long id);

    User findByEmail(String email);

    User save(User User);

    User getAuthenticatedPerson();

    void updateUser(User existingUser, UserEditDto updatedUser);

    String signUpUserAndGenerateToken(SignUpDto signUpDto);

    String loginAndGenerateToken(LoginDto loginDto);
}
