package core.service.http;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import core.model.ClientSocket;
import core.model.RequestDetail;
import core.model.ResponseDetail;

public interface MsgProcHttpService {
	
	public void procMessage(HttpServletResponse response, String msg) throws SQLException, IOException;

}
