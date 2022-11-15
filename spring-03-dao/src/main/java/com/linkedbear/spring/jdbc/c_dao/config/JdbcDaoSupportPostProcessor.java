package com.linkedbear.spring.jdbc.c_dao.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.sql.DataSource;
/**
 * @Author Zhang Q
 * @Description JdbcDaoSupport
 * @Date 10:48 2022/11/15
 **/
public class JdbcDaoSupportPostProcessor implements BeanPostProcessor {
    
    @Autowired
    private DataSource dataSource;
    
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof JdbcDaoSupport) {
            JdbcDaoSupport daoSupport = (JdbcDaoSupport) bean;
            daoSupport.setDataSource(dataSource);
        }
        return bean;
    }
}
