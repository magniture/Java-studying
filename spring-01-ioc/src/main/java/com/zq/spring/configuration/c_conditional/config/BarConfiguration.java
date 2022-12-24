package com.zq.spring.configuration.c_conditional.config;

import com.zq.spring.configuration.c_conditional.anno.ConditionalOnBean;
import com.zq.spring.configuration.c_conditional.component.Bar;
import com.zq.spring.configuration.c_conditional.component.Boss;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BarConfiguration {
    
    @Bean
//    @Conditional(ExistBossCondition.class)
    @ConditionalOnBean(Boss.class)
    public Bar bbbar() {
        return new Bar();
    }
}
