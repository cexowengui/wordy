package core.persistence.driver.redis;

import java.util.ArrayList;
import java.util.List;

import core.model.Message;

public class RedisStore {
	
	public void saveMessage(Message message){
		// TODO
		System.out.println("保存消息到redis中，个人消息和群消息分开两个hash中");
	}
	public void deleteMessage(int userNum){
		// TODO
		System.out.println("删除了redis中该用户之前的消息，包括个人消息和群消息");
	}
	public List<Message> getMessage(int userNum){
		// TODO
		System.out.println("从redis中获取该用户的离线消息，包括个人消息和群消息");
		return new ArrayList<Message>();
	}

}
