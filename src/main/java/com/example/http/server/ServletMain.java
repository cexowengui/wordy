package com.example.http.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class ServletMain {
	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);
		ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
        //mapping a Servlet to handler
        handler.addServletWithMapping(IndexHandler.class, "/*");
        
        //start up
        server.start();
        server.join();
        
	}
}
