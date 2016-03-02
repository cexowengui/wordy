package com.example.httpserver;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;


/**
 * @project SimpleHttpServer
 * @author 
 * @vresion 1.0 
 * @description  自定义的http服务器
 * 我们需要提供http服务，以便于网页可以展示一些东西，所以需要用到web服务器，
 * java web一般情况下都是用tomcat做服务器，但是我们这里不用这种玩意，自己用jdk自带的包简单实现一个，
 * 也可以用jetty这种极其轻量的包，这里使用自带的com.sun.net.httpserver.HttpServer
 */
@SuppressWarnings("restriction")
public class MyHttpServer {
    //启动服务，监听来自客户端的请求
	public static void httpserverService() throws IOException {
		HttpServerProvider provider = HttpServerProvider.provider();
		HttpServer httpserver =provider.createHttpServer(new InetSocketAddress(6666), 100);//监听端口6666,能同时接 受100个请求
		httpserver.createContext("/myApp", new MyHttpHandler()); 
		httpserver.setExecutor(null);
		httpserver.start();
		System.out.println("server started");
	}
	//Http请求处理类

	static class MyHttpHandler implements HttpHandler {
		public void handle(HttpExchange httpExchange) throws IOException {
			SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String TimeString = time.format(new java.util.Date());
			String responseMsg = "ok " + TimeString;   //响应信息
			InputStream in = httpExchange.getRequestBody(); //获得输入流
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String temp = null;
			while((temp = reader.readLine()) != null) {
				System.out.println("client request:"+temp);
			}
			httpExchange.sendResponseHeaders(200, responseMsg.length()); //设置响应头属性及响应信息的长度
			OutputStream out = httpExchange.getResponseBody();  //获得输出流
			out.write(responseMsg.getBytes());
			out.flush();
			httpExchange.close();                               
			
		}
	}
	public static void main(String[] args) throws IOException {
		httpserverService();
	}
}
