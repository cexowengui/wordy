package com.example.httpserver;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.handler.gzip.GzipHandler;

public class FileServerInJetty {
	public static void main(String[] args) throws Exception {

		Server server = new org.eclipse.jetty.server.Server(8080);

		
		ResourceHandler resource_handler = new ResourceHandler();
		
		resource_handler.setDirectoriesListed(false);
		resource_handler.setWelcomeFiles(new String[] { "index.html" });
		resource_handler.setResourceBase(".");

		// Add the ResourceHandler to the server.
		GzipHandler gzip = new GzipHandler();
		server.setHandler(gzip);
		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new Handler[] { resource_handler, new DefaultHandler() });
		gzip.setHandler(handlers);

		// Start things up! By using the server.join() the server thread will
		// join with the current thread.
		// See
		// "http://docs.oracle.com/javase/1.5.0/docs/api/java/lang/Thread.html#join()"
		// for more details.
		server.start();
		server.join();
	}

}
