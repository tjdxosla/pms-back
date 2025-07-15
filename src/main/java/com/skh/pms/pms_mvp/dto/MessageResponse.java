package com.skh.pms.pms_mvp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "메시지 응답 DTO")
public class MessageResponse {

    @Schema(description = "응답 메시지", example = "작업이 성공적으로 완료되었습니다.")
    private String message;
}
