package com.core.api;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class CORSFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, PATCH, OPTIONS");
        response.addHeader("Access-Control-Max-Age", "3600");
        response.addHeader("Access-Control-Allow-Headers",
                "Content-Type, X-User-Id, x-requested-with, X-Forwarded-Host,"
                        + "Cache-Control,Expires,Pragma, x-api-key, Authorization");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        if (!request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.name())
                || request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.name())
                        && request.getHeader("Access-Control-Request-Headers") == null) {
            filterChain.doFilter(request, response);
        }
    }
}
