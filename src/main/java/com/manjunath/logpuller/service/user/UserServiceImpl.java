/* Copyright (c) 04/04/21, 6:42 PM. Created by Manjunath R */

package com.manjunath.logpuller.service.user;

import java.util.Collections;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private Environment environment;

    @Override
    public UserDetails loadUserByUsername( String userNameRequest ) throws UsernameNotFoundException
    {
        String userName = environment.getProperty("spring.user.name");
        String password = environment.getProperty("spring.user.password");

        if( !Objects.equals(userName, userNameRequest) )
        {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new User(userName, passwordEncoder.encode(password), true, true, true, true, Collections.emptyList());
    }

}
