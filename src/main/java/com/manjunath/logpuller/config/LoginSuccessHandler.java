/* Copyright (c) 04/04/21, 8:39 PM. Created by Manjunath R */

package com.manjunath.logpuller.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.manjunath.logpuller.constants.RestEndPoints;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess( HttpServletRequest arg0, HttpServletResponse arg1,
            Authentication authentication ) throws IOException
    {
        redirectStrategy.sendRedirect(arg0, arg1, RestEndPoints.DASHBOARD);
    }
}
