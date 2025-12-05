package com.nt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                   .userDetailsService(userDetailsService)
                   .passwordEncoder(passwordEncoder)
                   .and()
                   .build();
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                // Permit public endpoints first
                .requestMatchers("/user/showLogin","/user/showForgotPwd","/user/newPwdGen","/user/register", "/user/save",
                                 "/css/**", "/js/**", "/webjars/**").permitAll()
                // Allow session setup for ANY authenticated user (key fix for 403)
                .requestMatchers("/user/setupSession").authenticated()
                // Restrict specific admin-only user endpoints
                .requestMatchers("/user/all", "/user/activate", "/user/inactivate", "/user/afterLogin").hasAuthority("ADMIN")
                // Other endpoints (unchanged)
                .requestMatchers("/uom/**","/OrderMethod/**", "/st/**",
                                 "/whUser/**", "/part/**").hasAnyAuthority("ADMIN", "APPUSER")
                .requestMatchers("/po/**", "/grn/**","/so/**","/shipping/**").hasAuthority("APPUSER")
                // General rule for remaining /user/** (admin-only)
                .requestMatchers("/user/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form 
                .loginPage("/user/showLogin")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/user/setupSession", true)  // Point to accessible endpoint
                .failureUrl("/user/showLogin?error")
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/user/showLogin?success")
            )
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/user/showLogin"))
            );

        return http.build();
    }
}
