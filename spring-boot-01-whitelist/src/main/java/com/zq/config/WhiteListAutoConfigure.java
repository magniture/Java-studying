package com.zq.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @BelongsProject: Java-studying
 * @BelongsPackage: com.zq.config

 */
@Configuration
@ConditionalOnClass(WhiteListProperties.class)  //这个注解的作用就是实例化一个类（要在类路径上才会实例化）
@EnableConfigurationProperties(WhiteListProperties.class) //作用就是使@ConfigurationProperties生效
public class WhiteListAutoConfigure {

    @Bean("whiteListConfig")
    @ConditionalOnMissingBean //用到这个方法上，只会实例化一个bean对象
    public String whiteListConfig(WhiteListProperties properties) {
        return properties.getUsers();
    }

}

