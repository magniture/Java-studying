package com.zq.spring.jdbc.a_quickstart;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
/**
 * @Author Zhang Q
 * @Description //TODO
 **/
public class JdbcTemplateQuickstartApplication {
    
    public static void main(String[] args) throws Exception {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/spring-dao?characterEncoding=utf8");
        dataSource.setUsername("root");
        dataSource.setPassword("4589512ok");
        
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        jdbcTemplate.execute("insert into tbl_user (name, tel) values ('hahaha', '12345')");
    }
}
