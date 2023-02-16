package com.autocrypt.mon.security;

import com.autocrypt.mon.security.handler.VsocFailureHandler;
import com.autocrypt.mon.security.handler.VsocLogoutSuccessHandler;
import com.autocrypt.mon.security.handler.VsocLoginSuccessHandler;

import com.autocrypt.mon.security.provider.AuthProvider;
import com.autocrypt.mon.security.service.VsocUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.Arrays;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    @Autowired
    private AuthProvider authProvider;

    @Autowired
    VsocUserDetailsService vsocUserDetailsService;

    private AccessDeniedHandler accessDeniedHandler;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(vsocUserDetailsService).passwordEncoder(passwordEncoder());
        auth.jdbcAuthentication().dataSource(dataSource);
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring()
                   .antMatchers("/static/**", "*.ico", "*.png");
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors()
                    .configurationSource(CorsConfigurationSource())
                    .and().csrf().disable()
                    .httpBasic()
                .and().authorizeRequests()
                    .antMatchers("/").permitAll()
                .and().formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/loginProcessing")
                    .usernameParameter("loginId")
                    .passwordParameter("password")
                    .successHandler(loginSuccessHandler())
                    .failureHandler(loginFailureHandler())
                .and().logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                    .logoutSuccessHandler(logoutSuccessHandler())
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true) // 로그아웃 시 Session 삭제.
                .and().sessionManagement()
                    .maximumSessions(1) // 로그인 세션 제한 (User 당 하나의 Session만 허용)
                .and()
                    .invalidSessionUrl("/login") // Session 끊길 시 Redirect 할 URL.
                .and()
                    .authenticationProvider(authProvider)
                    .exceptionHandling() // 에러처리
                    .accessDeniedHandler(this.accessDeniedHandler);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return new VsocLoginSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler loginFailureHandler() {
        return new VsocFailureHandler();
    }

    @Bean
    public VsocLogoutSuccessHandler logoutSuccessHandler() {
        return new VsocLogoutSuccessHandler();
    }

    @Bean
    public CorsConfigurationSource CorsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://127.0.0.1:8080"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "TOKEN_ID", "X-Requested-With", "Authorization", "Content-Type", "Content-Length", "Cache-Control"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
