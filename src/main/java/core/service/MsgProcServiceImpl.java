package core.service;

import java.rmi.registry.Registry;

import core.model.RequestDetail;
import core.model.ResponseDetail;

public class MsgProcServiceImpl implements MsgProcService {

	@Override
	public String procMessage(String msg) {
		MsgParseService msgParse = new MsgParseServiceImpl();
		RequestDetail requestDetail = msgParse.parseMessage(msg);
		return "";
	}
	
	public ResponseDetail Registry(RequestDetail requestDetail){
		RequestDetail.RegistryRequest registryRequest = requestDetail.getRegistryRequest();
		
		return null;
				
	}


}
