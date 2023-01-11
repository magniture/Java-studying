package com.zq.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @BelongsProject: Java-studying
 * @BelongsPackage: com.zq.config

 * @Description: 白名单配置获取
 */
@ConfigurationProperties("zz.whitelist") //这个注解就是或者yml或properties 中的 zz.whitelist.*
public class WhiteListProperties {

    private String users; //这个就是zz.whitelist.user，比如zz.whitelist.users：zq，那这个users就是zq

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }
}

