package com.example.tossback.member.filter;

import com.example.tossback.common.dto.CommonResponse;
import com.example.tossback.common.enums.ErrorCode;
import com.example.tossback.common.enums.JwtTokenType;
import com.example.tossback.common.exception.CommonException;
import com.example.tossback.config.jwt.util.CookieUtil;
import com.example.tossback.config.jwt.util.JWTUtil;
import com.example.tossback.config.redis.util.RedisUtil;
import com.example.tossback.member.dto.CustomMemberDetails;
import com.example.tossback.member.dto.MemberDTO;
import com.example.tossback.member.dto.MemberResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final Long refreshExpirationHours;

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final RedisUtil redisUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, RedisUtil redisUtil, Long refreshExpirationHours) {
        this.refreshExpirationHours = refreshExpirationHours;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.setFilterProcessesUrl("/member/login");
        this.redisUtil = redisUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            ObjectMapper om = new ObjectMapper();
            MemberDTO loginRequest = om.readValue(request.getInputStream(), MemberDTO.class);

            String userId = loginRequest.getUserId();
            String password = loginRequest.getPassword();

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userId, password);
            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        CustomMemberDetails memberDetails = (CustomMemberDetails) authentication.getPrincipal();

//        String username = memberDetails.getUsername();
        Long memberId = memberDetails.getMemberId();
        String userId = memberDetails.getUserId();
        String username = memberDetails.getUsername();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();
//        String role = "ROLE_ADMIN";
//        String token = jwtUtil.createJwt(username, role);
        String accessToken = jwtUtil.createToken(userId, role, JwtTokenType.ACCESS);
        String refreshToken = jwtUtil.createToken(userId, null, JwtTokenType.REFRESH);

        long refreshExpirationSeconds = refreshExpirationHours * 60 * 60;  // hours -> seconds
        redisUtil.setDataExpire("refreshToken:" + userId, refreshToken, refreshExpirationSeconds);

        response.addHeader(
                "Set-Cookie",
                CookieUtil.createHttpOnlyCookie("accessToken", accessToken, 60 * 60)
        );

        response.addHeader(
                "Set-Cookie",
                CookieUtil.createHttpOnlyCookie("refreshToken", refreshToken, refreshExpirationSeconds)
        );


        MemberResponseDTO userData = new MemberResponseDTO();
        userData.setMemberId(memberId);
        userData.setUserId(userId);
        userData.setUsername(username);
        userData.setUserType(role);
        userData.setRole(role);
        CommonResponse result = new CommonResponse(userData);
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(result));
        response.getWriter().flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
//        throw new CommonException("로그인 실패 : " + failed, ErrorCode.Unauthorized);
        CommonException exception = new CommonException(
                "로그인 실패 : " + failed.getMessage(),
                ErrorCode.Unauthorized // Unauthorized("0401", "ID 혹은 비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED)
        );
        response.setStatus(exception.getErrorCode().getHttpStatus().value());
        response.setContentType("application/json;charset=UTF-8");

        // JSON 응답
        response.getWriter().write(
                new ObjectMapper().writeValueAsString(
                        Map.of(
                                "type", "FAIL",
                                "errorCode", exception.getErrorCode().getCode(), // "0401"
                                "message", exception.getErrorCode().getMessage()
                        )
                )
        );
    }

}
