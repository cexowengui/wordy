package chatterbox;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class TcpClient {
	public final static int PORT = 1918;
	public final static String SERVER = "127.0.0.1";
	public final static int TIMEOUT = 130000;
	String clientName;

	public static void main(String[] args) {
		new TcpClient().connectServer();
	}

	public void connectServer() {
		try {
			Socket client = new Socket(SERVER, PORT);
			clientName = client.getInetAddress().toString() + ":" + client.getPort();
			System.out.println("连上服务器:[" + clientName + "]");
			new ReadThread(client).start();
			new WriteThread(client).start();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
