package org.example.object;

/**
 * @BelongsProject: Java-studying
 * @BelongsPackage: org.example.object
 * @Author: zhangq
 * @CreateTime: 2022-12-08  11:39
 * @Description: TODO
 */
public class Person {
    private String name; //字段（Filed）
    private int age;
    private int sex;

    public Person() { // 空的构造方法（方法体中没有内容），所以可以缺省
    }

    private void eat() { //方法
    }

    private void sleep() {
    }

    private void dadoudou() {
    }

    public static void main(String[] args) {
        Person person = new Person();
        System.out.println(person.name);
        System.out.println(person.age);
        System.out.println(person.sex);
    }
    //为什么会有这样的输出结果呢？因为 Person 对象没有初始化，因此输出了 String 的默认值 null，int 的默认值 0。
}
