package com.example.redis02.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @BelongsProject: redis-02
 * @BelongsPackage: com.example.redis02.model
 * @Author: zhangq
 * @CreateTime: 2022-11-14  16:26
 * @Description: TODO
 */
@Data
public class User implements Serializable {
    private String id;
    private String name;
    private String age;

}
