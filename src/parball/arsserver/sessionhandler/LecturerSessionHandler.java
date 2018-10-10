package parball.arsserver.sessionhandler;

import java.io.IOException;
import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.Session;

import parball.arsserver.common.EAnswer;
import parball.arsserver.common.ELecturerServerActions;
import parball.arsserver.common.Question;
import parball.arsserver.model.Lecturer;
import parball.arsserver.model.util.RefGenerator;
import parball.arsserver.websocketclientnotifier.LecturerWebsocketClientNotifier;

public class LecturerSessionHandler implements ISessionHandler {

	private Lecturer lecturer;

	@Override
	public void handleMessage(String message, Session session) throws IOException {
		JsonReader reader = Json.createReader(new StringReader(message));
		JsonObject jsonMessage = reader.readObject();
		String action = jsonMessage.getString("action");

		if (ELecturerServerActions.GET_REF.toString().equals(action))
			lecturer.sendRef();
		else if (ELecturerServerActions.SEND_QUESTION.toString().equals(action)) {
			String question = jsonMessage.getString("question");
			String answerA = jsonMessage.getString("answerA");
			String answerB = jsonMessage.getString("answerB");
			String answerC = jsonMessage.getString("answerC");
			String answerD = jsonMessage.getString("answerD");
			EAnswer correctAnswer = EAnswer.valueOf(jsonMessage.getString("correctAnswer"));
			int timeOutSec = Integer.parseInt(jsonMessage.getString("timeOutSec"));
			Question q = new Question();
			q.setQuestion(question);
			q.setAnswerA(answerA);
			q.setAnswerB(answerB);
			q.setAnswerC(answerC);
			q.setAnswerD(answerD);
			q.setCorrectAnswer(correctAnswer);
			lecturer.sendQuestion(q, timeOutSec);
		} else if (ELecturerServerActions.STOP_QUESTION.toString().equals(action))
			lecturer.stopQuestion();
	}

	@Override
	public void openSession(Session session) {
		Lecturer oldValue;
		do {
			String ref = RefGenerator.generateRef(4);
			lecturer = new Lecturer(ref);
			oldValue = AvailableLecturersMapSingleton.getInstance().putIfAbsent(ref, lecturer);
		} while (oldValue != null);
		lecturer.setClientNotifier(new LecturerWebsocketClientNotifier(session));
		lecturer.sendRef();
	}

	@Override
	public void closeSession(Session session) {
		lecturer.lectureFinished();
		AvailableLecturersMapSingleton.getInstance().remove(lecturer.getRef());
	}

}
