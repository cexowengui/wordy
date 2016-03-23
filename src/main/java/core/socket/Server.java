package core.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import core.model.ClientSocket;
import core.util.ConfigRead;


/*
 * 2016-03-02 16:45
 * 目前还是有点问题，server.java启动，然后启动这个包里面的SocketClient.java，偶尔会出现
 * 客户端的console无法输入第二行数据，关闭重启就会好，暂时不知道什么问题
 */

public class Server {	

	public static void main(String[] args) {
		
		new Server().OpenServer();
		
		/*启动定时任务线程清理断开的客户端连接，虽然客户端退出会在ClientHandler的finally里面把socket关闭，但是全局变量
		 * SocketMap里面还保留了client的socket，必须清理掉对于三分钟以上没有心跳更新的socket都清除掉
		*/
		new Thread( new CleanInactivateClientThread()).start();
		
		/*
		 * 启动过期消息处理线程
		 */
		new Thread(new CleanOverdueMessageThread()).start();
	}
	

	public void OpenServer() {
		try {
			/*掌握四种线程池的用法和原理
			 * newCachedThreadPool：线程池为无限大，当执行第二个任务时第一个任务已经完成，会复用执行第一个任务的线程，而不用每次新建线程。
			 * newFixedThreadPool：创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
			 * newScheduledThreadPool：创建一个定长线程池，支持定时及周期性任务执行。
			 * newSingleThreadExecutor：创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
			 * 
			 */
			ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
			ServerSocket server = new ServerSocket(Integer.valueOf(ConfigRead.getConfigProperties("server_port")).intValue());
			Socket socket;
			while ((socket = server.accept()) != null) {
				System.out.println("接收到客户端连接");
				ClientSocket clientSocket = new ClientSocket();
				clientSocket.setSocket(socket);
				clientSocket.setInput(new DataInputStream(socket.getInputStream()));
				clientSocket.setOutput(new DataOutputStream(socket.getOutputStream()));
				clientSocket.setUpdateTime(System.currentTimeMillis());
				
				//来一个客户端，就开启一个处理线程，比如apache就是这样，注释掉下面这行，采用线程池来处理，比如nginx
				//new ClientHandler(clientSocket).start();
				cachedThreadPool.execute(new ClientHandler(clientSocket));
				
			}
			
			System.out.println("服务端退出");
			if (!socket.isClosed()) {
				socket.close();
			}
			if (!server.isClosed()) {
				server.close();
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {

		}
	}

	

}
