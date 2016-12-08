package com.baozun.DAO;



import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.baozun.domain.Visitor;



public class CommonDao {
	private static Connection getConnection() {  
	    java.sql.Connection conn = null;  
	    try {  
	        Class.forName("com.mysql.jdbc.Driver");  
	        String url = "jdbc:mysql://localhost:3306/crm?user=root&password=123";
	         conn = DriverManager.getConnection(url); 
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	    return conn;  
	} 
	
	 public static void main(String[] args) {
		Visitor v = new Visitor();
//		v.setEmail("email");
//		v.setName("yang");
//		insertVisitor(v);
		System.err.println(convert("DATA_OBJECT_NAME"));;
	}
	
	 /* 
	  * 新增Visitor, ScalarHandler的demo 
	  */  
	 public static void insertVisitor(Visitor visitor) {  
	     Connection conn = getConnection();  
	     QueryRunner qr = new QueryRunner();  
	     String sql = "insert into visitor (Name, Email, Status, CreateTime) values (?, ?, ?, ?)";  
	     try {  
	         int count = qr.update(conn, sql, visitor.getName(), visitor.getEmail(), 1, new Date());  
	         BigInteger newId = (BigInteger) qr.query(conn, "select last_insert_id()", new ScalarHandler<BigInteger>(1));  
	         visitor.setId(Integer.valueOf(String.valueOf(newId)));  
	         System.out.println("新增" + count + "条数据=>Id：" + newId);  
	     } catch (SQLException e) {  
	         e.printStackTrace();  
	     }  
	 }
	 /**
	  * 删除方法
	  * @param id
	  */
	 public static void deleteVisitor(int id) {  
		    Connection conn = getConnection();  
		    QueryRunner qr = new QueryRunner();  
		    String sql = "delete from visitor where status>0 and id=?";  
		    try {  
		        int count = qr.update(conn, sql, id);  
		        System.out.println("删除" + count + "条数据。");  
		    } catch (SQLException e) {  
		        // TODO: handle exception  
		        e.printStackTrace();  
		    }  
		}
	 
	 /**
	  * 更新操作
	  * 
	  */
	 public static void updateVisitor(int id) {  
		    Visitor visitor = retrieveVisitor(id);  
		    System.out.println("更新前：" + visitor);  
		    Connection conn = getConnection();  
		    String updateFieldStr = visitor.getName();  
		    QueryRunner qr = new QueryRunner();  
		    String sql = "update visitor set Name = ?, Email = ?, Status = ?, CreateTime = ? where status>0 and Id = ?";  
		    if (updateFieldStr.contains("updated")) {  
		        updateFieldStr = updateFieldStr.substring(0, updateFieldStr.indexOf("updated"));  
		    } else {  
		        updateFieldStr = updateFieldStr + "updated";  
		    }  
		    visitor.setName(updateFieldStr);  
		    try {  
		        int count = qr.update(conn, sql, new Object[] { visitor.getName(), visitor.getName(), visitor.getStatus(),  
		                visitor.getCreateTime(), visitor.getId() });  
		        System.out.println("更新了" + count + "条数据");  
		        System.out.println("更新后：" + visitor);  
		    } catch (SQLException e) {  
		        // TODO: handle exception  
		        e.printStackTrace();  
		    }  
		}
	 
	 
	 
	 /**
	  * 查询返回值    基于beanHandle
	  * @param id
	  * @return
	  */
	 public static Visitor retrieveVisitor(int id) {  
		    Connection conn = getConnection();  
		    Visitor visitor = null;  
		    QueryRunner qr = new QueryRunner();  
		    String sql = "select * from visitor where status>0 and id=?";          
		    try {  
		        visitor = (Visitor) qr.query(conn, sql, new BeanHandler<Visitor>(Visitor.class), id);  
		        System.out.println(visitor);  
		        return visitor;  
		    } catch (Exception e) {  
		        e.printStackTrace();  
		    }  
		    return visitor;  
		}
	 
	 /**
	  * 查询返回值    基于BeanListHandler方法
	  */
	 public static void getVisitorList() {  
		    Connection conn = getConnection();  
		    QueryRunner qr = new QueryRunner();  
		    String sql = "select * from visitor where status>0";  
		    try {  
		        List<Visitor> ls = qr.query(conn, sql, new BeanListHandler<Visitor>(Visitor.class));  
		        for (Visitor visitor : ls) {  
		            System.out.println(visitor);  
		        }  
		    } catch (SQLException e) {  
		        // TODO Auto-generated catch block  
		        e.printStackTrace();  
		    }  
		} 
	 
	 /**
	  * MapHandler操作
	  * @param id
	  */
	 public static void getVisitWithMap(int id) {  
		    Connection conn = getConnection();  
		    QueryRunner qr = new QueryRunner();  
		    String sql = "select * from visitor where status>0 and id=?";  
		    try {  
		        Map<String, Object> map = qr.query(conn, sql, new MapHandler(), id);  
		        Integer visitorId = Integer.valueOf(map.get("Id").toString());  
		        String visitorName = map.get("Name").toString();  
		        String visitorEmail = map.get("Email").toString();  
		        Integer visitorStatus = Integer.valueOf(map.get("Status").toString());  
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		        Date visitorCreateTime = sdf.parse(map.get("CreateTime").toString());  
		        Visitor visitor = new Visitor(visitorName, visitorEmail);  
		        visitor.setId(visitorId);  
		        visitor.setStatus(visitorStatus);  
		        visitor.setCreateTime(visitorCreateTime);  
		        System.out.println(visitor);  
		    } catch (Exception e) {  
		        // TODO: handle exception  
		        e.printStackTrace();  
		    }  
		} 
	 
	 /**
	  * 基于  MapListHandler
	  */
	 public static void getVisitWithMapLs() {  
	        Connection conn = getConnection();  
	        QueryRunner qr = new QueryRunner();  
	        String sql = "select * from visitor where status>0";  
	        try {  
	            List<Map<String, Object>> mapLs = qr.query(conn, sql, new MapListHandler());  
	            for (Map<String, Object> map : mapLs) {  
	                Integer visitorId = Integer.valueOf(map.get("Id").toString());  
	                String visitorName = map.get("Name").toString();  
	                String visitorEmail = map.get("Email").toString();  
	                Integer visitorStatus = Integer.valueOf(map.get("Status").toString());  
	                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	                Date visitorCreateTime = sdf.parse(map.get("CreateTime").toString());  
	                Visitor visitor = new Visitor(visitorName, visitorEmail);  
	                visitor.setId(visitorId);  
	                visitor.setStatus(visitorStatus);  
	                visitor.setCreateTime(visitorCreateTime);  
	                System.out.println(visitor);  
	            }  
	        } catch (Exception e) {  
	            // TODO: handle exception  
	            e.printStackTrace();  
	        }  
	    }  
	 
     /**
      * 将数据库字段转换成驼峰式命名式属性字段
      * DATA_OBJECT_NAME -> dataObjectName
      */
     private static String convert(String objName) {
             StringBuilder result = new StringBuilder();
             String[] tokens = objName.split("_");
             for (String token : tokens) {
                     if (result.length() == 0)
                             result.append(token.toLowerCase());
                     else
                             result.append(capitalize(token.toLowerCase()));
             }
             return result.toString();
     }
     
     /**
      * 这个方法本来common-lang3中的方法
      * @param str
      * @return
      */
 	public static String capitalize(String str) {
		int strLen;

		if ((str == null) || ((strLen = str.length()) == 0)) {
			return str;
		}

		return new StringBuffer(strLen)
				.append(Character.toTitleCase(str.charAt(0)))
				.append(str.substring(1)).toString();
	}

}
