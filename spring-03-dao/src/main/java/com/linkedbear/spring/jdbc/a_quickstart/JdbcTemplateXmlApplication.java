package com.linkedbear.spring.jdbc.a_quickstart;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
/**
 * @Author Zhang Q
 * @Description  Spring整合jdbc
 * @Date 10:19 2022/11/15
 **/
public class JdbcTemplateXmlApplication {
    
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/spring-jdbc.xml");
        JdbcTemplate jdbcTemplate = ctx.getBean(JdbcTemplate.class);
        jdbcTemplate.execute("insert into tbl_account (user_id, money) values (3, 100)");
    }
}
