package org.function;


import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author chriszhang
 * @version 1.0
 * @date 2020/3/25
 */
public class StreamCase {

    private static List<String> menuList = Arrays.asList("apple", "banana", "pork", "pineapple", "chicken", "pear");

    public static void stageCase() {
        //注意观察打印顺序(存在**循环合并**现象)
        menuList.stream().filter(d -> {
            System.out.println("filtering: " + d);
            return "pork".equals(d) || "chicken".equals(d);
        }).map(str -> {
            System.out.println("mapping: " + str);
            return str.length();
        }).limit(2).collect(Collectors.toList());
    }

    public static void stageCase2() {
        //给定两个数字列表，如何返回所有的数对呢?例如，给定列表[1, 2, 3]和列表[3, 4]，
        //应该返回[(1, 3), (1, 4), (2, 3), (2, 4), (3, 3), (3, 4)]。
        List<Integer> arrInt_1 = Arrays.asList(1, 2, 3);
        List<Integer> arrInt_2 = Arrays.asList(3, 4);
        List<int[]> res =
                arrInt_1.stream()
                        .flatMap(i -> arrInt_2.stream().map(j -> new int[]{i, j}))
                        .collect(Collectors.toList());
        System.out.println(res);
    }

    public static void terminalCase() {
        //一般来说流包含三个元素：数据源、中间操作链、终端操作
        //这样的理念类似构造器模式
        //flatMap的使用场景：把["hello","world"]变成["h","e","l","o","w","r","d"]
        String[] str = new String[]{"hello", "world"};
        Arrays.stream(str).map(s -> s.split(""))//Stream[h,e,l,l,o],Stream[w,o,r,l,d]
                //Stream[h,e,l,l,o,w,o,r,l,d]，简单说就是把流里面的值变成一个流，然后再把所有流连接起来成为一个流
                .flatMap(Arrays::stream)
                .distinct().collect(Collectors.toList());
    }

    /**
     * reduce的使用场景，通常是做归约处理，比如累积和，
     */
    public static void terminalCase2() {
        //这里举一个比较有意思的reduce使用：找出最大值,实际使用中你可以使用等归约函数来代替
        List<String> strList = Arrays.asList("1", "5", "2", "3", "4", "1", "5", "6", "8", "9");
        Integer max = strList.stream()
                .map(Integer::parseInt)
                //相当于(a,b)-> Math.max(a,b)
                .reduce(Integer::max)
                .orElse(null);
        System.out.println(max);

    }

    /**
     * 场景3，得出一个勾股三元数，如（3,4,5）
     */
    public static void terminalCase3() {
        //构造出a数
        List<int[]> resList = IntStream.rangeClosed(1,100).boxed()
                //合并多个b数流
                .flatMap(a->
                    //构造出b数
                    IntStream.rangeClosed(a,100)
                            .filter(b-> Math.sqrt(a*a+b*b) %1 ==0)
                            .mapToObj(b-> new int[]{a,b,(int)Math.sqrt(a*a+b*b)})
                ).collect(Collectors.toList());
        resList.forEach(arr-> System.out.println("("+arr[0]+","+arr[1]+","+arr[2]+")"));
    }

    /**
     * reduce的使用场景，通常是做归约处理，比如累积和，比较串行和并行计算下的执行效率
     */
    public static void terminalParallelCase() {
        //值得注意的是，java8提供了IntStream等原始类型的Stream来减少装箱拆箱的成本，
        // 并且也提供了部分流特性以及转化为对象流的方法
        IntStream integerStream = IntStream.rangeClosed(0, 100000000);
        long beginTime = System.currentTimeMillis();
        BigDecimal simResult = integerStream
                .mapToObj(BigDecimal::new)
                .reduce(BigDecimal::add).orElse(null);
        long costTime = System.currentTimeMillis() - beginTime;
        System.out.println("结果: " + simResult + " 。串行计算耗时: " + costTime);

        //这里注意 流只能被使用一次，再使用必须重新从数据源获取
        integerStream = IntStream.rangeClosed(0, 100000000);

        beginTime = System.currentTimeMillis();
        BigDecimal parallelResult = integerStream
                .parallel()
                .mapToObj(BigDecimal::new)
                .reduce(BigDecimal::add).orElse(null);
        costTime = System.currentTimeMillis() - beginTime;
        System.out.println("结果: " + parallelResult + " 。并行计算耗时: " + costTime);

    }

    public static void streamCase(){
        //值创建流
        Stream<String> stringStream = Stream.of("1","4","6");

        //数组创建流
        Stream<String> stringStream1 = Arrays.stream(new String[]{"a","b"});

        //文件创建流
        try(Stream<String> stringStream2 = Files.lines(
                Paths.get(
                        StreamCase.class.getResource("/").getPath()+"/a.txt"), StandardCharsets.UTF_8
                )){
            //文件内容操作
            System.out.println(stringStream2.mapToInt(Integer::valueOf).reduce(0, Integer::sum));

        }catch (IOException e){
            e.printStackTrace();
        }

        //函数生成无限流

        //迭代
        //生成前20个斐波那契数列
        Stream.iterate(new int[]{0,1},arr->new int[]{arr[1],arr[0]+arr[1]})
                .limit(20)
                .map(arr->arr[0])
                .forEach(System.out::print);

        //生成
        Stream.generate(Math::random)
                .limit(10)
                .forEach(System.out::print);
    }

    public static void main(String[] args) {
        terminalCase3();
    }
}
