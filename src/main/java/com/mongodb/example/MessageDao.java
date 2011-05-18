package com.mongodb.example;

import java.util.Map;
import java.util.Properties;
import java.util.Vector;

public abstract class MessageDao {
	public static MessageDao getDao() throws Exception{
		Properties exampleProps = new Properties();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		exampleProps.load(classLoader.getResourceAsStream("/example.properties"));
		Class<?> daoClass = Class.forName(exampleProps.getProperty("com.mongodb.example.dao", "HbMessageDao"));
		return (MessageDao) daoClass.newInstance();
	}
	
	public abstract void addMessage(String msg, String author) throws Exception;
	public abstract void deleteMessage(String id) throws Exception;
	public abstract Vector<Map<String, String>> getMessages() throws Exception;
}
