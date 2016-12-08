package com.baozun.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Visitor {
	private int id;  
    private String name;  
    private String email;  
    private int status;  
    private Date createTime;  
  
    public Visitor() {  
        // TODO Auto-generated constructor stub  
        setCreateTime(new Date());  
    }  
  
    public Visitor(String name, String email) {  
        this.setName(name);  
        this.setEmail(email);  
        this.setStatus(1);  
        this.setCreateTime(new Date());  
    }  
  
    public int getId() {  
        return id;  
    }  
  
    public void setId(int id) {  
        this.id = id;  
    }  
  
    public String getName() {  
        return name;  
    }  
  
    public void setName(String name) {  
        this.name = name;  
    }  
  
    public String getEmail() {  
        return email;  
    }  
  
    public void setEmail(String email) {  
        this.email = email;  
    }  
  
    public int getStatus() {  
        return status;  
    }  
  
    public void setStatus(int status) {  
        this.status = status;  
    }  
  
    public Date getCreateTime() {  
        return createTime;  
    }  
  
    public void setCreateTime(Date createTime) {  
        this.createTime = createTime;  
    }  
      
    @Override  
    public String toString() {  
        // TODO Auto-generated method stub  
        return String.format("{Id: %d, Name: %s, Email: %s, CreateTime: %s}", getId(), getName(), getEmail(),  
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCreateTime()));  
    }  
}
