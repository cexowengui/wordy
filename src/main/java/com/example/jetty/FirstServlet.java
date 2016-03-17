package com.example.jetty;



import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;


public class FirstServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String msg = "Hello World!";  

    public FirstServlet() {
    }  

    public FirstServlet(String msg) {
        this.msg = msg;
    }  

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String name = request.getParameter("name");
    	String password = request.getParameter("password"); 

    	response.setCharacterEncoding("UTF-8");
        //response.setContentType("text/html");
    	response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        //response.getWriter().println("&lt;h1&gt;" + msg + "&lt;/h1&gt;");
        //response.getWriter().println("测试中文" + request.getSession(true).getId());
        
        JSONObject studentJSONObject = new JSONObject();
        studentJSONObject.put("name", "Jason");  
        studentJSONObject.put("id", 20130001);  
        studentJSONObject.put("phone", "13579246810");
        studentJSONObject.put("phone2", "13579246810");
        
        
        if(name!=null)
        	studentJSONObject.put("名字", name);  

        if(password!=null)
        	studentJSONObject.put("密码", password); 
        response.getWriter().println(studentJSONObject.toString());

    }
}
