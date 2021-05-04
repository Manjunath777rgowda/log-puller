package com.manjunath.logpuller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.env.Environment;

import com.manjunath.logpuller.utils.FileUtil;

@SpringBootApplication
@EnableFeignClients
public class LogPullerApplication implements CommandLineRunner {

    @Autowired
    private Environment environment;

    public static void main( String[] args )
    {
        SpringApplication.run(LogPullerApplication.class, args);
    }

    @Override
    public void run( String... args ) throws Exception
    {
        FileUtil.createFolder(environment.getProperty("app.folder"));
    }
}
