package chatterbox;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ServerPool {
	public final static int PORT = 1918;
	ArrayList<Socket> sockets = new ArrayList<Socket>();
	ServerSocket server;
	Executor service = Executors.newCachedThreadPool();

	public static void main(String[] args) throws IOException {
		new ServerPool().openServer();
	}

	void openServer() throws IOException {
		server = new ServerSocket(PORT);
		while (true) {
			Socket connection = server.accept();
			sockets.add(connection);
			service.execute(new ServerThread(connection));
		}
	}

}
