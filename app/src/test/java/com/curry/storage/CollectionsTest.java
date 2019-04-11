package com.curry.storage;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CollectionsTest {

    /**
     * https://www.jianshu.com/p/09e8c713e506
     *
     * Collections.singletonList()
     * Collections.addAll()
     * Collections.sort()
     *
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        //返回单个对象的不可变list
        //Collections.singletonList()
        /*List<String> list = Collections.singletonList("11");
        list.add("22");
        System.out.println(list);*/

        // 数组添加到集合里
        // Collections.addAll()
        /*List<String> list1 = new ArrayList<>();
        String[] arrays1 = new String[]{"11", "22"};
        Collections.addAll(list1, arrays1);
        System.out.println(list1);*/

        //可以加一个比较器
        // Collections.max()
        List<String> list2 = new ArrayList<>();
        list2.add("4");
        list2.add("2");
        list2.add("c");
        list2.add("b");
        list2.add("你");
        list2.add("好");
        System.out.println(list2);
        String max = Collections.max(list2);
        System.out.println(max);

        //二分法搜索list集合中的指定对象
        /*Collections.binarySearch(list2, );*/

        //替换
        Collections.fill();

    }

    //将数据源排序，大的在前面，逆序
    public void sort(List<QuickAccount> accountDetailList) {
        if (accountDetailList == null) {
            return;
        }
        Collections.sort(accountDetailList, new Comparator<QuickAccount>() {
            //返回值为int类型，大于0表示正序，小于0表示逆序
            @Override
            public int compare(QuickAccount o1, QuickAccount o2) {
                //将格式化时间转换成时间戳比较
                long timeStamp1;
                long timeStamp2;
                try {
                    timeStamp1 = Long.valueOf(o1.getUpdateTime());
                    timeStamp2 = Long.valueOf(o2.getUpdateTime());
                } catch (Exception e) {
                    e.printStackTrace();
                    timeStamp1 = System.currentTimeMillis();
                    timeStamp2 = System.currentTimeMillis();
                }
                if (timeStamp1 >= timeStamp2) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
    }
}