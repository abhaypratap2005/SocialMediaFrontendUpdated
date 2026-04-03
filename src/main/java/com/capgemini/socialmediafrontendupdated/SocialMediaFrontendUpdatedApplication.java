package com.capgemini.socialmediafrontendupdated;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SocialMediaFrontendUpdatedApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialMediaFrontendUpdatedApplication.class, args);
    }

}
