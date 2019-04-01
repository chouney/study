package jdk18.stream;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author chriszhang
 * @version 1.0
 * @date 2018/9/9
 */
public class ArrayListStreamHelper {

    public static ArrayListReferenceStage.Head stream(ArrayList arrayList) {

        return new ArrayListReferenceStage.Head(arrayList);
    }

    public static void main(String[] args) {
        ArrayList list = new ArrayList() {
            {
                add(1);
                add(2);
                add(6);
                add(10);
                add(12);
                add(14);
                add(11);
            }
        };
        //不能实现的问题在于泛型的类型转换！！！！
//        ArrayListStreamHelper.stream(list).filter(a->a);
    }
}
