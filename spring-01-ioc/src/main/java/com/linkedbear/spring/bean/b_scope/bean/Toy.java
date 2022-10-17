package com.linkedbear.spring.bean.b_scope.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//@Scope ，并声明为原型类型
@Component
@Scope("prototype") // ConfigurableBeanFactory.SCOPE_SINGLETON
public class Toy {
    public Toy() {
        System.out.println("Toy constructor run ...");
    }
}
