package com.skh.pms.pms_mvp.controller;

import com.skh.pms.pms_mvp.dto.LoginResponse;
import com.skh.pms.pms_mvp.dto.MessageResponse;
import com.skh.pms.pms_mvp.entity.User;
import com.skh.pms.pms_mvp.security.JwtTokenProvider;
import com.skh.pms.pms_mvp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "인증", description = "인증 관련 API")
public class LoginController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    //dummyData - 임시 데이터(사용자 없을 때 폴백용)
    private final String dummyusername = "user";
    private final String dummypassword = "1234";
    private final String dummyrole = "worker";

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "사용자 아이디와 비밀번호로 로그인하여 JWT 토큰을 발급받습니다.")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "200", description = "로그인 성공", 
                    content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = "인증 실패", 
                    content = @Content(schema = @Schema(implementation = MessageResponse.class)))
    })
    public ResponseEntity<?> login(
            @Parameter(description = "로그인 요청 정보", required = true) 
            @RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        log.info("로그인 시도: {}", username);

        // 실제 사용자 확인
        Optional<User> userOptional = userService.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                String token = jwtTokenProvider.createToken(username, user.getRole());
                LoginResponse response = LoginResponse.builder()
                        .token(token)
                        .username(user.getUsername())
                        .name(user.getName())
                        .role(user.getRole())
                        .build();

                log.info("로그인 성공: {}", username);
                return ResponseEntity.ok(response);
            }
        }

        // 더미 데이터 체크 (개발 중 테스트용)
        if (dummyusername.equals(username) && password.equals(dummypassword)) {
            String token = jwtTokenProvider.createToken(username, dummyrole);
            LoginResponse response = LoginResponse.builder()
                    .token(token)
                    .username(dummyusername)
                    .name("테스트 사용자")
                    .role(dummyrole)
                    .build();
            return ResponseEntity.ok(response);
        }

        log.warn("로그인 실패: {}", username);
        MessageResponse errorResponse = new MessageResponse("아이디 또는 비밀번호가 올바르지 않습니다");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "사용자 로그아웃을 처리합니다.")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "200", description = "로그아웃 성공", 
                    content = @Content(schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", 
                    content = @Content(schema = @Schema(implementation = MessageResponse.class)))
    })
    public ResponseEntity<?> logout(
            @Parameter(description = "JWT 토큰이 포함된 요청", required = true)
            HttpServletRequest request) {
        // 요청 헤더에서 JWT 토큰 추출
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            // 실제 프로덕션 환경에서는 여기서 토큰을 블랙리스트에 추가하거나
            // Redis 등을 사용하여 무효화된 토큰을 관리할 수 있습니다.

            log.info("사용자 로그아웃 처리됨");
            MessageResponse response = new MessageResponse("로그아웃되었습니다.");
            return ResponseEntity.ok(response);
        }

        MessageResponse errorResponse = new MessageResponse("유효하지 않은 토큰입니다.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
