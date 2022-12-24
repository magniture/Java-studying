package com.zq.spring.basic_dl.c_oftype;

import com.zq.spring.basic_dl.c_oftype.dao.DemoDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

public class OfTypeApplication {
    
    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("basic_dl/quickstart-oftype.xml");
        Map<String, DemoDao> beans = ctx.getBeansOfType(DemoDao.class);
        beans.forEach((beanName, bean) -> {
            System.out.println(beanName + " : " + bean.toString());
        });
    }

    /*
  在官方原文：
  ApplicationContext是BeanFactory 的子接口，它增加了：
    与 SpringFramework 的 AOP 功能轻松集成
    消息资源处理（用于国际化）
    事件发布
    应用层特定的上下文，例如 Web 应用程序中使用的 WebApplicationContext

    所以ApplicationContext包含BeanFactory的功能，也有很多扩展
*/
}
