package parball.arsserver.sessionhandler;

import java.io.IOException;
import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.Session;

import parball.arsserver.common.EMonitorServerActions;
import parball.arsserver.common.ERefCheckedResolution;
import parball.arsserver.model.Lecturer;
import parball.arsserver.model.Monitor;
import parball.arsserver.websocketclientnotifier.MonitorWebsocketClientNotifier;

public class MonitorSessionHandler implements ISessionHandler {

	private Monitor monitor;

	@Override
	public void handleMessage(String message, Session session) throws IOException {
		JsonReader reader = Json.createReader(new StringReader(message));
		JsonObject jsonMessage = reader.readObject();
		String action = jsonMessage.getString("action");

		if (EMonitorServerActions.CHECK_REFERENCE.toString().equals(action)) {
			String ref = jsonMessage.getString("ref");
			Lecturer lecturer = AvailableLecturersMapSingleton.getInstance().get(ref);
			if (lecturer == null)
				monitor.refChecked(ERefCheckedResolution.FAIL);
			else {
				monitor.enterLecture(lecturer);
				monitor.refChecked(ERefCheckedResolution.SUCCESS);
			}
		} else if (EMonitorServerActions.GET_CURRENT_QUESTION.toString().equals(action))
			monitor.requestForCurrentQuestion();
		else if (EMonitorServerActions.EXIT_LECTURE.toString().equals(action))
			monitor.exitLecture();
	}

	@Override
	public void openSession(Session session) {
		monitor = new Monitor();
		monitor.setClientNotifier(new MonitorWebsocketClientNotifier(session));
	}

	@Override
	public void closeSession(Session session) {
		monitor.exitLecture();
	}

}
