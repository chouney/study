package org.function;

import org.function.model.ForkJoinSumCalculator;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * 并行数据处理
 *
 * 使用Stream的并行处理要注意一下几点：
 * 1. 以基准测试为准
 * 2. 留意装箱，尽量用LongStream,IntStream来避免装箱拆箱
 * 3. 留意一些操作，避免一些比如findFirst和limit这样一来元素顺序的操作
 * 4. 留意数据结构本身一否易于分解，比如ArrayList相比LinkedList不用遍历就可以实现分解，
 * 另外用range工厂方法创建的原始类型流也可以快速分解。
 * 5. 注意合并操作，如果合并成本过大，可能会抵消并行的优化
 *
 * @author chriszhang
 * @version 1.0
 * @date 2020/5/25
 */
public class ParallelCase {
    /**
     * 在了解java8如何整合fork/join框架的之前
     * 我们需要了解java8在使用它的时候是如何管理线程池的，以及为用户提供了哪些线程管理方法
     */
    public static void forkJoinPoolCase(){
        /**
         * parallelStream并行流使用了默认的ForkJoinPool
         * 他的线程数量为默认的线程数量，线程数=核数，即Runtime.getRuntime().availableProcessors()得到的
         *
         * 1. 可以通过下述方式修改线程池大小，但需要注意的是，
         *  这个是一个全局设置，目前无法专门为某个指定并行流指定线程数。
         */
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","12");


        /**
         * Fork/Join任务分为RecursiveTask和RecursiveAction两类
         * RecursiveTask接收任务compute()计算后返回R
         * RecursiveAction只接收任务计算，不返回结构做join
         */
        long number[] = LongStream.rangeClosed(1,10000000).toArray();
        RecursiveTask<Long> customTask  = new ForkJoinSumCalculator(number);
        long start = System.currentTimeMillis();
        Long result = forkJoinPool.invoke(customTask);
        System.out.println("result :"+result+", in "+(System.currentTimeMillis() - start)+" msec");
    }

    public static void badStreamCase(){
        /**
         * 并行实践的一个badCase，当执行的函数不符合函数的无状态特性，导致并行执行时每次结果都不一样。
         */
        ParallelCase.Accumulator accumulator = new Accumulator();
        long start = System.currentTimeMillis();
        LongStream.rangeClosed(1,10000000).forEach(accumulator::add);
        System.out.println("result :"+accumulator.total+", in "+(System.currentTimeMillis() - start)+" msec");

        ;
        System.out.println("result :"+LongStream.rangeClosed(1,10000000).parallel().sum()+", in "+(System.currentTimeMillis() - start)+" msec");


        accumulator.total = 0;
        start = System.currentTimeMillis();
        LongStream.rangeClosed(1,10000000).parallel().forEach(accumulator::add);
        System.out.println("result :"+accumulator.total+", in "+(System.currentTimeMillis() - start)+" msec");

        accumulator.total = 0;
        start = System.currentTimeMillis();
        LongStream.rangeClosed(1,10000000).parallel().forEach(accumulator::add);
        System.out.println("result :"+accumulator.total+", in "+(System.currentTimeMillis() - start)+" msec");

        accumulator.total = 0;
        start = System.currentTimeMillis();
        LongStream.rangeClosed(1,10000000).parallel().forEach(accumulator::add);
        System.out.println("result :"+accumulator.total+", in "+(System.currentTimeMillis() - start)+" msec");
    }

    public static void main(String[] args){
        badStreamCase();
        forkJoinPoolCase();
//
    }

    public static class Accumulator{
        public long total = 0;
        public void add(long value) {total += value;}
    }

}
