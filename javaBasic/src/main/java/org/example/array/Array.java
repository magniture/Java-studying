package org.example.array;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 * @BelongsProject: Java-studying
 * @BelongsPackage: org.example.array
 * @Author: zhangq
 * @CreateTime: 2022-12-08  09:39
 * @Description: 数组
 */
public class Array {
    /*数组元素的类型可以是基础类型，也可以是引用数据类型*/
    public static void main(String[] args) throws Exception{
        //1.可以通过两种声明方式，这种是未初始化：
        int oneArray[];
        int[] twoArray;

        //2. 数组初始化方式
        int[] onArray = new int[10];
        for (int j : onArray) {
            System.out.println(j);
        }

        int[] antherArray = new int[]{8,1,2,3,4};
        for (int j : antherArray) {
            System.out.println(j);
        }

        String[] onString = {"1","2","3"};
        Stream<String> s = Arrays.stream(onString);
        s.forEach(System.out::println);

        //Arrays类提供的排序
        Arrays.sort(antherArray);
        for (int j : antherArray) {
            System.out.println(j);
        }

        //通过二分查找，可以快速找到4的位置
        int er = Arrays.binarySearch(antherArray,4);
        System.out.println(er);

        //如何优雅打印数组呢
        Stream.of(onString).forEach(System.out::println);
        Arrays.stream(onString).forEach(System.out::println);
    }
}
