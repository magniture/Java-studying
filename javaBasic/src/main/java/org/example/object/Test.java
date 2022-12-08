package org.example.object;

/**
 * @BelongsProject: Java-studying
 * @BelongsPackage: org.example.object
 * @Author: zhangq
 * @CreateTime: 2022-12-08  11:48
 * @Description: TODO
 */
public class Test {
    public static void main(String[] args) {
        Shape shape1 = new Line();
        shape1.draw();
        Shape shape2 = new Circle();
        shape2.draw();
    }
}
/*
*  1.继承就是子类可以拥有父类的方法和字段，可以自己扩展一些自己的方法和字段，也可以重写父类方法
*  2.多态就是一群子类可以继承父类并重写
*
* */
