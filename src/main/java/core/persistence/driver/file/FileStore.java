package core.persistence.driver.file;

import java.util.ArrayList;
import java.util.List;

import core.model.Message;

public class FileStore {
	
	public void saveMessage(Message message){
		// TODO
		System.out.println("保存消息到文件中，个人消息和群消息分开两个文件保存");
		
	}
	public void deleteMessage(int userNum){
		// TODO
		System.out.println("删除了file中该用户之前的消息，包括个人消息和群消息");
	}
	
	public List<Message> getMessage(int userNum){
		// TODO
		System.out.println("从file中获取该用户的离线消息，包括个人消息和群消息");
		return new ArrayList<Message>();
	}

}
