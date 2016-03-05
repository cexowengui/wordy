package com.example.http.server;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;

public class ServletMain {
	public static void main(String[] args) throws Exception {

		// 实现了返回单个html页面的功能
		Server server = new org.eclipse.jetty.server.Server(8080);
		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setDirectoriesListed(true);
		// 缺省页面，不指定文件名就显示该页面，其余页面需要添加文件名.html来访问
		resourceHandler.setWelcomeFiles(new String[] { "index.html" });
		// 指定服务器的默认初始路径。"."表示项目wordy文件夹，这里设置成/resource中的html文件夹
		resourceHandler.setResourceBase("./src/main/resource/html/");
		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new Handler[] { resourceHandler, new DefaultHandler() });
		server.setHandler(handlers);

		server.start();
		server.join();
	}
}
