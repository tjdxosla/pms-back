package com.skh.pms.pms_mvp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "사용자 생성 요청 DTO")
public class UserCreateRequest {

    @Schema(description = "사용자 아이디", example = "john_doe", required = true)
    @NotBlank(message = "사용자 이름은 필수입니다")
    @Size(min = 3, max = 50, message = "사용자 이름은 3~50자 사이여야 합니다")
    private String username;

    @Schema(description = "비밀번호", example = "password1234", required = true)
    @NotBlank(message = "비밀번호는 필수입니다")
    @Size(min = 4, message = "비밀번호는 최소 4자 이상이어야 합니다")
    private String password;

    @Schema(description = "이름", example = "홍길동", required = true)
    @NotBlank(message = "이름은 필수입니다")
    private String name;

    @Schema(description = "역할", example = "user", required = true, allowableValues = {"admin", "user", "worker"})

    @NotBlank(message = "역할은 필수입니다")
    private String role;
}
