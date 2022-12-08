package org.example.object;

/**
 * @BelongsProject: Java-studying
 * @BelongsPackage: org.example.object
 * @Author: zhangq
 * @CreateTime: 2022-12-08  11:40
 * @Description: TODO
 */
public class OnObject {

    private String name;
    private int age;
    private int sex;

    public void initialize(String n, int a, int s) {  //初始化
        name = n;
        age = a;
        sex = s;
    }

    public static void main(String[] args) {
        OnObject person = new OnObject();
        person.initialize("沉默王二",18,1);

        System.out.println(person.name);
        System.out.println(person.age);
        System.out.println(person.sex);
    }
}
