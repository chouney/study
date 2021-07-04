package org.function;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author chriszhang
 * @version 1.0
 * @date 2020/4/8
 */
public class CollectionCase {
    //Collect方法使用样例
    /**
     * Collectors工厂提供的收集器提供了三大功能：
     * 1.归约和汇总(收集器toList等属于归约，但因为功能比较复杂放到4中)
     * 2.元素分组
     * 3.元素分区
     */

    /**
     * 1.归约和汇总
     */
    public static void collectionCase1(){
        //初始化
        List<Transaction> demoList = genNewTransactionList();

        //归约,可以用count()代替
        long number = demoList.stream().collect(Collectors.counting());
        System.out.println(number);

        //归约，找最大值,可以用max()代替
        Optional<Transaction> maxTransaction = demoList.stream().
                collect(Collectors.maxBy(Comparator.comparingInt(Transaction::getTotal)));
        System.out.println(maxTransaction.get());

        //归约，通过收集器接口开发自定义收集器见Case4



        //汇总
        IntSummaryStatistics intSummaryStatistics =
                demoList.stream().collect(Collectors.summarizingInt(Transaction::getTotal));
        //最大值、最小值、数量和综合
        System.out.println(intSummaryStatistics.getMax());
        System.out.println(intSummaryStatistics.getMin());
        System.out.println(intSummaryStatistics.getCount());
        System.out.println(intSummaryStatistics.getSum());
        //接入新值
        intSummaryStatistics.accept(5);

        //汇总：求平均值
        System.out.println(demoList.stream().collect(Collectors.averagingInt(Transaction::getTotal)));

        //汇总：连接字符串
        System.out.println(demoList.stream().map(Transaction::getCurrency).map(Currency::getName)
                .collect(Collectors.joining(",")));

        //更加广义的归约汇总Collector.reduce()
        /**
         * reduce()接收三个参数
         * 1.归约操作的起始值，即初始值
         * 2.转换函数
         * 3.表示两个项目如何进行归约的方法
         */
        //collect方法通过map+reduce的方式同样可以解决
        System.out.println(demoList.stream().
                collect(Collectors.reducing(0,Transaction::getTotal,(a,b)->a*b/2)));



    }

    /**
     * 元素分组
     * Collector.toList汇总成List集合
     * Collector.groupingBy汇总成以指定的主键的Map集合
     */
    public static void collectionCase2(){
        //初始化
        List<Transaction> demoList = genNewTransactionList();

        //实现方式，groupingBy，实现思想后面会详细解释
        //groupingBy参数传分类函数，指定键类型
        //样例1，对一个交易列表按货币分组，获得该货币的所有交易额总和(返回一个Map<Currency,Integer>)
        Map<Currency,List<Transaction>> result = demoList.stream().
                collect(Collectors.groupingBy(Transaction::getCurrency));
        System.out.println(result);

        //样例2自定义分组，对一个交易列表按交易对应金额分组，分为s,m,l
        Map<String,List<Transaction>> result2 = demoList.stream().
                collect(Collectors.groupingBy(transaction -> transaction.getTotal() > 4000 ? "l" :
                        (transaction.getTotal() > 2000 ? "m" : "s")));
        System.out.println(result2);

        //样例3自定义多级分组，对一个交易列表按交易按货币名称区分，再分为s,m,l
        Map<Currency,Map<String,List<Transaction>>> result3 = demoList.stream().
                collect(Collectors.groupingBy(Transaction::getCurrency,
                        Collectors.groupingBy(transaction -> transaction.getTotal() > 4000 ? "l" :
                        (transaction.getTotal() > 2000 ? "m" : "s"))));
        System.out.println(result3);

        //样例4自定义分组，对子分组还可以做其他Collector方法的收集操作
        //统计货币下的交易总额
        Map<Currency,Integer> result4 = demoList.stream().
                collect(Collectors.groupingBy(Transaction::getCurrency,
                        Collectors.summingInt(Transaction::getTotal)));
        System.out.println(result4);


    }

    /**
     * 元素分区
     */
    public static void collectionCase3(){
        //分区和分组的特点在于，对于分区函数返回的true和false的不同，会分别将他们保存到两个列表当中。
        List<Transaction> demoList = genNewTransactionList();

        //样例1 以2000元金额为界限将交易分区
        Map<Boolean,List<Transaction>> result = demoList.stream().
                collect(Collectors.partitioningBy(tr -> tr.getTotal() > 2000));
        System.out.println(result);

        //样例2 以2000元金额为界限将交易按货币类型进行分区
        Map<Boolean,Map<Currency,List<Transaction>>> result2 = demoList.stream().
                collect(Collectors.partitioningBy(tr -> tr.getTotal() > 2000,
                        Collectors.groupingBy(Transaction::getCurrency)));
        System.out.println(result2);
    }

