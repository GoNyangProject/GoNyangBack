package com.example.tossback.config.jwt.filter;

import com.example.tossback.common.enums.JwtTokenType;
import com.example.tossback.common.enums.UserRoleType;
import com.example.tossback.config.jwt.util.CookieUtil;
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


        String accessToken = CookieUtil.getCookieValue(request, "accessToken");

        if (accessToken == null) {
            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                accessToken = authorizationHeader.replace("Bearer ", "");
            }
        }

        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }
        if (jwtUtil.isExpired(accessToken)) {
            String refreshToken = CookieUtil.getCookieValue(request, "refreshToken");

            if (refreshToken == null) {
                refreshToken = request.getHeader("Refresh-Token");
            }
            if(refreshToken != null && jwtUtil.validateToken(refreshToken)) {
                String userId = jwtUtil.getUserId(refreshToken);
                String redisRefresh = redisUtil.getData("refreshToken:" + userId);
                if(refreshToken.equals(redisRefresh)) {
                    String newAccess = jwtUtil.createToken(userId , jwtUtil.getRole(refreshToken), JwtTokenType.ACCESS);
                    String newRefresh = jwtUtil.createToken(userId , null,JwtTokenType.REFRESH);

                    long refreshExpirationSeconds = refreshExpirationHours * 60 * 60;
                    redisUtil.setDataExpire("refreshToken:" + userId,newRefresh,refreshExpirationSeconds);
                    response.addHeader("Set-Cookie",
                            CookieUtil.createCookie("accessToken", newAccess, 60 * 60, false));
                    response.addHeader("Set-Cookie",
                            CookieUtil.createCookie("refreshToken", newRefresh, refreshExpirationSeconds, true));

                    setAuthentication(userId, jwtUtil.getRole(refreshToken));

                    filterChain.doFilter(request, response);
                    return;
                }else{
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            }else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        //Access Token 유효할 경우
        String userId = jwtUtil.getUserId(accessToken);
        String role = jwtUtil.getRole(accessToken);

        setAuthentication(userId ,role);

        filterChain.doFilter(request, response);

    }
    private void setAuthentication(String userId, String role) {
        Member member = new Member();
        member.setUserId(userId);
        member.setPassword("temp");
        member.setUserRoleType(UserRoleType.valueOf(role));

        CustomMemberDetails customMemberDetails = new CustomMemberDetails(member);

        Authentication authToken =
                new UsernamePasswordAuthenticationToken(
                        customMemberDetails,
                        null,
                        customMemberDetails.getAuthorities()
                );

        SecurityContextHolder.getContext().setAuthentication(authToken);
        log.info("인증 성공 - 유저ID: {}, 권한: {}", userId,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities());
    }
}
