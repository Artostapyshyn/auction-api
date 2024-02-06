package com.artostapyshyn.auction.service;

import com.artostapyshyn.auction.dto.LoginDto;
import com.artostapyshyn.auction.dto.SignUpDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface UserAuthService {
    ResponseEntity<Map<String, Object>> signUpUser(SignUpDto signUpDto);
    ResponseEntity<Map<String, Object>> loginUser(LoginDto loginDto);
}
