package com.curry.file.util2;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 遍历，替换，比较。
   集合工具类
 */
public class test {

    public static void main(String[] args) {
        //


    }

    public void test1(String ... arrays1) {
        //返回单个对象的不可变list
        List<String> list = Collections.singletonList("11");
        list.add("22");
        System.out.println(list);

        //
        List<String> list1 = new ArrayList<>();
        arrays1 = new String[]{"11", "22"};
        Collections.addAll(list1, arrays1);
        System.out.println(list1);

//        Collections.sort
//        Collections.emptySet()
//
//        Arrays.asList
//        Arrays.toString
//        Arrays.sort

    }

}
