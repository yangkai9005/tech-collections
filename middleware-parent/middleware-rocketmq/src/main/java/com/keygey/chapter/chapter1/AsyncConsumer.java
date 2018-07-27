package com.keygey.chapter.chapter1;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullCallback;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.common.message.MessageQueue;

public class AsyncConsumer {
	private static final Map<MessageQueue, Long> OFFSE_TABLE = new HashMap<MessageQueue, Long>();
	public static void main(String[] args) throws Exception {
		pullNotFoundBlockMsg();
	}
	private static void pullNotFoundBlockMsg() throws Exception{
		DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("PullConsumer");
		consumer.setNamesrvAddr("localhost:9876");
		consumer.start();
		Set<MessageQueue> msgQueue = consumer.fetchSubscribeMessageQueues("TopicTestA");
		for (final MessageQueue mq : msgQueue) {
			pullMsg(consumer, mq);
		}

		consumer.shutdown();
	}
	
	private static void pullMsg(final DefaultMQPullConsumer consumer,final MessageQueue mq) throws Exception{
		consumer.pullBlockIfNotFound(mq,null, getMessageQueueOffset(mq), 30,new PullCallback() {
			public void onSuccess(PullResult pullResult) {
				System.out.println(pullResult);
				
			}
			
			public void onException(Throwable e) {
				System.out.println(e.getMessage());
			}
		});
	}
	
	
	private static long getMessageQueueOffset(MessageQueue mq) {
		Long offset = OFFSE_TABLE.get(mq);
		if ( offset != null )
			return offset;

		return 0;
	}

	private static void putMessageQueueOffset(MessageQueue mq, long offset) {
		OFFSE_TABLE.put(mq, offset);
	}

}
