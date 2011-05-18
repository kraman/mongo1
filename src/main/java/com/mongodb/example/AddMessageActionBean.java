package com.mongodb.example;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/add")
public class AddMessageActionBean implements ActionBean {
    private ActionBeanContext context;
    private String author;
    private String msg;
    
    public ActionBeanContext getContext() { return context; }
    public void setContext(ActionBeanContext context) { this.context = context; }

    public String getMsg() { return msg; }
    public void setMsg(String s) { this.msg = s; }

    public String getAuthor() { return author; }
    public void setAuthor(String s) { this.author = s; }

   
    @DefaultHandler
    public Resolution loadPage() {
    	return new ForwardResolution("/addmsg.jsp");
    }
    
    public Resolution addMsg() {
    	
    	try {
    		MessageDao dao = MessageDao.getDao();
    		dao.addMessage(getMsg(), getAuthor());
            System.out.println("Added Message");
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
       
        return new RedirectResolution("/messages");
    }
    

    
}