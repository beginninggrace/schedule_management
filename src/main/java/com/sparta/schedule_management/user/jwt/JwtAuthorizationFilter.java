package com.sparta.schedule_management.user.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.schedule_management.user.dto.response.UserResponse;
import com.sparta.schedule_management.user.security.UserDetailsImpl;
import com.sparta.schedule_management.user.security.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Slf4j(topic = "JWT 검증 및 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter { // OncePerRequestFilter : 요청이 한 번 올 때마다 filter를 태우겠다는 의미

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = jwtUtil.resolveToken(request); // bearer 글자가 짤린 순수 token 가져오기

        if (Objects.nonNull(token)) { // token이 null이 아니면 수행 - (token != null)도 가능
            if (jwtUtil.validateToken(token)) { // validateToken에 exception이 터지지 않았다면 정상적인 토큰이 여기로 들어옴
                Claims info = jwtUtil.getUserInfoFromToken(token); // 유저정보 뽑아오기 - Claims이라는게 유저정보를 담고있다고 보면 됨(토큰에 뭘 넣느냐에 따라 Claim에 뭐가 나올지 결정된다)

                // 인증정보에 유저정보(username) 넣기
                String username = info.getSubject();

                // username -> user 조회
                SecurityContext context = SecurityContextHolder.createEmptyContext(); // SecurityContextHolder : 현재 쓰레드에서 공용으로 사용할 수 있는 보안 컨텍스트 정보(보안 문맥정보)를 들고 있는 holder

                // -> userDetails 에 담고
                UserDetailsImpl userDetails = userDetailsServiceImpl.getUserDetails(username);

                // -> authentication의 principal 에 담고
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // -> securityContent 에 담고
                context.setAuthentication(authentication);

                // -> SecurityContextHolder 에 담고
                SecurityContextHolder.setContext(context);
                // -> 이제 @AuthenticationPrincipal 로 조회할 수 있음


            } else {
                // 인증정보가 존재하지 않을때
                UserResponse responseDto = new UserResponse("토큰이 유효하지 않습니다.", HttpStatus.BAD_REQUEST.value());
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 응답값에 Status 세팅
                response.setContentType("application/json; charset=UTF-8"); // body가 깨지지 않기 위해 타입 정의
                response.getWriter().write(objectMapper.writeValueAsString(responseDto)); // responseDto를 그냥 넣어주면 안되고 objectMapper를 통해 mapping해줘야 함 - .write 들어가면 ()안에 string으로 받고 있음 즉, 객체 그대로로는 문자열(숫자)를 넣어줄 수 없어서
                // 이렇게 하면 응답body가 JSON형태의 문자열로 바뀌어서 들어간다고 보면 된다.
                return; // filter를 타지않고 바로 에러응답을 바로 return할 수 있도록 처리
            }
        }
        // 로그인이 끝났으면 doFilter로 다음 로그인 처리할 수 있도록 유도
        filterChain.doFilter(request, response);
    }
}
