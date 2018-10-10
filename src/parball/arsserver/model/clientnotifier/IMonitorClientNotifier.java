package parball.arsserver.model.clientnotifier;

import parball.arsserver.common.EAnswer;
import parball.arsserver.common.ERefCheckedResolution;

public interface IMonitorClientNotifier {

	void refChecked(ERefCheckedResolution resolution);

	void sendQuestion(String question, String answerA, String answerB, String answerC, String answerD, long remainingTimeMillis);

	void questionFinished(EAnswer correctAnswer, int numAnswersA, int numAnswersB, int numAnswersC, int numAnswersD);

	public void sendGramification(String gamification, String splitRow, String splitColumn);

	void lectureFinished();

}
