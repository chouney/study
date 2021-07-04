package org.function.model;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author chriszhang
 * @version 1.0
 * @date 2020/5/27
 */
public class ForkJoinSumCalculator extends RecursiveTask<Long> {

    public static final int THRESHOLD = 10;

    private long[] numbers; //传入数组
    private int end;//子任务结束位置
    private int start;//子任务开始位置

    public ForkJoinSumCalculator(long[] numbers) {
        this(numbers,0,numbers.length);
    }

    public ForkJoinSumCalculator(long[] numbers,int start,int end) {
        this.numbers = numbers;
        this.end = end;
        this.start = start;
    }

    /**
     * compute的实现结构类似于：
     * if(任务无需划分){
     * 顺序计算该任务
     * }else {
     *  将任务划分两个子任务
     *  递归调用本方法，拆分每个子任务，等待所有子任务完成
     *  合并每个子任务的结果
     * }
     *
     * fork/join的一些最佳用法
     * 1. 对一个任务调用join方法会阻塞调用方，应该调起所有子任务之后再进行join方法调用。
     * 2. (待分析原因)在RecursiveTask内部应该只用compute或者fork方法，而不是使用ForkJoinPool的invoke方法来计算
     * 3.
     *
     * @return
     */
    @Override
    protected Long compute() {
        int length = end-start;
        if(length <=THRESHOLD){
            return calculate();
        }
        ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers,start,start+ (length >>1));
        ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers,start+(length>> 1), end);
        leftTask.fork();
        //这里做了小优化，开启左分支线程，同时同步执行右分支逻辑，这样不会有大量线程挂起等待结果
        //优化前690msec， 优化后246mec
        long rightResult = rightTask.compute();
        long leftResult = leftTask.join();
        return leftResult+rightResult;
    }

    private long calculate(){
        long sum =0;
        for(int i = start;i<end;i++){
            sum+=numbers[i];
        }
        return sum;
    }
}
