package com.artostapyshyn.auction.controller;

import com.artostapyshyn.auction.dto.LoginDto;
import com.artostapyshyn.auction.dto.SignUpDto;
import com.artostapyshyn.auction.service.UserAuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Validated
@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(maxAge = 3600, origins = "*")
@AllArgsConstructor
public class AuthController {
    private final UserAuthService userAuthService;

    @Operation(summary = "Login user")
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@Valid @RequestBody LoginDto loginDto, BindingResult bindingResult) {
        return userAuthService.loginUser(loginDto, bindingResult);
    }

    @Operation(summary = "Sign up user")
    @PostMapping("/sign-up")
    public ResponseEntity<Map<String, Object>> signUpUser(@Valid @RequestBody SignUpDto signUpDto, BindingResult bindingResult) {
        return userAuthService.signUpUser(signUpDto, bindingResult);
    }

}
