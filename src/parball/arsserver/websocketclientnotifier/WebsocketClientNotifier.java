package parball.arsserver.websocketclientnotifier;

import java.io.IOException;
import java.util.logging.Logger;

import javax.json.JsonObject;
import javax.websocket.Session;

public abstract class WebsocketClientNotifier {
	protected Session session;
	
	public WebsocketClientNotifier(Session session) {
		this.session = session;
	}
	
	protected void send(JsonObject message) {
    	try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException e) {
            Logger.getLogger(WebsocketClientNotifier.class.getName()).info(e.getMessage());
        }
    }

}
