package com.skh.pms.pms_mvp.controller;

import com.skh.pms.pms_mvp.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final JwtTokenProvider jwtTokenProvider;

    //dummyData
    private final String dummyusername = "user";
    private final String dummypassword = "1234";
    private final String dummyrole = "worker";

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {

        String username = request.get("username");
        String password = request.get("password");

        log.info("username = {}, password = {}", username, password);

        //validate dummyData
        if(dummyusername.equals(username) && password.equals(dummypassword)) {
            String token = jwtTokenProvider.createToken(username, dummyrole);
            return ResponseEntity.ok(Map.of("token", token));
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid username or password"));
        }
    }
}
