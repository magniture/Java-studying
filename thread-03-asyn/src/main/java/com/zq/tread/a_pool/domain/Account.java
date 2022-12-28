package com.zq.tread.a_pool.domain;

/**
 * @BelongsProject: Java-studying
 * @BelongsPackage: com.zq.tread.a_pool.domain

 */
public class Account{
     int age;
     int id;
     String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Account{" +
                "age=" + age +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
