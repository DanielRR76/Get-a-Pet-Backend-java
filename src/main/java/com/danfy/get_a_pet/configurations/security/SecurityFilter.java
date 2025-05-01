package com.danfy.get_a_pet.configurations.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.danfy.get_a_pet.domain.repositories.IUserRepository;
import com.danfy.get_a_pet.domain.services.ITokenService;
import com.danfy.get_a_pet.exceptions.RestErrorMessage;
import com.danfy.get_a_pet.exceptions.auth.ExpiredTokenException;
import com.google.gson.Gson;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ITokenService tokenService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            var token = recoveryToken(request);
            if (token != null) {
                var decodedToken = tokenService.validate(token);
                UserDetails user = userRepository.findByEmail(decodedToken.email());
                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredTokenException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setCharacterEncoding("UTF-8");
            var error = new RestErrorMessage(HttpStatus.FORBIDDEN, e.getMessage());
            Gson gson = new Gson();
            response.getWriter().write(gson.toJson(error));
        }
    }

    private String recoveryToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }

}
