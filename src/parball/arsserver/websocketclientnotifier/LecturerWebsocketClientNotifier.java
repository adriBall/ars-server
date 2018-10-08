package parball.arsserver.websocketclientnotifier;

import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;

import parball.arsserver.common.ELecturerClientActions;
import parball.arsserver.model.clientnotifier.ILecturerClientNotifier;

public class LecturerWebsocketClientNotifier extends WebsocketClientNotifier implements ILecturerClientNotifier {

	public LecturerWebsocketClientNotifier(Session session) {
		super(session);
	}
	
	@Override
	public void sendRef(String ref) {
		JsonProvider provider = JsonProvider.provider();
        JsonObject message = provider.createObjectBuilder()
                .add("action", ELecturerClientActions.SEND_REF.toString())
                .add("ref", ref)
                .build();
        send(message);
	}

	@Override
	public void questionFinished() {
		JsonProvider provider = JsonProvider.provider();
        JsonObject message = provider.createObjectBuilder()
                .add("action", ELecturerClientActions.QUESTION_FINISHED.toString())
                .build();
        send(message);
		
	}

}
