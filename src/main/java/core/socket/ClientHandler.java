package core.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import core.model.ClientSocket;
import core.model.RequestDetail;
import core.service.MsgParseService;
import core.service.MsgProcService;
import core.service.MsgProcServiceImpl;
import core.service.SocketMap;
import core.service.socket.MsgParseServiceSocketImpl;

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
				
				new MessageProcThread(clientSocket, msg).start();
				
			}
		} catch (Exception e) {
			//System.out.println(e.toString());
		} finally {
			System.out.println("客户端断开，对应的客户端线程退出");
			try {
				/*
				 * 如果代码能够走到这里，说明这个客户端关闭了或者出现其他异常导致连接断开了，
				 * 这里就应该把SocketMap这个全局变量的socket删除掉，但是无奈的是这里无法获取到这个socket，
				 * 因为我们把接收到的消息处理在MessageProcThread线程里面处理了，线程是无法返回消息处理结果的，
				 * SocketMap中socket是key-value键值对存储的，这里无法获取到这个socket的key，也就是QQ号，所以
				 * 只能依赖于CleanInactivateClientThread线程去清理，但是这样就有延时了。
				 * 可以想办法改进一下，在这里就直接把SocketMap里面属于这个客户端的socket删除掉，这样就最好了。
				 * 比如：SocketMap.userNumMap.remove(userNum);想办法获取到这个连接的userNum
				 * 
				 * */				
				this.clientSocket.getInput().close();
				this.clientSocket.getOutput().close();
				this.clientSocket.getSocket().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

class MessageProcThread extends Thread {
	private ClientSocket clientSocket;
	private String message;

	public MessageProcThread(ClientSocket client, String message) {
		this.clientSocket = client;		
		this.message = message;
	}

	public void run() {		
		try {
			String clientName = this.clientSocket.getSocket().getInetAddress().toString() + 
					this.clientSocket.getSocket().getPort();
			
			MsgParseService msgParse = new MsgParseServiceSocketImpl();
			RequestDetail requestDetail = msgParse.parseMessage(this.message);
			
			MsgProcService msgProcService = new MsgProcServiceImpl();
			msgProcService.procMessage(this.clientSocket, requestDetail);
			System.out.println("服务器已经完成客户端请求处理并回复消息给客户端，该处理线程退出");

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			
		}
	}
}
