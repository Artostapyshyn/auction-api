package com.artostapyshyn.auction.service;

import com.artostapyshyn.auction.dto.LoginDto;
import com.artostapyshyn.auction.dto.SignUpDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.Map;

public interface UserAuthService {
    ResponseEntity<Map<String, Object>> signUpUser(SignUpDto signUpDto, BindingResult bindingResult);

    ResponseEntity<Map<String, Object>> loginUser(LoginDto loginDto, BindingResult bindingResult);
}
