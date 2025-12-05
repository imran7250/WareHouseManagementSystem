package com.nt.intercepter;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.nt.model.User;
import com.nt.service.IUserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class UserSessionInterceptor implements HandlerInterceptor {

    @Autowired
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Principal principal = request.getUserPrincipal();
        if (principal != null && request.getSession() != null) {
            HttpSession session = request.getSession();
            if (session.getAttribute("userOb") == null) {
                Optional<User> user = userService.findByEmail(principal.getName());
                user.ifPresent(u -> session.setAttribute("userOb", u));
            }
        }
        return true;
    }
}