    /**
     * 自定义收集器：
     * Collector接口用于被collect方法调用，即定义一套收集器的规则
     *
     * 收集器Collector有下列方法组成：
     *  Supplier<A> supplier();
     *  BiConsumer<A, T> accumulator();
     *  Function<A, R> finisher();
     *  BinaryOperator<A> combiner();
     *  Set<Characteristics> characteristics();
     *  其中A、T、R。
     *  A表示累加器类型，用于在收集过程中积累结果
     *  T表示要收集的目标
     *  R表示结果目标
     *
     *  有了上述三元组我们就可以定义诸如收集器接口操作toList、toSet等
     *
     */
    public static void collectionCase4(){
        //以一个toList的收集器为例
        Collector<Integer,List<Integer>,List<Integer>> toListCollector =
                new Collector<Integer,List<Integer>,List<Integer>>() {
            @Override
            public Supplier<List<Integer>> supplier() {
                //1. supplier返回的方法引用，用于建立新的结果容器
                // 类似于方法的开头我们都会new一个resultEntity

                // 这里初始化一个空的list
                return ArrayList::new;
            }

            @Override
            public BiConsumer<List<Integer>,Integer> accumulator() {
                //2. accumulator方法返回的方法引用，用于累加元素到结果容器中

                //这里对每个元素做add操作
                return (a,t) -> a.add(t);
            }

            @Override
            public BinaryOperator<List<Integer>> combiner() {
                //3. combiner顾名思义会返回一个用于归约操作的方法引用，
                // 它定义在流的各个子部分进行并行处理时如何对各个子部分所得累加器合并

                //这里的合并操作其实就是addAll
                return (list1,list2) -> {list1.addAll(list2);return list1;};
            }

            @Override
            public Function<List<Integer>,List<Integer>> finisher() {
                //4. finisher方法提供的方法引用，会在遍历完整个流后执行，
                // 以便将整个累加对象转换成一个集合操作的最终的结果

                //这里我们需要的结果正好就是一个list，因此无需转换，返回本身即可
                return Function.identity();
            }

            @Override
            public Set<Characteristics> characteristics() {
                //5. 最后这个方法，定义了收集器的行为:
                //Characteristics.CONCURRENT;他表明accmulator函数可以从多个线程同时调用且收集器可以做并行归约，若没有标UNORDERED则并行归约操作仅能用于无序数据源
                //Characteristics.IDENTITY_FINISH; 他表明了收集器中的finisher返回的是一个恒等函数，即累加器结果可以作为最终结果，从而允许跳过finisher步骤。
                //Characteristics.UNORDERED; 归约结果不受流中项目的遍历和累积顺序的影响

                return null;
            }
        };

        //使用方式
        List<Transaction> list = genNewTransactionList();
        List<Integer> result = list.stream().map(Transaction::getTotal).collect(toListCollector);

        //或者通过自定义方式,见MethodRefCase.collectionCase()
        //需要注意的是这种方式永远是IDENTITY_FINISH和CONCURRENT的类型

    }

    public static void main(String[] args){
        collectionCase3();
    }

    /**
     * 交易
     */
    public class Transaction{
        //货币
        private Currency currency;
        //交易额
        private Integer total;

        public Currency getCurrency() {
            return currency;
        }

        public void setCurrency(Currency currency) {
            this.currency = currency;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        @Override
        public String toString() {
            return "Transaction{" +
                    "currency=" + currency +
                    ", total=" + total +
                    '}';
        }
    }

    /**
     * 货币
     */
    public class Currency{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Currency{" +
                    "name='" + name + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Currency currency = (Currency) o;
            return Objects.equals(getName(), currency.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getName());
        }
    }


    private static List<Transaction> genNewTransactionList(){
        return Arrays.asList(genNewTransaction("chn",1000),
                genNewTransaction("usd",2000),
                genNewTransaction("eup",1500),
                genNewTransaction("kor",4000),
                genNewTransaction("chn",1200),
                genNewTransaction("usd",1500),
                genNewTransaction("eup",3500),
                genNewTransaction("kor",900),
                genNewTransaction("chn",5000));
    }

    private static Transaction genNewTransaction(String curName,Integer total){
        CollectionCase collectionCase = new CollectionCase();
        Currency currency = collectionCase.new Currency();
        currency.setName(curName);
        Transaction transaction = collectionCase.new Transaction();
        transaction.setCurrency(currency);
        transaction.setTotal(total);
        return transaction;
    }
}
