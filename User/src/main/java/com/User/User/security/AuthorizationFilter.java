package com.User.User.security;

import com.User.User.SpringApplicationContext;
import com.User.User.model.UserDto;
import com.User.User.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AuthorizationFilter extends BasicAuthenticationFilter {



    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);

    }


    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        String authorizationHeader = req.getHeader(SecurityConstants.HEADER_STRING);
        if (authorizationHeader == null ||
                !authorizationHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }
        authorizationHeader = authorizationHeader.replace(SecurityConstants.TOKEN_PREFIX,"").trim();
        UsernamePasswordAuthenticationToken authorization = getAuthentication(authorizationHeader);
        SecurityContextHolder.getContext().setAuthentication(authorization);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        String commonKey = SecurityConstants.getSecretToken();
        if (commonKey == null) return null;
        SecretKey key = Keys.hmacShaKeyFor(commonKey.getBytes());

        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String email = claims.getSubject();

        UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
        UserDto userDto = userService.getUserById(email);

        List<SimpleGrantedAuthority> authorities = userDto.getRole().stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
        if (email != null) return new UsernamePasswordAuthenticationToken(email, null, authorities);
        return null;
    }

}
