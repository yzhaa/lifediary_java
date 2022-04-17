package yzh.lifediary.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import yzh.lifediary.handle.*;

import javax.servlet.http.HttpSession;

@Configuration
public class MySecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                formLogin()
                // 表单的账号密码
                .usernameParameter("account")
                .passwordParameter("password")
                .loginProcessingUrl("/login")//登录时候访问的路径
                .successHandler(new MyAuthenticationSuccessHandler())
                .failureHandler(new MyAuthenticationFailureHandler())
                //成功跳转路径           .defaultSuccessUrl()
                .and().exceptionHandling()
                .authenticationEntryPoint(new MyAuthenticationEntryPoint())
                .accessDeniedHandler(new MyAccessDeniedHandler())
                .and().logout()
                .logoutSuccessHandler(new MyLogoutSuccessHandler())
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and().authorizeRequests()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/register/**").permitAll()
                .antMatchers("/pic/**").permitAll()
                .antMatchers("/**").authenticated()
                .anyRequest().authenticated()
                .and().sessionManagement().maximumSessions(1).and()
                .and().cors()
                .and()
                .csrf().disable();

    }



    public static UserDetails currentUser(HttpSession session) {
        SecurityContextImpl securityContext = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        return ((UserDetails)securityContext.getAuthentication().getPrincipal());
    }



}
