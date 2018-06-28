package com.keygey.chapter.chapter1;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageBatch;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class SyncProducer {

	public static void main(String[] args) throws Exception {
		DefaultMQProducer producer = new DefaultMQProducer("TEST_ROCKET_MQ_GROUP");
		producer.setNamesrvAddr("193.112.91.102:9876");
		producer.start();
		producer.getDefaultMQProducerImpl().registerCheckForbiddenHook(null);
		for (int i = 0; i < 100; i++) {
			 Message msg = new Message("TopicTest" /* Topic */,
		                "TagA" /* Tag */,
		                ("Hello RocketMQ " +
		                    i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
		            );
			 SendResult result = producer.send(msg);
			 System.out.printf("%s%n", result);
		}
		producer.shutdown();
	}

}
