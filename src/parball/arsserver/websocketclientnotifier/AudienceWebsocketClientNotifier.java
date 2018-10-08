package parball.arsserver.websocketclientnotifier;

import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;

import parball.arsserver.common.EAnswer;
import parball.arsserver.common.EAudienceClientActions;
import parball.arsserver.common.ERefCheckedResolution;
import parball.arsserver.model.clientnotifier.IAudienceClientNotifier;

public class AudienceWebsocketClientNotifier extends WebsocketClientNotifier implements IAudienceClientNotifier {

	public AudienceWebsocketClientNotifier(Session session) {
		super(session);
	}
	
	@Override
	public void refChecked(ERefCheckedResolution resolution) {
		JsonProvider provider = JsonProvider.provider();
        JsonObject message = provider.createObjectBuilder()
                .add("action", EAudienceClientActions.REF_CHECKED.toString())
                .add("resolution", resolution.toString())
                .build();
        send(message);
	}
	
	@Override
	public void sendRemainingTime(long remainingTimeMillis, EAnswer currentAnswer) {
		JsonProvider provider = JsonProvider.provider();
        JsonObject message = provider.createObjectBuilder()
                .add("action", EAudienceClientActions.SEND_REMAINING_TIME.toString())
                .add("remainingTimeMillis", ""+remainingTimeMillis)
                .add("currentAnswer", currentAnswer.toString())
                .build();
        send(message);
	}
	
	@Override
	public void questionFinished(EAnswer correctAnswer) {
		JsonProvider provider = JsonProvider.provider();
        JsonObject message = provider.createObjectBuilder()
                .add("action", EAudienceClientActions.QUESTION_FINISHED.toString())
                .add("correctAnswer", correctAnswer.toString())
                .build();
        send(message);
	}
	
	@Override
	public void questionStarted(long remainingTimeMillis) {
		JsonProvider provider = JsonProvider.provider();
        JsonObject message = provider.createObjectBuilder()
                .add("action", EAudienceClientActions.QUESTION_STARTED.toString())
                .add("remainingTimeMillis", ""+remainingTimeMillis)
                .build();
        send(message);
	}
	
	@Override
	public void lectureFinished() {
		JsonProvider provider = JsonProvider.provider();
        JsonObject message = provider.createObjectBuilder()
                .add("action", EAudienceClientActions.LECTURE_FINISHED.toString())
                .build();
        send(message);
	}

}
