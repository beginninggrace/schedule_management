package com.sparta.schedule_management.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)// 얘가 알아서 object로 응답할 때 필드가 null인 애들이 있으며 JSON으로 파싱할 때, null인 얘들은 빼고 받는 거
public class UserResponse {
    private String message; // 응답 메세지
    private Integer statusCode; // 상태 코드
}
