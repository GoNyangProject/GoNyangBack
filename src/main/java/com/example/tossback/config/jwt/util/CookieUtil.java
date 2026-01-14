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

    public static String createCookie(String name, String value, long maxAgeSeconds, boolean isHttpOnly) {
        StringBuilder sb = new StringBuilder();

        sb.append(name).append("=").append(value).append("; ");
        sb.append("Max-Age=").append(maxAgeSeconds).append("; ");
        sb.append("Path=/; ");

        if (isHttpOnly) {
            sb.append("HttpOnly; ");
        }

        if (!isLocal()) {
            // 배포 환경(HTTPS) 설정
            sb.append("Secure; ");
            sb.append("SameSite=None");
        } else {
            // 로컬 환경(HTTP) 설정
            sb.append("SameSite=Lax");
        }

        return sb.toString();
    }
    public static boolean isLocal() {
        return "local".equals(System.getProperty("spring.profiles.active"));
    }
}
