package com.cardgame.filters;
import com.cardgame.entity.User;
import com.cardgame.repository.UserRepository;
import com.cardgame.service.AuthenticationService;
import com.cardgame.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationService authService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    )
            throws ServletException, IOException {

        if (Objects.equals(request.getRequestURI(), "/api/v1/auth/logout")) {
            Cookie cookie = new Cookie("token", null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
            Cookie refreshcookie = new Cookie("refreshtoken", null);
            refreshcookie.setMaxAge(0);
            refreshcookie.setPath("/");
            response.addCookie(refreshcookie);
            filterChain.doFilter(request,response);
            return;
        }
        final String jwt;
        final long userId;

        var tokenCookie = WebUtils.getCookie(request, "token");
        if (tokenCookie == null) {
            var refreshTokenCookie = WebUtils.getCookie(request, "refreshtoken");
            if (refreshTokenCookie == null) {
                filterChain.doFilter(request,response);
                return;
            } else {
                jwt = refreshTokenCookie.getValue();
            }
        } else {
            jwt = tokenCookie.getValue();
        }

        userId = Long.parseLong( jwtService.extractUserId(jwt));
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            filterChain.doFilter(request,response);
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtService.isTokenValid(jwt,user.get())) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user.get(),
                        null,
                        user.get().getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        response.addCookie(authService.tokenCookie(user.get()));
        response.addCookie(authService.refreshTokenCookie(user.get()));
        filterChain.doFilter(request,response);
    }
}