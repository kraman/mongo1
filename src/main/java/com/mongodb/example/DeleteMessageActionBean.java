package com.mongodb.example;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

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
			MessageDao dao = MessageDao.getDao();
			dao.deleteMessage(getMsgId());
            System.out.println("Deleted Message");
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
		
    	return new RedirectResolution("/messages");
    }   
}