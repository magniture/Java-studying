### 如何开发聚合、实体以及值对象
在实现聚合对象等领域模型对象时，需要确保这些领域模型对象的非技术性，即不要被技术体系污染。
```java
@Document("users")
public class User {
       @Id
       private String id;
       @Field("userCode")
       private String userCode;
       @Field("userName")
       private String userName;
       
}
```
在 User 类上添加了 @Document、@Id、@Field 等 Spring Data MongoDB 所提供的一组专用注解。这样就导致了领域模型对象中夹杂着具体的技术实现方案，这在 DDD 中是不推荐的。

#### 抽取实体和值对象
实体和值对象都是聚合的组成部分，区别在于实体具有唯一标识和状态性。
##### 抽取实体
```java
public class Consultation {
         private String consultationId;//咨询Id
         private String account;//用户账号
         private OrderProfile order;//问询订单
         private String inquire;//工单问询内容        

         public Consultation(String consultationId, String account, OrderProfile order, String inquire) {
                super();
                this.consultationId = consultationId;
                this.account = account;
                this.order = order;
                this.inquire = inquire;
         }

         public String getConsultationId() {
                return consultationId;
         }

         public void setConsultationId(String consultationId) {
                 this.consultationId = consultationId;
         }

         public String getAccount() {
                 return account;
         }

         public void setAccount(String account) {
                 this.account = account;
         }

         public OrderProfile getOrder() {
                 return order;
         }

         public void setOrder(OrderProfile order) {
                 this.order = order;
         }

         public String getInquire() {
                 return inquire;
         }

         public void setInquire(String inquire) {
                 this.inquire = inquire;
         }
}
```
请注意，这里故意将各种 getter/setter 方法都展示出来的目的在于说明对于实体对象而言，所有的属性原则上都是可以修改的。我们可以通过不同的构造函数来初始化部分字段，然后再通过一组 setter 方法来改变它们的状态。这点是实体和值对象在实现上的本质区别。


其实这个知识点也没啥可说的，可日常的开发规范差不多，你可以理解为建造者模式。后续会把代码发出来