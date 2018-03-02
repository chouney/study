/**
 * Created by chriszhang on 2017/6/14.
 */
public class DemoLambda {

    /**
     * 函数式编程
     * 接口有且仅有一个抽象方法，则可以通过下列方式实现函数式编程
     * 常用如Runnable和Comparator等
     */
    public void test4FunctionLambda(){
        DemoInterface a =  (x, y) -> {
            return x+y;
        };
        a.sum(3,5);
        System.out.println(a);
    }




    public static void main(String[] args){
        DemoLambda demoLambda = new DemoLambda();
        demoLambda.test4FunctionLambda();
    }
}
