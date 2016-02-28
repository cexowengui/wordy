package chatterbox;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ServerPool {
	public final static int PORT = 1918;
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(PORT);
		Executor service = Executors.newCachedThreadPool();  
		
		while (true) {
		      Socket connection = server.accept(); // Block waiting for connection
		      service.execute(new ServerThread(connection));
		    }
	}

}
