package com.keygey.demo.chapter1;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.keygey.util.ConnectionFactoryUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Send {
	private static final String QUEUE_NAME = "hello";
	public static void main(String[] args) {
		try(Connection conn = ConnectionFactoryUtils.getInstance();
			Channel channel = conn.createChannel();) {
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			String message = "Hello World!";
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
			System.out.println(" [x] Sent '" + message + "'");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		
	}

}
