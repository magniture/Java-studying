package com.zq.spring.basic_dl.b_bytype;

import com.zq.spring.basic_dl.b_bytype.bean.Person;
import com.zq.spring.basic_dl.b_bytype.dao.DemoDao;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class QuickstartByTypeApplication {

    public static void main(String[] args) {
        BeanFactory factory = new ClassPathXmlApplicationContext("basic_dl/quickstart-bytype.xml");
        Person person = factory.getBean(Person.class);
        System.out.println(person);

        DemoDao demoDao = factory.getBean(DemoDao.class);
        System.out.println(demoDao.findAll());
    }
}
// xml 把 DemoDaoImpl注入到容器，bean工厂通过接口找到对应实现类