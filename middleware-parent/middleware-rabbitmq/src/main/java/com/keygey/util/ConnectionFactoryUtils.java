package com.keygey.util;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionFactoryUtils {
	private static final ConnectionFactory CONNECTION_FACTROY = new ConnectionFactory();
	private static final String HOST = "193.112.91.102";
	private static final Integer PORT = 5672;
	public static Connection getInstance() throws IOException, TimeoutException{
		CONNECTION_FACTROY.setHost(HOST);
		CONNECTION_FACTROY.setPort(PORT);
		return CONNECTION_FACTROY.newConnection();
	}
}
