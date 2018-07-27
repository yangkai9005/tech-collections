package com.keygey.chapter.chapter1;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.common.message.MessageQueue;

public class SyncConsumer {
	private static final Map<MessageQueue, Long> OFFSE_TABLE = new HashMap<MessageQueue, Long>();

	public static void main(String[] args) throws Exception {
		balanceFetch();
	}
	
	private static void pullNotFoundBlockMsg() throws Exception{
		DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("PullConsumer");
		consumer.setNamesrvAddr("localhost:9876");
		consumer.start();
		Set<MessageQueue> msgQueue = consumer.fetchSubscribeMessageQueues("TopicTestA");
		for (MessageQueue mq : msgQueue) {
			boolean flag = true;
			while (flag) {
				try {
					PullResult pullResult = consumer.pullBlockIfNotFound(mq,null, getMessageQueueOffset(mq), 30);
					System.out.printf("%s%n", pullResult);
					putMessageQueueOffset(mq, pullResult.getNextBeginOffset());
					switch (pullResult.getPullStatus()) {
					case FOUND:
						break;
					case NO_MATCHED_MSG:
						break;
					case NO_NEW_MSG:
						flag = false;
						break ;
					case OFFSET_ILLEGAL:
						break;
					default:
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		consumer.shutdown();
	}
	
	private static void pullNotFoundRetrunMsg() throws Exception{
		DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("PullConsumer");
		consumer.setNamesrvAddr("localhost:9876");
		consumer.start();
		Set<MessageQueue> msgQueue = consumer.fetchSubscribeMessageQueues("TopicTestA");
		for (MessageQueue mq : msgQueue) {
			boolean flag = true;
			while (flag) {
				try {
					PullResult pullResult = consumer.pull(mq,null, getMessageQueueOffset(mq), 30);
					System.out.printf("%s%n", pullResult);
					putMessageQueueOffset(mq, pullResult.getNextBeginOffset());
					switch (pullResult.getPullStatus()) {
					case FOUND:
						break;
					case NO_MATCHED_MSG:
						break;
					case NO_NEW_MSG:
						flag = false;
						break ;
					case OFFSET_ILLEGAL:
						break;
					default:
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		consumer.shutdown();
	}
	
	private static void balanceFetch() throws Exception{
		DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("PullConsumer");
		consumer.setNamesrvAddr("localhost:9876");
		consumer.start();
		/*Set<MessageQueue> msgQueue = consumer.fetchMessageQueuesInBalance("TopicTestA");
		System.out.println(msgQueue.size());
		for (MessageQueue mq : msgQueue) {
			boolean flag = true;
			while (flag) {
				try {
					PullResult pullResult = consumer.pull(mq,null, getMessageQueueOffset(mq), 30);
					System.out.printf("%s%n", pullResult);
					putMessageQueueOffset(mq, pullResult.getNextBeginOffset());
					switch (pullResult.getPullStatus()) {
					case FOUND:
						break;
					case NO_MATCHED_MSG:
						break;
					case NO_NEW_MSG:
						flag = false;
						break ;
					case OFFSET_ILLEGAL:
						break;
					default:
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}*/
		//consumer.shutdown();
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
