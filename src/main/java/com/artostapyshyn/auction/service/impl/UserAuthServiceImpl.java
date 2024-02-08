package com.artostapyshyn.auction.service.impl;

import com.artostapyshyn.auction.dto.LoginDto;
import com.artostapyshyn.auction.dto.SignUpDto;
import com.artostapyshyn.auction.service.UserAuthService;
import com.artostapyshyn.auction.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {

    private final UserService userService;

    @Override
    public ResponseEntity<Map<String, Object>> signUpUser(SignUpDto signUpDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return handleValidationErrors(bindingResult);
        }

        Map<String, Object> responseMap = new HashMap<>();

        String token = userService.signUpUserAndGenerateToken(signUpDto);
        responseMap.put("email", signUpDto.getEmail());
        responseMap.put("message", "Account created successfully");
        responseMap.put("token", token);
        return ResponseEntity.ok(responseMap);
    }

    @Override
    public ResponseEntity<Map<String, Object>> loginUser(LoginDto loginDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return handleValidationErrors(bindingResult);
        }

        Map<String, Object> responseMap = new HashMap<>();

        String token = userService.loginAndGenerateToken(loginDto);
        responseMap.put("message", "Logged In");
        responseMap.put("token", token);
        return ResponseEntity.ok(responseMap);
    }

    private ResponseEntity<Map<String, Object>> handleValidationErrors(BindingResult bindingResult) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("error", "Validation failed");
        responseMap.put("errors", bindingResult.getAllErrors());
        return ResponseEntity.badRequest().body(responseMap);
    }
}
