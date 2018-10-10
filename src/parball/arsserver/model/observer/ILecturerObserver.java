package parball.arsserver.model.observer;

import parball.arsserver.common.EAnswer;
import parball.arsserver.common.Question;

public interface ILecturerObserver extends IObserver {

	void questionStarted(Question question, long remainingTimeMillis);

	void questionFinished(EAnswer correctAnswer, int numAnswersA, int numAnswersB, int numAnswersC, int numAnswersD);

	void sendGramification(String gramification, String splitRow, String splitColumn);

	void lectureFinished();

}
