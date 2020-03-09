package org.ver9;

import java.util.List;
import java.util.Map;

public class CollectionCase {
    //自带集合工厂方法升级
    public static void collectionCase(){
        //用of方法代替immutablexxx
        List<String> immutableList  = List.of("zhangsan","lisi","wangwu");

        Map<String,Integer> immutableMap = Map.of("2",1,"3",3);
    }
}
