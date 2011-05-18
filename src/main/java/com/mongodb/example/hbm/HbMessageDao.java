package com.mongodb.example.hbm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.mongodb.example.MessageDao;

public class HbMessageDao extends MessageDao{
	@Override
	public void addMessage(String msg, String author) throws Exception{
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(new Message(msg, author));
		session.getTransaction().commit();
		session.close();
		sessionFactory.close();
	}
	
	@Override
	public void deleteMessage(String id) throws Exception{
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<?> result = session.createQuery("from Message where id=:id").setLong("id", Long.parseLong(id)).list();
		if( result.size() > 0 ){
			session.delete(result.get(0));
		}
		session.getTransaction().commit();
		session.close();
		sessionFactory.close();
	}
	
	@Override
	public Vector<Map<String, String>> getMessages() throws Exception{
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<?> result = session.createQuery("from Message").list();
		Vector<Map<String, String>> ret = new Vector<Map<String,String>>();
		for (Object object : result) {
			Message msg = (Message) object;
			Map<String, String> map = new HashMap<String, String>();
			map.put("_id", msg.getId().toString());
			map.put("msg", msg.getMsg());
			map.put("author", msg.getAuthor());
			ret.add(map);
		}
		session.getTransaction().commit();
		session.close();
		sessionFactory.close();
		return ret;
	}
}
