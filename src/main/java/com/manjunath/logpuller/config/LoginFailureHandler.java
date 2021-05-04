/* Copyright (c) 04/04/21, 9:08 PM. Created by Manjunath R */

package com.manjunath.logpuller.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.manjunath.logpuller.constants.RestEndPoints;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationFailure( HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception ) throws IOException
    {
        redirectStrategy.sendRedirect(request, response, RestEndPoints.LOGIN + "?error");
    }

}