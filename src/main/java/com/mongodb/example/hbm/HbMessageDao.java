/*
 * Copyright (c) 2011, 10gen
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * - Redistributions of source code must retain the above copyright notice, 
 *   this list of conditions and the following disclaimer.
 * - Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation 
 *   and/or other materials provided with the distribution.
 * - Neither the name of the 10gen nor the names of its contributors 
 *   may be used to endorse or promote products derived from this software without 
 *   specific prior written permission.
 *   
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF 
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
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
