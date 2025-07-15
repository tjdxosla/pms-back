package com.skh.pms.pms_mvp.dto;

import com.skh.pms.pms_mvp.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "사용자 응답 DTO")
public class UserResponse {

    @Schema(description = "사용자 ID", example = "1")
    private Long id;

    @Schema(description = "사용자 아이디", example = "john_doe")
    private String username;

    @Schema(description = "이름", example = "홍길동")
    private String name;

    @Schema(description = "역할", example = "user")
    private String role;

    @Schema(description = "생성 일시", example = "2025-07-16T14:30:00")
    private LocalDateTime createdAt;

    public static UserResponse fromEntity(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
