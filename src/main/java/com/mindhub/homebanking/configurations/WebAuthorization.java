package com.mindhub.homebanking.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableWebSecurity
public class WebAuthorization {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/web/index.html").permitAll()
                .antMatchers("/api/login/**").permitAll()
                .antMatchers("/web/css/**", "/web/img/**", "/web/js/**").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers(HttpMethod.POST,"/api/clients").permitAll()
                .antMatchers("/h2-console/**").hasAuthority("ADMIN")
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/rest/**").hasAuthority("ADMIN")
                .antMatchers("/**").hasAuthority("CLIENT");
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.formLogin()
                .loginPage("/api/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll()
                .successHandler((req, res, auth) -> {
                    clearAuthenticationAttributes(req);
                })
                .failureHandler((req, res, exc) -> {
                    Map<String, Object>
                            errorResponse=new HashMap<>();
                            errorResponse.put("message","Login failed");
                    res.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
                });
        http.logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> {
            res.sendRedirect("/web/index.html");
        });
        return http.build();
    }
    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}




//****
//@EnableWebSecurity
//@Configuration
//public class WebAuthorization extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http.authorizeRequests()
//                .antMatchers("/web/index.html").permitAll()
//                .antMatchers("/web/css/", "/web/img/", "/web/js/").permitAll()
//                .antMatchers("/h2-console/**").permitAll()
//                .antMatchers("/admin/").hasAuthority("ADMIN")
//                .antMatchers("/api/").hasAuthority("CLIENT")
//                .antMatchers("/web/").hasAuthority("CLIENT")
//                .antMatchers("/rest/").hasAuthority("ADMIN")
//                .antMatchers("/").hasAuthority("USER");
//        http.formLogin()
//                .usernameParameter("email")
//                .passwordParameter("password")
//                .loginPage("/api/login");
//
//        http.logout().logoutUrl("/api/logout");
//
//        http.csrf().disable();
//        http.headers().frameOptions().disable();
//        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
//        // if login is successful, just clear the flags asking for authentication
//        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));
//        // if login fails, just send an authentication failure response
//        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
//        // if logout is successful, just send a success response
//        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
//    }
//    private void clearAuthenticationAttributes(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
//        }
//    }
//}


//******

//package com.mindhub.homebanking.configurations;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.web.WebAttributes;
//import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//@EnableWebSecurity
//
//@Configuration
//
//class WebAuthorization extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//            http.authorizeRequests()
//                    .antMatchers("/web/html/index.html").permitAll()
//                    .antMatchers("/web/login").permitAll()
//                    .antMatchers(HttpMethod.POST, "/api/clients").permitAll()
//                    .antMatchers("/h2-console/**").permitAll()
//                    .antMatchers("/**").permitAll()
//                    .antMatchers("/web/css/**", "/web/img/**", "/web/js/**").permitAll()
//
//
//                    .antMatchers("/web/html/clientList.html").hasAuthority("ADMIN")
//                    .antMatchers("/web/html/indexAdmin.html").hasAuthority("ADMIN")
//                    .antMatchers( "/web/html/loanAdmin.html").hasAuthority("ADMIN")
//                    .antMatchers("/api/loansType").hasAuthority("ADMIN")
//
//
//
//                    .antMatchers("/api/clients/current/accounts").hasAuthority("CLIENT")
//                    .antMatchers(HttpMethod.POST, "/api/clients/current/cards").hasAuthority("CLIENT")
//                    .antMatchers(HttpMethod.POST, "/api/transactions").hasAuthority("CLIENT")
//                    .antMatchers("/api/loans").hasAuthority("CLIENT")
//                    .antMatchers(HttpMethod.POST,"/api/loans").hasAuthority("CLIENT");
//
//            http.formLogin()
//                    .usernameParameter("email")
//                    .passwordParameter("password")
//                    .loginPage("/api/login");
//            http.logout().logoutUrl("/api/logout");
//
//
//            // turn off checking for CSRF tokens
//            http.csrf().disable();
//            //disabling frameOptions so h2-console can be accessed
//
//            http.headers().frameOptions().disable();
//
//            // if user is not authenticated, just send an authentication failure response
//
//            http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
//
//            // if login is successful, just clear the flags asking for authentication
//
//            http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));
//
//            // if login fails, just send an authentication failure response
//
//            http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
//
//            // if logout is successful, just send a success response
//
//            http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
//
//        }
//
//        private void clearAuthenticationAttributes(HttpServletRequest request) {
//
//            HttpSession session = request.getSession(false);
//
//            if (session != null) {
//
//                session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
//
//            }
//
//        }
//
//
//
//
//    }
//
//
//
//
//
//
//
