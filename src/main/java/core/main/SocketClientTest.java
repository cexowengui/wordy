package core.main;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketClientTest {
	private DataOutputStream output;
	private DataInputStream input;
	private String clientName;

	public static void main(String[] args) {
		System.out.println(System.getProperty("java.class.path"));
		// 在main函数中，启动服务器的socket
		new SocketClientTest().ConnectServer();
	}

	public void ConnectServer() {
		try {
			Socket socket = new Socket("127.0.0.1", 12345);
			clientName = socket.getInetAddress().toString();
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());

			new readServer().start();
			new writeServer().start();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public class readServer extends Thread {
		private Socket client;

		public void run() {
			String msg;
			try {
				while (true) {
					msg = input.readUTF();
					if (msg != null)
						System.out.println("收到消息：【" + clientName + "】 " + msg);
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}

	public class writeServer extends Thread {
		private Socket client;

		public void run() {
			try {
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
				String userInput;
				while (true) {
					if (stdIn.ready()) {
						userInput = stdIn.readLine();
						if (!userInput.equals("exit")) {
							output.writeUTF(userInput);
							System.out.println("已发送消息给【" + clientName + "】" + userInput);
						}
					}
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}

}
