package com.example1.demo1.jwt;

/**
 * Created by liuhaiyang on 2017/5/26.
 */

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    List<String> excludeUrlPatterns = new LinkedList<>();
    PathMatcher pathMatcher = new AntPathMatcher();

    public JwtAuthenticationFilter(String... excludeUrlPatterns) {
        this.excludeUrlPatterns.addAll(
                Arrays.asList(excludeUrlPatterns));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String token = request.getHeader(JwtUtil.HEADER_STRING); //4.从请求头中获取token。
            JwtUtil.validateToken(token); //5.验证token，验证的时候如果验证失败会抛出异常。
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return excludeUrlPatterns.stream()
                .anyMatch(p -> pathMatcher.match(p, request.getServletPath()));
    }

}