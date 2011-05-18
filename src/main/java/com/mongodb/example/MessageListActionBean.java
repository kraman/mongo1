package com.mongodb.example;

import java.util.Map;
import java.util.Vector;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/messages")
public class MessageListActionBean implements ActionBean {
	
	private ActionBeanContext context;
	
	private Vector<Map<String, String>> msgs = new Vector<Map<String, String>>();
	
	public Vector<Map<String, String>> getMsgs() {return msgs;}
	public void setMsgs(Vector<Map<String, String>> v){ msgs = v;}

	@Override
	public ActionBeanContext getContext() {return context;}

	@Override
	public void setContext(ActionBeanContext context) {this.context = context;	}
	
	@DefaultHandler
    public Resolution addition() {
        getMsgList();
        return new ForwardResolution("/msglist.jsp");
    }
	
	 public void getMsgList() {
			try {
				MessageDao dao = MessageDao.getDao();
				setMsgs(dao.getMessages());
	    		System.out.println("vector size:"+msgs.size());
	    		} catch (Exception e) {
	    		e.printStackTrace();
	    		return;
	    	}
	    }
}