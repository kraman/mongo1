package com.mongodb.example;

import org.bson.types.ObjectId;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoURI;
import com.mongodb.WriteConcern;

@UrlBinding("/delete/{msgId}")
public class DeleteMessageActionBean implements ActionBean {
    private ActionBeanContext context;
    private String msgId;
    
    public ActionBeanContext getContext() { return context; }
    public void setContext(ActionBeanContext context) { this.context = context; }

    public void setMsgId(String msgId) { this.msgId = msgId; }
	public String getMsgId() { return msgId; }
	
	@DefaultHandler
    public Resolution deleteMessage() {
		
		try {
    		Mongo m = MongoHolder.MONGOS.connect(new MongoURI("mongodb://localhost"));
    		
    		DB db = m.getDB("msgsdb");
    		DBCollection coll = db.getCollection("msgs");
    		coll.setWriteConcern(WriteConcern.SAFE);
    		BasicDBObject query = new BasicDBObject();
            query.put("_id", new ObjectId(msgId));
    		coll.remove(query);
            System.out.println("Deleted Message");
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
		
    	return new RedirectResolution("/messages");
    }   
}