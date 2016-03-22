package core.socket;

public class CleanOverdueMessageThread implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//这里面对保存在服务器上的离线消息进行清理，比如默认7天还没有被接收的消息就全部删除掉
		//包括file和redis两种持久化的消息都要清理
		
	}

}
