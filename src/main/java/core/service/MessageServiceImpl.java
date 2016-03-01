package core.service;

import org.junit.runner.Request;

import core.model.RequestDetail;
import core.util.MessageConstant;

public class MessageServiceImpl implements MessageService {


	public void parseMessage(String message) {
		String[] messStrings = message.split("+");
		RequestDetail req = new RequestDetail();
		switch (Integer.valueOf(messStrings[0]).intValue()) {
		case MessageConstant.REGISTRY:			
			break;
		case MessageConstant.ADD_FRIEND:

			break;
		case MessageConstant.CREATE_GROUP:

			break;
		case MessageConstant.ADD_GROUP:

			break;
		case MessageConstant.SEND_MESSAGE:

			break;

		default:
			break;
		}
		


	}
	
	public String userRegistry(){
		
	}

}
