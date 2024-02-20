package com.sparta.schedule_management.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j // log찍기 위함
@Component
public class JwtUtil {

    // Header KEY 값
    public static final String AUTHORIZATION_HEADER = "Authorization";

    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";

    @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey - 토큰을 만들 때 우리만의 key를 만들 수 있도록(properties에 넣은 secret key)
    private String secretKey;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; // 암호화 알고리즘

    private Key key; // 암호화된 key값을 여기에 담아서 사용할 것임 - 위에 String secretKey가 SignatureAlgorithm.HS256로 알고리즘화하여 담은 것

    @PostConstruct // key값을 이 클래스 bean이 생성될 때 같이 만들어질 수 있도록 @PostConstruct 붙임 - 런타임에서 이 bean이 생성될 때 메소드(init)가 호출이 됨
    public void init() { // 실제 우리가 사용할 암호화된 key값
        byte[] bytes = Base64.getDecoder().decode(secretKey); // secret key를 기준으로 Base64로 Decoding을 하고
        key = Keys.hmacShaKeyFor(bytes); // Decoding된 bytes값을 JSON web Token 라이브러리인 Keys의 .hmacShaKeyFor를 통해 암호화된 secret key를 받을 수 있게 됨
    }

    // 토큰 뽑아오기
    public String resolveToken(HttpServletRequest request) { // HttpServletRequest로 요청정보 받기
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER); // bearer붙어있는 Token 뽑아오기
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) { // StringUtils.hasText(bearerToken) : bearerToken이 text를 갖고있는지 확인 & enum인 BEARER_PREFIX의 value값으("bearer ")로 시작하는지
            return bearerToken.substring(7);
        }
        return null; // 그게 아니면 null 응답
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token); // key값으로 암호화한 애인지 검증하겠다 - 저기에 key값 넣어주고 여기에 token 넣어주면 됨
            return true; // return true인 이유 : .parseClaimsJws 의미가 token이 유효한 토큰이 아니면(예:만료,지원하지 않는, 잘못된) exception 터트리는 거라서
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    // 인증 유저정보를 토큰에서 뽑아오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody(); // Claim이라는 곳에 담아놓은 body가 꺼내짐
    }

    // JWT 토큰 생성
    public String createToken(String username) {
        Date date = new Date(); // util 패키지에 있는 Date - Date값을 기준으로 암호화를 할 것이기 때문에

        // 토큰 만료시간 60분
        long TOKEN_TIME = 60 * 60 * 1000;
        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username) // 사용자 식별자값(ID)
                        //.claim(AUTHORIZATION_KEY, role) // 사용자 권한
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 만료 시간
                        .setIssuedAt(date) // 발급일
                        .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                        .compact();
    }
}
