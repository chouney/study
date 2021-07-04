package org.ver9.flow;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * 整型消息订阅者
 */
public class IntegerProcessor extends SubmissionPublisher<Integer> implements Flow.Processor<String,Integer>{


    /**
     * 订阅上游消息
     */
    private Flow.Subscription upperSubscription;

    /**
     * 下游待发送消息
     */
    private Flow.Subscriber nextSubcriber;

    public IntegerProcessor(Flow.Subscriber subscriber) {
        this.nextSubcriber = subscriber;
        this.subscribe(subscriber);
    }

    /**
     * 订阅一个新的消息时触发次方法
     * @param subscription
     */
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        System.out.println("IntegerProcessor开始订阅"+subscription);
        this.upperSubscription = subscription;
        //拉取n个消息
        subscription.request(1);
    }


    /**
     * 有新的数据时触发此方法
     * @param item
     */
    @Override
    public void onNext(String item) {
        System.out.println("IntegerProcessor接收到数字："+item);

        //处理
        Integer num = Integer.valueOf(item);

        //交由下游处理
        submit(num);



        System.out.println("IntegerProcessor处理数字完成");
        this.upperSubscription.request(1);
    }

    /**
     * 在Publisher生产数据出现异常时调用此方法
     * @param throwable
     */
    @Override
    public void onError(Throwable throwable) {
        System.out.println("IntegerProcessor远端生产数字失败: "+ throwable.getMessage());

    }

    /**
     * 在是整个订阅结束时调用此方法
     */
    @Override
    public void onComplete() {
        System.out.println("IntegerProcessor订阅完成");
    }


}
