package core.main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import core.model.ClientSocket;

import java.io.IOException;

public class ClientHandler extends Thread {

	private ClientSocket clientSocket;

	public ClientHandler(ClientSocket client) {
		this.clientSocket = client;
	}

	public void run() {
		String msg;
		DataInputStream input = null;
		DataOutputStream output = null;

		try {
			String clientName = this.clientSocket.getSocket().getInetAddress().toString();			
			input = this.clientSocket.getInput();
			output = this.clientSocket.getOutput();			
			while ((msg = input.readUTF()) != null) {
				System.out.println("收到消息：【" + clientName + "】 " + msg);
				/* 
				 * 开启一个消息处理和回复线程，把接收到的消息扔给该线程的子线程去处理
				 * 这里已经是一个处理某个客户端的子线程了，为什么这里还需要开一个子线程的子线程？因为这里接收到了
				 * 客户端的发送过来的请求，那么需要处理它，如果直接在本函数处理，那么这个时候客户端再发送一个请求，
				 * 就来不及响应了，理论上像QQ微信这种具有客户端的请求不会那么快，因为所有请求都是靠人操作发送的，
				 * 但是如果我们这个系统是消息系统，那么必然是要提供http和sdk给外界的，那么客户端发送的请求都是
				 * 程序发送的，会 非常快，所以我们这里还是开一个处理请求的线程，本线程只负责 消息接受。这样做代价
				 * 是服务器需要频繁创建销毁线程，所以需要使用线程池。两种方式各有利弊。
				 */
				new MessageReplyThread(clientSocket, msg).start();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			System.out.println("客户端断开，对应的客户端线程退出");
			try {
				this.clientSocket.getInput().close();
				this.clientSocket.getOutput().close();
				this.clientSocket.getSocket().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

class MessageReplyThread extends Thread {
	private ClientSocket clientSocket;
	private String message;

	public MessageReplyThread(ClientSocket client, String message) {
		this.clientSocket = client;		
		this.message = message;
	}

	public void run() {		
		try {
			String clientName = this.clientSocket.getSocket().getInetAddress().toString() + 
					this.clientSocket.getSocket().getPort();
			
			// while (true) {//因为服务器只有收到客户端的请求才会回复消息，不会主动发送消息，所以这不需要while
			this.clientSocket.getOutput().writeUTF(message + " reply");
			this.clientSocket.getOutput().flush();
			System.out.println("已回复消息给客户端【" + clientName + "】" + message + " reply");
			// }
			System.out.println("服务器已经完成客户端请求处理并回复消息给客户端，回复线程退出");	

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			
		}
	}
}
