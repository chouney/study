package org.ver9.flow;

import java.io.IOException;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.stream.IntStream;

public class FlowCase {

    /**
     * FlowAPI主要专注于处理数据的流通，比如对数据的请求、减速、丢弃和阻塞等，而StreamAPI则是用于对数据集的处理
     *
     *
     * Flow定义了统一的响应式编程规范，其中包含
     * Publisher接口，生产数据和控制事件
     * Subscriber接口，消费数据和事件
     * Subscription接口，用于链接Publisher和Subscriber
     * Processor接口，用于转换Publisher到Subscriber（类似于管道，见用例）
     * Flow提供了SubmissionPublisher和Publisher实现
     */

    public static void main(String[] args) throws IOException {
        SumSubscriber sumSubscriber = new SumSubscriber();

        IntegerProcessor processor = new IntegerProcessor(sumSubscriber);

        SubmissionPublisher<Integer> topSender = new SubmissionPublisher<>();

        topSender.subscribe(sumSubscriber);

        IntStream.rangeClosed(1,20).forEach(i -> {
            System.out.println("submit"); topSender.submit(i);
        });

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
