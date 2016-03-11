package core.main;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import core.model.ClientSocket;
import core.service.SocketMap;
import core.util.ConfigRead;

public class CleanInactivateClientThread implements Runnable {
	public void run() {
		Lock lock = new ReentrantLock();
		while (true) {
			try {
				//该定时任务默认5秒运行一次
				Thread.sleep(Integer.parseInt(ConfigRead.getConfigProperties("clean_thread_run_interval")));				
				//可以使用下面的方式遍历，也可以使用迭代器，java集合的迭代器要熟悉
				for (Map.Entry<Integer, ClientSocket> e : SocketMap.userNumMap.entrySet()) {			
					//当前时间大于配置项规定的客户端最大心跳间隔时间，就认为客户端下线了
					if(System.currentTimeMillis() - e.getValue().getUpdateTime() > 
					1000 * Integer.parseInt(ConfigRead.getConfigProperties("max_client_heartbeat_interval"))){
						SocketMap.userNumMap.remove(e.getKey());
					}
				}
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}finally {
				lock.unlock();
			}
			
		}
		

	}
}
