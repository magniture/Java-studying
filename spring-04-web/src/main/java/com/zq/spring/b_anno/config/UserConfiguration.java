package com.zq.spring.b_anno.config;

import com.zq.spring.a_xml.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {
    
    @Bean
    public UserService userService() {

        return new UserService();

    }
}
