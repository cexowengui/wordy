package chatterbox;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerThread implements Runnable {
	private  Socket client;               // Socket connect to client
	String clientName;
	public ServerThread(Socket client) {
		this.client = client;
	}
	public void handleConnection(){
		clientName = client.getInetAddress().toString()+": " +client.getPort();
        new ReadThread(client).start();
        new WriteThread(client).start();
	}

	public void run() {
		handleConnection();
	}
	

	
	class ReadThread extends Thread {
		Socket client;

		public ReadThread(Socket socket) {
			this.client = socket;
		}

		@Override
		public void run() {
			String msg;
			try {
				DataInputStream inputStream = new DataInputStream(client.getInputStream());
				while (true) {
					msg = inputStream.readUTF();
					if (msg != null) {
						System.out.println("收到[" + clientName + "]消息: " + msg);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	class WriteThread extends Thread {
		Socket client;

		public WriteThread(Socket socket) {
			this.client = socket;
		}

		@Override
		public void run() {
			String msg;
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				DataOutputStream outputStream = new DataOutputStream(client.getOutputStream());
				while (true) {
					if (in.ready()) {
						msg = in.readLine();
						if (msg != null) {
							outputStream.writeUTF(msg);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
	
}
