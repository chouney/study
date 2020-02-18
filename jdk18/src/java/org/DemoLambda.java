package org;


import java.util.function.Function;

/**
 * Created by chriszhang on 2017/6/14.
 */
public class DemoLambda {

    private Function<Integer,Integer> pow = (a) -> Math.max(a,3);

    /**
     * 函数式编程
     * 接口有且仅有一个抽象方法，则可以通过下列方式实现函数式编程
     * 常用如Runnable和Comparator等
     */
    public void test4FunctionLambda() {
        DemoInterface a = (x, y) -> {
            return x + y;
        };
        a.sum(3, 5);
        System.out.println(a);
    }

    /**
     * 查看正常匿名内部类与lambda调用的方式有何区别
     */
    public static void testNormalInvoke() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("normal invoke");
            }
        }).start();
    }
    public static void testLambdaInvoke() {
        new Thread(() ->
                System.out.println("lambda invoke")
        ).start();
    }


    public static void main(String[] args) {
        DemoLambda demoLambda = new DemoLambda();
//        System.out.println("/api/v1/guarantor/aweqwe/qwe".matches("^/ap1/v\\d+/guarantor/.*"));
        DemoLambda.testNormalInvoke();
        DemoLambda.testLambdaInvoke();
        System.out.println(demoLambda.pow.apply(5));
    }
}
