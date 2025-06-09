package com.example.lawtest.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_ADMIN")) {
//            response.sendRedirect("/admin/dashboard");
        } else if (roles.contains("ROLE_LAWYER")) {
//            response.sendRedirect("/lawyer/dashboard");
            response.sendRedirect("/orders");
        } else if (roles.contains("ROLE_SUPPORT")) {
            response.sendRedirect("/support/dashboard");
        } else if (roles.contains("ROLE_CLIENT")) {
//            response.sendRedirect("/client/dashboard");
            response.sendRedirect("/lawyers");
        } else {
            response.sendRedirect("/dashboard"); // fallback
        }
    }
}