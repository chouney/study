package org.function;

import org.function.model.Apple;
import org.function.model.OddAppleFilter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 *
 * @author chriszhang
 * @version 1.0
 * @date 2020/3/10
 */
public class MethodRefCase {

    /**
     *
     * 方法参数化：
     * 配合函数式接口，可以将方法作为参数传入
     * 再配合lamda，实现匿名方法声明，一步步简化
     */
    public static void MethodRef(){
        //方法引用File.isHidden方法
        /**
         * 方法引用有三种
         */
        //1.静态方法的引用
        Stream<Integer> stream = Stream.of("1","2","3").map(Integer::parseInt);
        //2.指向任意类型的实例方法，用于lamda参数的方法引用，
        // 比如下列demo可以写成 (File file)-> file.isHidden()
        // 或者(arg0,rest) -> arg0.instanceMethod(rest) <----> ClassName::instanceMethod
        File[] filteredFiles = new File(".").listFiles(File::isHidden);


        //3. 现有对象的实例方法，
        //(arg0) -> expr.instanceMethod(arg0)  <------>  expr::instanceMethod
        String a = "333";
        Integer aLength = (Integer) Stream.of().findAny().orElseGet(a::length);
    }

    /**
     * 自带函数接口的使用场景
     * @param integers
     * @param findBigger
     */
    public static void methodRef2(List<Integer> integers , BiPredicate<Integer,Integer> findBigger){
        //方法引用场景2,找出最大的数，使用自带Predicate接口
        //为方便说明，下列语句写的繁琐一些i
        int max = Integer.MIN_VALUE;
        for(int i : integers){
            if(findBigger.test(i,max)){
                max = i;
            }
        }
        System.out.println(max);
        // 调用如下方法
        // methodRef2(Arrays.asList(1,2,6,4,2,3,7),(a,b) -> a > b);
    }

    /**
     * 构造方法的参数化说明
     * @param lists
     * @param initFunction
     */
    public static void methodRef3(List<Integer> lists, Function<Integer, List<String>> initFunction){
        //构造方法参数化说明
        List<List<String>> result =
                lists.stream().map(initFunction).collect(Collectors.toList());

        //这里的构造方法参数ArrayList::new相当于(integer) -> new ArrayList(integer),只要方法参数对得上
//        List<Integer> intList = Arrays.asList(1,2,3,4,5,6,7);
//        methodRef3(intList,(int) -> new ArrayList(int));
    }

    /**
     * 函数式接口的复合使用
     */
    public static void methodRef4(){
        //诸如Comparator、Predicate等自带接口都是通过default方法实现多个函数接口符合衔接，如下：
        //先筛选偶数的，再找红色
        Predicate<Apple> applePredicate = new OddAppleFilter()
                .negate()
                .and(apple->"red".equals(apple.getColor()));

        //demo
        List<Apple> apples = IntStream.rangeClosed(1,10)
                .mapToObj((i)-> new Apple(i,(i & 1) == 1 ? "red" : "other"))
                .filter(applePredicate)
                .collect(Collectors.toList());

    }



    /**
     * 函数式接口(Functional Interface)是Java 8对一类特殊类型的接口的称呼。 这类接口只定义了唯一的抽象方法的接口，并且这类接口使用了@FunctionalInterface进行注解。在jdk8中，引入了一个新的包java.util.function, 可以使java 8 的函数式编程变得更加简便。这个package中的接口大致分为了以下四类：
     *  *
     *  * Function: 接收参数，并返回结果，主要方法 R apply(T t)
     *  * Consumer: 接收参数，无返回结果, 主要方法为 void accept(T t)
     *  * Supplier: 不接收参数，但返回结构，主要方法为 T get()
     *  * Predicate: 接收参数，返回boolean值，主要方法为 boolean test(T t)
     *  * Operator: 特殊的function，接收和返回的参数类型一样(Function子类)
     *  *
     */
    public static void collectionCase(){
        int[] arr = IntStream.rangeClosed(1,20).toArray();
        //
        // 分别实现了Supplier
        Map<String,String> maps = Arrays.stream(arr).mapToObj(String::valueOf).collect((Supplier<Map<String, String>>) HashMap::new,
                (stringStringMap, s) -> stringStringMap.put(s,s), Map::putAll);

    }
}
