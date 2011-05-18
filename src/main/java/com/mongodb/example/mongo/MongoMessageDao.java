package com.mongodb.example.mongo;

import java.util.Map;
import java.util.Vector;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoURI;
import com.mongodb.WriteConcern;
import com.mongodb.example.MessageDao;

public class MongoMessageDao extends MessageDao{
	@Override
	public void addMessage(String msg, String author) throws Exception{
		Mongo m = MongoHolder.MONGOS.connect(new MongoURI("mongodb://localhost"));
		
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
		Mongo m = MongoHolder.MONGOS.connect(new MongoURI("mongodb://localhost"));
		
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
		Mongo m = MongoHolder.MONGOS.connect(new MongoURI("mongodb://localhost"));
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
