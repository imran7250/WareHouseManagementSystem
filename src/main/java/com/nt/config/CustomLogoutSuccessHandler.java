package com.nt.config;

import com.nt.service.IUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final IUserService service;

    public CustomLogoutSuccessHandler(IUserService service) {
        this.service = service;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException {
        if (authentication != null) {
            String email = authentication.getName();
            service.findByEmail(email).ifPresent(user -> {
                service.modifyStatus(user.getId(), false); // ✅ set INACTIVE
            });
        }
        response.sendRedirect("/user/showLogin?logout");
    }
}
