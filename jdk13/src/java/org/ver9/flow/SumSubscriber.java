package org.ver9.flow;

import java.util.concurrent.Flow;

/**
 * 整型消息订阅者
 */
public class SumSubscriber implements Flow.Subscriber<Integer>{


    private Flow.Subscription subscription;

    /**
     * 订阅一个新的消息时触发次方法
     * @param subscription
     */
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        System.out.println("SumSubscriber开始订阅");
        this.subscription = subscription;
        //拉取n个消息
        subscription.request(1);
    }

    /**
     * 有新的数据时触发此方法
     * @param item
     */
    @Override
    public void onNext(Integer item) {
        System.out.println("SumSubscriber接收到数字："+item);

        System.out.println(item+5);


        System.out.println("SumSubscriber处理数字完成");
        subscription.request(1);
    }

    /**
     * 在Publisher生产数据出现异常时调用此方法
     * @param throwable
     */
    @Override
    public void onError(Throwable throwable) {
        System.out.println("SumSubscriber生产数字失败");

    }

    /**
     * 在是整个订阅结束时调用此方法
     */
    @Override
    public void onComplete() {
        System.out.println("SumSubscriber订阅完成");
    }
}
