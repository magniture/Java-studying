package com.zq.spring.annotation.a_quickstart.config;

import com.zq.spring.annotation.a_quickstart.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuickstartConfiguration {

    @Bean(name = "zq")
    public Person person() {
        Person person = new Person();
        person.setName("zq");
        person.setAge("18");
        return person;
    }

}
