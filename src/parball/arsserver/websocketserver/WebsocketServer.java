package parball.arsserver.websocketserver;

import java.io.IOException;
import java.util.logging.Logger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import parball.arsserver.sessionhandler.ISessionHandler;

public abstract class WebsocketServer {

	private ISessionHandler sessionHandler;

	{
		sessionHandler = createSessionHandler();
	}

	protected abstract ISessionHandler createSessionHandler();

	@OnOpen
	public void open(Session session) {
		sessionHandler.openSession(session);
	}

	@OnClose
	public void close(Session session) {
		sessionHandler.closeSession(session);
	}

	@OnError
	public void error(Throwable error) {
		Logger.getLogger(WebsocketServer.class.getName()).info(error.getMessage());
	}

	@OnMessage
	public void handleMessage(String message, Session session) throws IOException {
		sessionHandler.handleMessage(message, session);
	}

}
