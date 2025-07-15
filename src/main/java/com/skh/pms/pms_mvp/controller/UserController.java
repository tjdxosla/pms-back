package com.skh.pms.pms_mvp.controller;

import com.skh.pms.pms_mvp.dto.UserCreateRequest;
import com.skh.pms.pms_mvp.dto.UserResponse;
import com.skh.pms.pms_mvp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "사용자", description = "사용자 관리 API")
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "사용자 생성", description = "새로운 사용자를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "사용자 생성 성공",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = Object.class)))
    })
    public ResponseEntity<UserResponse> createUser(
            @Parameter(description = "사용자 생성 요청 정보", required = true,
                    schema = @Schema(implementation = UserCreateRequest.class))
            @Valid @RequestBody UserCreateRequest request) {
        log.info("사용자 생성 요청: {}", request.getUsername());

        try {
            UserResponse createdUser = userService.createUser(request);
            log.info("사용자 생성 성공: {}", createdUser.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (IllegalArgumentException e) {
            log.error("사용자 생성 실패: {}", e.getMessage());
            throw e;
        }
    }
}