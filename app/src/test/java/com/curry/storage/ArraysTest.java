package com.curry.storage;

import org.junit.Test;

public class ArraysTest {

    /**
     * https://www.jianshu.com/p/09e8c713e506
     *
     * Arrays.asList()
     * Arrays.toString()
     * Arrays.sort()
     *
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        //集合泛型转换
        //Arrays.asList()
        /*List<String> list3 = new ArrayList<>();
        list3.add("3");
        List mItemList = Arrays.asList(list3.toArray(new String[0]));*/

        //数组格式化成字符串
        //Arrays.toString()
        /*String[] arrays4 = {"1", "2"};
        String s4 = Arrays.toString(arrays4);
        System.out.println(s4);*/

        // 数组排序
        // Arrays.sort();
        /*String[] arrays5 = {"1","4","你","3","c","b","a","章", "2"};
        System.out.println(Arrays.toString(arrays5));
        Arrays.sort(arrays5);
        System.out.println(Arrays.toString(arrays5));*/

    }

}