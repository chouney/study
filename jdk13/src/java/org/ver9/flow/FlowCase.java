package org.ver9.flow;

import java.io.IOException;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.stream.IntStream;

/**
 * FlowAPI是Java9，concurrent包新增的工具
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
public class FlowCase {



    /**
     * 样例只是简单的展示了如何使用，FlowApi真正的用途是用于处理生产端和消费端吞吐量不一致，以及可能出现的丢弃阻塞等现象。
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) {
        SumSubscriber sumSubscriber = new SumSubscriber();

        Flow.Subscriber<String> processor = new IntegerProcessor(sumSubscriber);

        SubmissionPublisher<String> topSender = new SubmissionPublisher<>();

        topSender.subscribe(processor);

        IntStream.rangeClosed(1,20).forEach(i -> {
            System.out.println("生产submit字符串:"+i); topSender.submit(String.valueOf(i));
        });

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
