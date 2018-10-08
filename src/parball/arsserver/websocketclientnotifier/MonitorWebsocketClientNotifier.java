package parball.arsserver.websocketclientnotifier;

import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;

import parball.arsserver.common.EAnswer;
import parball.arsserver.common.EMonitorClientActions;
import parball.arsserver.common.ERefCheckedResolution;
import parball.arsserver.model.clientnotifier.IMonitorClientNotifier;

public class MonitorWebsocketClientNotifier extends WebsocketClientNotifier implements IMonitorClientNotifier {

	public MonitorWebsocketClientNotifier(Session session) {
		super(session);
	}
	
	@Override
	public void refChecked(ERefCheckedResolution resolution) {
		JsonProvider provider = JsonProvider.provider();
        JsonObject message = provider.createObjectBuilder()
                .add("action", EMonitorClientActions.REF_CHECKED.toString())
                .add("resolution", resolution.toString())
                .build();
        send(message);
	}

	@Override
	public void sendQuestion(String question, String answerA, String answerB, String answerC,
			String answerD, long remainingTimeMillis) {
		JsonProvider provider = JsonProvider.provider();
        JsonObject message = provider.createObjectBuilder()
                .add("action", EMonitorClientActions.SEND_QUESTION.toString())
                .add("question", question)
                .add("answerA", answerA)
                .add("answerB", answerB)
                .add("answerC", answerC)
                .add("answerD", answerD)
                .add("remainingTimeMillis", ""+remainingTimeMillis)
                .build();
        send(message);
	}

	@Override
	public void sendGramification(String gamification, String splitRow, String splitColumn) {
		JsonProvider provider = JsonProvider.provider();
		JsonObject message = provider.createObjectBuilder()
                .add("action", EMonitorClientActions.SEND_GAMIFICATION.toString())
                .add("gamification", gamification)
                .add("splitRow", splitRow)
                .add("splitColumn", splitColumn)
                .build();
        send(message);
	}

	@Override
	public void questionFinished(EAnswer correctAnswer, int numAnswersA, int numAnswersB,
			int numAnswersC, int numAnswersD) {
		JsonProvider provider = JsonProvider.provider();
        JsonObject message = provider.createObjectBuilder()
                .add("action", EMonitorClientActions.QUESTION_FINISHED.toString())
                .add("correctAnswer", correctAnswer.toString())
                .add("numAnswersA", ""+numAnswersA)
                .add("numAnswersB", ""+numAnswersB)
                .add("numAnswersC", ""+numAnswersC)
                .add("numAnswersD", ""+numAnswersD)
                .build();
        send(message);
	}

	@Override
	public void lectureFinished() {
		JsonProvider provider = JsonProvider.provider();
        JsonObject message = provider.createObjectBuilder()
                .add("action", EMonitorClientActions.LECTURE_FINISHED.toString())
                .build();
        send(message);
	}

}
