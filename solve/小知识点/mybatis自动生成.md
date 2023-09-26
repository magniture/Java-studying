#集成Mybatis官方生成器
* 集成mybatis-generator
    * 添加Maven插件
    * 添加generator-config.xml
    * 创建maven启动命令
    * 生成器生成的4个文件
* 使用的生成的代码完成查询count
  <br/>
  <br/>
## 集成mybatis-generator
用来生成单表的增删改查操作
多表关联还是得写自定义的mapper
### 添加Maven插件
```java
<build>
    <plugins>
        <!-- mybatis generator 自动生成代码插件 -->
        <plugin>
            <groupId>org.mybatis.generator</groupId>
            <artifactId>mybatis-generator-maven-plugin</artifactId>
            <version>1.4.0</version>
            <configuration>
                <configurationFile>src/main/resources/generator/generator-config.xml</configurationFile>
                <overwrite>true</overwrite>
                <verbose>true</verbose>
            </configuration>
            <dependencies>
                <dependency>
                    <groupId>mysql</groupId>
                    <artifactId>mysql-connector-java</artifactId>
                    <version>8.0.22</version>
                </dependency>
            </dependencies>
        </plugin>
    </plugins>
</build>
```

### 添加generator-config.xml
```java
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>
    <context id="Mysql" targetRuntime="MyBatis3" defaultModelType="flat">

        <!-- 自动检查关键字，为关键字增加反引号 -->
        <property name="autoDelimitKeywords" value="true"/>
        <property name="beginningDelimiter" value="`"/>
        <property name="endingDelimiter" value="`"/>

        <!--覆盖生成XML文件-->
        <plugin type="org.mybatis.generator.plugins.UnmergeableXmlMappersPlugin" />
        <!-- 生成的实体类添加toString()方法 -->
        <plugin type="org.mybatis.generator.plugins.ToStringPlugin"/>

        <!-- 不生成注释 -->
        <commentGenerator>
            <property name="suppressAllComments" value="true"/>
        </commentGenerator>

        <!-- 配置数据源，需要根据自己的项目修改 -->
        <jdbcConnection driverClass="com.mysql.cj.jdbc.Driver"
                        connectionURL="jdbc:mysql://rm-uf6470s9615e13hc4no.mysql.rds.aliyuncs.com/paymentdev?serverTimezone=Asia/Shanghai"
                        userId="paymentdev"
                        password="Paymentdev123">
        </jdbcConnection>

        <!-- domain类的位置 -->
        <javaModelGenerator targetProject="src\main\java"
                            targetPackage="com.ruyuan.payment.server.domain"/>

        <!-- mapper xml的位置 -->
        <sqlMapGenerator targetProject="src\main\resources"
                         targetPackage="mapper"/>

        <!-- mapper类的位置 -->
        <javaClientGenerator targetProject="src\main\java"
                             targetPackage="com.ruyuan.payment.server.mapper"
                             type="XMLMAPPER"/>

        <table tableName="course" domainObjectName="Course"/>
    </context>
</generatorConfiguration>
```

###创建maven启动命令
```
mybatis-generator:generate
```

###生成器生成的4个文件
Course.java：和表对应的实体类
CourseExample.java：用来构造where条件和order by
CourseMapper.java：持久层入口
CourseMapper.xml：SQL语句

使用要点：
* 这四个文件在以后的开发过程中，都不要修改
* 每次要有表结构变更，都要重新生成该表
* 自定义的SQL写在自定义的Mapper中

##使用的生成的代码完成查询count
