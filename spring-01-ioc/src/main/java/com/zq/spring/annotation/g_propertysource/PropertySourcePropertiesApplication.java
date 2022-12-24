package com.zq.spring.annotation.g_propertysource;

import com.zq.spring.annotation.g_propertysource.bean.JdbcProperties;
import com.zq.spring.annotation.g_propertysource.config.JdbcPropertiesConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PropertySourcePropertiesApplication {
    
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
                JdbcPropertiesConfiguration.class);
        System.out.println(ctx.getBean(JdbcProperties.class).toString());
    }
}
