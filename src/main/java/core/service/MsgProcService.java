package core.service;

import java.io.IOException;
import java.sql.SQLException;

import core.model.ClientSocket;
import core.model.RequestDetail;
import core.model.ResponseDetail;

public interface MsgProcService {
	
	public void procMessage(ClientSocket clientSocket, String msg) throws SQLException, IOException;

}
