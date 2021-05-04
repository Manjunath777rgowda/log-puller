package com.manjunath.logpuller.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.manjunath.logpuller.constants.RestEndPoints;
import com.manjunath.logpuller.constants.ViewConstants;
import com.manjunath.logpuller.service.user.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private LoginFailureHandler loginFailureHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Override
    protected void configure( AuthenticationManagerBuilder auth )
    {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure( HttpSecurity http ) throws Exception
    {
        http.authorizeRequests()
                .antMatchers(RestEndPoints.REGISTRATION + "**", RestEndPoints.LOGIN + "**", RestEndPoints.IMPORT + "**",
                        RestEndPoints.GET_LOG + "**", "/js/**", "/css/**", "/image/**")
                .permitAll().anyRequest().authenticated()

                .and().formLogin().successHandler(loginSuccessHandler).failureHandler(loginFailureHandler)
                .loginPage(RestEndPoints.LOGIN).permitAll()

                .and().logout().invalidateHttpSession(true).clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher(RestEndPoints.LOGOUT))
                .logoutSuccessUrl(RestEndPoints.LOGIN + "?" + ViewConstants.LOGOUT).permitAll();
    }
}
