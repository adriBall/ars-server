package parball.arsserver.sessionhandler;

import java.io.IOException;
import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.Session;

import parball.arsserver.common.EAnswer;
import parball.arsserver.common.EAudienceServerActions;
import parball.arsserver.common.ERefCheckedResolution;
import parball.arsserver.model.Audience;
import parball.arsserver.model.Lecturer;
import parball.arsserver.websocketclientnotifier.AudienceWebsocketClientNotifier;

public class AudienceSessionHandler implements ISessionHandler {

	private Audience audience;

	public void handleMessage(String message, Session session) throws IOException {
		JsonReader reader = Json.createReader(new StringReader(message));
		JsonObject jsonMessage = reader.readObject();
		String action = jsonMessage.getString("action");

		if (EAudienceServerActions.CHECK_REFERENCE.toString().equals(action)) {
			String ref = jsonMessage.getString("ref");
			Lecturer lecturer = AvailableLecturersMapSingleton.getInstance().get(ref);
			if (lecturer == null)
				audience.refChecked(ERefCheckedResolution.FAIL);
			else {
				audience.enterLecture(lecturer);
				audience.refChecked(ERefCheckedResolution.SUCCESS);
			}

		} else if (EAudienceServerActions.SET_USERNAME.toString().equals(action)) {
			String username = jsonMessage.getString("username");
			audience.setUsername(username);
		} else if (EAudienceServerActions.EXIT_LECTURE.toString().equals(action))
			audience.exitLecture();
		else if (EAudienceServerActions.SEND_ANSWER.toString().equals(action)) {
			EAnswer answer = EAnswer.valueOf(jsonMessage.getString("answer"));
			audience.answer(answer);
		} else if (EAudienceServerActions.GET_REMAINING_TIME.toString().equals(action))
			audience.requestForRemainingTime();

	}

	@Override
	public void openSession(Session session) {
		audience = new Audience();
		audience.setClientNotifier(new AudienceWebsocketClientNotifier(session));
	}

	@Override
	public void closeSession(Session session) {
		audience.exitLecture();
	}

}
