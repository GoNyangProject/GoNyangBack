package com.example.tossback.config.jwt.util;

import jakarta.servlet.http.HttpServletRequest;

public class CookieUtil {
    public static String getCookieValue(HttpServletRequest request, String name) {
        if (request.getCookies() == null) return null;

        for (var cookie : request.getCookies()) {
            if (cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    public static String createHttpOnlyCookie(String name, String value, long maxAgeSeconds) {
        StringBuilder sb = new StringBuilder();

        sb.append(name).append("=").append(value).append("; ");
        sb.append("Max-Age=").append(maxAgeSeconds).append("; ");
        sb.append("Path=/; ");
        sb.append("HttpOnly; ");

        // 로컬 개발 환경(HTTP)에서는 Secure 속성이 있으면 삭제가 안 될 수 있습니다.
        // 만약 HTTPS 환경이라면 sb.append("Secure; "); 를 추가해야 합니다.
        if (!isLocal()) {
            sb.append("Secure; ");
            sb.append("SameSite=None"); // 배포 환경(HTTPS)
        } else {
            sb.append("SameSite=Lax");   // 로컬 환경(HTTP)
        }

        return sb.toString();
    }
    public static boolean isLocal() {
        return "local".equals(System.getProperty("spring.profiles.active"));
    }
}
