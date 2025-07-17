package com.example.tossback.config.jwt.filter;

import com.example.tossback.common.enums.JwtTokenType;
import com.example.tossback.config.jwt.util.JWTUtil;
import com.example.tossback.config.redis.util.RedisUtil;
import com.example.tossback.member.dto.CustomMemberDetails;
import com.example.tossback.member.entity.Member;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final Long refreshExpirationHours;

    public JWTFilter(JWTUtil jwtUtil, RedisUtil redisUtil, Long refreshExpirationHours) {
        this.jwtUtil = jwtUtil;
        this.redisUtil = redisUtil;
        this.refreshExpirationHours = refreshExpirationHours;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            log.info("token null");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.replace("Bearer ", "");

        if (jwtUtil.isExpired(token)) {
            String refreshToken = request.getHeader("Refresh-Token");
            if(refreshToken != null && jwtUtil.validateToken(refreshToken)) {
                String userId = jwtUtil.getUserId(refreshToken);
                String redisRefresh = redisUtil.getData("refreshToken:" + userId);
                if(refreshToken.equals(redisRefresh)) {
                    String newAccess = jwtUtil.createToken(userId , jwtUtil.getRole(refreshToken), JwtTokenType.ACCESS);
                    String newRefresh = jwtUtil.createToken(userId , null,JwtTokenType.REFRESH);

                    long refreshExpirationSeconds = refreshExpirationHours * 60 * 60;
                    redisUtil.setDataExpire("refreshToken:" + userId,newRefresh,refreshExpirationSeconds);
                    // 응답 헤더로 토큰 전송
                    response.setHeader("Authorization", "Bearer " + newAccess);
                    response.setHeader("Refresh-Token", newRefresh);

                    Member member = new Member();
                    member.setUserId(userId);
                    member.setPassword("tempPassword");

                    CustomMemberDetails customMemberDetails = new CustomMemberDetails(member);
                    Authentication authToken = new UsernamePasswordAuthenticationToken(
                            customMemberDetails, null, customMemberDetails.getAuthorities()
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    filterChain.doFilter(request, response);
                    return;
                }else{
                    log.info("RefreshToken 불일치");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            }else {
                log.info("Access + Refresh 둘다 만료 또는 없음");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        //Access Token 유효할 경우
        String userId = jwtUtil.getUserId(token);
        //String role = jwtUtil.getRole(token);

        Member member = new Member();
        member.setUsername(userId);
        member.setPassword("tempPassword");

        CustomMemberDetails customMemberDetails = new CustomMemberDetails(member);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customMemberDetails, null, customMemberDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);

    }
}
