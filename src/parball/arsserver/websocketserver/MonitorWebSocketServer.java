package parball.arsserver.websocketserver;

import javax.websocket.server.ServerEndpoint;

import parball.arsserver.sessionhandler.ISessionHandler;
import parball.arsserver.sessionhandler.MonitorSessionHandler;

@ServerEndpoint("/monitor")
public class MonitorWebSocketServer extends WebsocketServer {

	@Override
	protected ISessionHandler createSessionHandler() {
		return new MonitorSessionHandler();
	}

}
