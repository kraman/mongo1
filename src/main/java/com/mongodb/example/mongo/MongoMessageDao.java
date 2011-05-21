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
package com.mongodb.example.mongo;

import java.util.Map;
import java.util.Vector;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.example.MessageDao;

public class MongoMessageDao extends MessageDao{
    private Mongo m;

    public MongoMessageDao() throws Exception{
        //com.mongodb.example.mongo_servers
		Properties exampleProps = new Properties();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		exampleProps.load(classLoader.getResourceAsStream("/example.properties"));
		String mongoServersStr = exampleProps.getProperty("com.mongodb.example.mongo_servers", "HbMessageDao");
        String mongoServers[] = mongoServersStr.split(",");
        if(mongoServers.length == 1){
            m = new Mongo(new ServerAddress(mongoServersStr));
        }else{
            List<ServerAddress> addr = new ArrayList<ServerAddress>();
            for(String a : mongoServers){
                addr.add(new ServerAddress(a));
            }
            m = new Mongo(addr);
        }
    }

	@Override
	public void addMessage(String msg, String author) throws Exception{
		DB db = m.getDB("msgsdb");
		DBCollection coll = db.getCollection("msgs");
		coll.setWriteConcern(WriteConcern.SAFE);
		
		BasicDBObject doc = new BasicDBObject();

        doc.put("msg", msg);
        doc.put("author", author);
        coll.insert(doc);
        System.out.println("Added Message");
	}
	
	@Override
	public void deleteMessage(String msgId) throws Exception{
		DB db = m.getDB("msgsdb");
		DBCollection coll = db.getCollection("msgs");
		coll.setWriteConcern(WriteConcern.SAFE);
		BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(msgId));
		coll.remove(query);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Vector<Map<String, String>> getMessages() throws Exception{
		Vector<Map<String, String>> msgs = new Vector<Map<String,String>>();
		DB db = m.getDB("msgsdb");
		DBCollection coll = db.getCollection("msgs");
		DBCursor cursor = coll.find();
		System.out.println("Cursor count:"+cursor.count());
		System.out.println("vector size:"+msgs.size());
		
		while(cursor.hasNext()){
		    DBObject obj = cursor.next();
		    msgs.add(obj.toMap());
		}
		return msgs;
	}
}
