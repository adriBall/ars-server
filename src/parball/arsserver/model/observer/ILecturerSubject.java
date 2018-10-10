package parball.arsserver.model.observer;

import parball.arsserver.common.EAnswer;
import parball.arsserver.common.Question;

public interface ILecturerSubject extends ISubject {

	void notifyQuestionStarted(Question question, long remainingTimeMillis);

	void notifyQuestionFinished(EAnswer correctAnswer, int numAnswersA, int numAnswersB, int numAnswersC, int numAnswersD);

	void notifyGramification();

	void notifyLectureFinished();

}
