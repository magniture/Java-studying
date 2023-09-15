@Builder 遇到的坑

在对象中设置默认值时，会出现默认值丢失的情况。

```java
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private Long id;

    private String name;

    private String type = "0";

}

// 测试方法
class Main {
    public static void main(String[] args) {
        Account account = Account.builder().name("Li").id(1L).build();
        System.out.println(account);
    }
}


```

```java
Account(id=1, name=Li, type=null)

```

通过反编译`@Builder`注解生成的内容可以看出，问题就在于`build`方法。

```java
public static class AccountBuilder {
    private Long id;
    private String name;
    private String type;

    AccountBuilder() {
    }

    public Account.AccountBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public Account.AccountBuilder name(String name) {
        this.name = name;
        return this;
    }

    public Account.AccountBuilder type(String type) {
        this.type = type;
        return this;
    }
  
    public Account build() {
        return new Account(this.id, this.name, this.type);
    }

    public String toString() {
        return "Account.AccountBuilder(id=" + this.id + ", name=" + this.name + ", type=" + this.type + ")";
    }
}

```



## 解决方式

在有默认值的属性上加上`@Builder.Default`注解即可。

```java
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private Long id;

    private String name;

    @Builder.Default
    private String type = "0";

}

```

或者使用`@Accessors`

```java
//@Builder
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
@Accessors(chain = true)
@Data
public class Account {

    private Long id;

    private String name;

    // @Builder.Default
    private String type = "0";

}

class Main {
    public static void main(String[] args) {
//        Account account = Account.builder().name("Li").id(1L).build();
//        System.out.println(account);
      
        Account ac = new Account().setId(1L).setName("Li");
        System.out.println(ac);
      
    }
}

```
