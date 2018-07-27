package com.keygey.chapter.chapter2;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class ProducerOrder {

	public static void main(String[] args) throws UnsupportedEncodingException {
        try {
        	DefaultMQProducer producer = new DefaultMQProducer("OrderProducer");
            producer.setNamesrvAddr("localhost:9876");
            producer.start();

            String[] tags = new String[] {"TagA", "TagB", "TagC", "TagD", "TagE"};
            for (int i = 0; i < 100; i++) {
                int orderId = i % 10;
                Message msg =
                    new Message("TopicTestOrder", tags[i % tags.length], "KEY" + i,
                        ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                //保证同一个订单在同一个queue当中
                SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                        Integer id = (Integer) arg;
                        int index = id % mqs.size();
                        return mqs.get(index);
                    }
                }, orderId);

                System.out.println(sendResult+"  orderId:"+orderId);
            }

            producer.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
