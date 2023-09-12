```java

@Configuration
public class JavaConfig01 {
}
@Component
public class JavaConfig02 {
}

```

首先，分别向 Spring 容器中注入两个 Bean，JavaConfig01 和 JavaConfig02，其中，JavaConfig01 上添加的是 @Configuration 注解而 JavaConfig02 上添加的则是 @Component 注解。

现在，在 XML 文件中配置包扫描：

```xml
<context:component-scan 
base-package="org.javaboy.demo.p6"/>

```

最后，加载 XML 配置文件，初始化容器：

```java
public class Demo {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans_demo.xml");
        JavaConfig01 config01 = ctx.getBean(JavaConfig01.class);
        JavaConfig02 config02 = ctx.getBean(JavaConfig02.class);
        System.out.println("config01.getClass() = " + config01.getClass());
        System.out.println("config02.getClass() = " + config02.getClass());
    }
}

```


![image.png](assets/image.png)


从上面这段代码中，我们可以得出来两个结论：

1. @Configuration 注解也是 Spring 组件注解的一种，通过普通的 Bean 扫描也可以扫描到 @Configuration。
2. @Configuration 注解注册到 Spring 中的 Bean 是一个 CGLIB 代理的 Bean，而不是原始 Bean，这一点和 @Component 不一样，@Component 注册到 Spring 容器中的还是原始 Bean。
