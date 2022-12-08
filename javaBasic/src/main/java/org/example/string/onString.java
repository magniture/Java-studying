package org.example.string;

/**
 * @BelongsProject: Java-studying
 * @BelongsPackage: org.example.string
 * @Author: zhangq
 * @CreateTime: 2022-12-08  10:18
 * @Description: TODO
 */
public class onString {
    public static void main(String[] args) {
        //new 是先看字符串常量池中有没有这个对象，没的话创建后，再在堆中创建，引用堆中的；有的话直接在堆中创建，引用堆中
        String s1 = new String("你这"); //字符串常量池先创建一个”你这“对象，然后堆在创建一个”你这“对象，s1引用堆中的对象
        String s2 = s1.intern(); // intern 从常量池中发现有"你这"对象，直接引用
        System.out.println(s1==s2); //s1引用的是堆，s2引用的是常量池

        // "" 双引号，是直接在字符串常量池中创建并引用
        String e1 = "我这";
        String e2 = e1.intern();
        System.out.println(e1==e2);

        String l1 = new String("娘子")+new String("啊哈");
        String l2 = l1.intern();
        System.out.println(l1==l2);
        // intern 在字符串常量池中没找到”娘子啊哈“, 但在堆中有，所以字符串常量池就会保存堆的引用，true



        String alita = new String("小萝莉");
        String luolita = new String("小萝莉");

        System.out.println(alita.equals(luolita)); // true
        System.out.println(alita == luolita); // false
        /* == 是查看内存地址是否一样， equals 是 判处值是否一样 */


        //拼接
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <10; i++ ) {
            String aa = "你干嘛";
            String bb = "哎呦~";
            sb.append(aa);
            sb.append(bb);
        }
        System.out.println(sb);

        String chenmo = "沉默";
        System.out.println(chenmo.concat(chenmo));

        String json = String.join("-","唱","跳","rap");
        System.out.println(json);

        String[] heizi = json.split("-");
        for (String j:heizi) {
           System.out.println(j);
        }
    }

}
