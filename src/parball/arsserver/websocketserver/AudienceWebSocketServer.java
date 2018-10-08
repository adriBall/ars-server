package parball.arsserver.websocketserver;

import javax.websocket.server.ServerEndpoint;

import parball.arsserver.sessionhandler.AudienceSessionHandler;
import parball.arsserver.sessionhandler.ISessionHandler;

@ServerEndpoint("/audience")
public class AudienceWebSocketServer extends WebsocketServer {

	@Override
	protected ISessionHandler createSessionHandler() {
		return new AudienceSessionHandler();
	}

}
