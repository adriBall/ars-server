package parball.arsserver.websocketserver;

import javax.websocket.server.ServerEndpoint;

import parball.arsserver.sessionhandler.ISessionHandler;
import parball.arsserver.sessionhandler.LecturerSessionHandler;

@ServerEndpoint("/lecturer")
public class LecturerWebSocketServer extends WebsocketServer {

	@Override
	protected ISessionHandler createSessionHandler() {
		return new LecturerSessionHandler();
	}

}
