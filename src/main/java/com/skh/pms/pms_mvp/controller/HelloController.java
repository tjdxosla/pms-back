package com.skh.pms.pms_mvp.controller;

import com.skh.pms.pms_mvp.dto.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "헬로", description = "기본 테스트 API")
@Slf4j
public class HelloController {

    private static final String HELLO_MESSAGE = "Hello World";

    @GetMapping("/hello")
    @Operation(summary = "Hello 메시지", description = "간단한 Hello World 메시지를 반환합니다.")
    @ApiResponse(responseCode = "200", description = "성공",
            content = @Content(schema = @Schema(implementation = MessageResponse.class)))
    public ResponseEntity<MessageResponse> hello() {
        log.info("Hello 엔드포인트 호출됨");
        return ResponseEntity.ok(new MessageResponse(HELLO_MESSAGE));
    }
}