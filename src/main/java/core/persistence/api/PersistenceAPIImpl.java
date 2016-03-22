package core.persistence.api;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import core.model.Message;
import core.persistence.driver.file.FileStore;
import core.persistence.driver.redis.RedisStore;
import core.util.ConfigRead;

public class PersistenceAPIImpl implements PersistenceAPI {
	
	public static Object getDriver() throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		if (ConfigRead.getConfigProperties("persistence_way").equals("file")) {//file
			Class driverClass = Class.forName("core.persistence.driver.file.FileStore");
			Object driver = driverClass.newInstance();
			return driver;
			
		} else {//redis
			Class driverClass = Class.forName("core.persistence.driver.file.RedisStore");
			Object driver = driverClass.newInstance();
			return driver;
		}
		
	}

	@Override
	public void saveMessage(Message message) {
		//object driver = getDriver()
		if (ConfigRead.getConfigProperties("persistence_way").equals("file")) {//file
			new FileStore().saveMessage(message);		
		}else{
			new RedisStore().saveMessage(message);
		}
	}

	@Override
	public void deleteMessage(int userNum) {
		//需要删除用户消息和群消息，群消息复杂一点，需要判断是否已经发送完毕
		//object driver = getDriver()
		if (ConfigRead.getConfigProperties("persistence_way").equals("file")) {//file
			new FileStore().deleteMessage(userNum);	
		}else{
			new RedisStore().deleteMessage(userNum);
		}
		
	}

	@Override
	public List<Message> getMessage(int userNum) {
		// TODO Auto-generated method stub
		if (ConfigRead.getConfigProperties("persistence_way").equals("file")) {//file
			return new FileStore().getMessage(userNum);	
		}else{
			return new RedisStore().getMessage(userNum);
		}
	}

}
