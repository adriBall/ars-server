package parball.arsserver.sessionhandler;

import java.io.IOException;

import javax.websocket.Session;

public interface ISessionHandler {

	void handleMessage(String message, Session session) throws IOException;

	void openSession(Session session);

	void closeSession(Session session);

}
