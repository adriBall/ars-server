package parball.arsserver.model;

import parball.arsserver.common.EAnswer;
import parball.arsserver.common.ERefCheckedResolution;
import parball.arsserver.common.Question;
import parball.arsserver.model.clientnotifier.IMonitorClientNotifier;
import parball.arsserver.model.exception.AlreadyInLectureException;
import parball.arsserver.model.observer.ILecturerObserver;

public class Monitor implements ILecturerObserver {

	Lecturer lecturer;
	private IMonitorClientNotifier clientNotifier;

	public Monitor() {
		super();
	}

	public void setClientNotifier(IMonitorClientNotifier clientNotifier) {
		this.clientNotifier = clientNotifier;
	}

	public void enterLecture(Lecturer lecturer) {
		if (this.lecturer != null)
			throw new AlreadyInLectureException();
		lecturer.registerObserver(this);
		this.lecturer = lecturer;
	}

	public void exitLecture() {
		if (lecturer != null) {
			lecturer.removeObserver(this);
			lecturer = null;
		}
	}

	public void refChecked(ERefCheckedResolution resolution) {
		clientNotifier.refChecked(resolution);
	}

	public void requestForCurrentQuestion() {
		if (lecturer != null)
			lecturer.sendQuestionToMonitor(this);
	}

	public void sendCurrentQuestion(Question question, long remainingTimeMillis) {
		clientNotifier.sendQuestion(question.getQuestion(), question.getAnswerA(), question.getAnswerB(),
				question.getAnswerC(), question.getAnswerD(), remainingTimeMillis);
	}

	@Override
	public void questionStarted(Question question, long remainingTimeMillis) {
		clientNotifier.sendQuestion(question.getQuestion(), question.getAnswerA(), question.getAnswerB(),
				question.getAnswerC(), question.getAnswerD(), remainingTimeMillis);
	}

	@Override
	public void questionFinished(EAnswer correctAnswer, int numAnswersA, int numAnswersB, int numAnswersC,
			int numAnswersD) {
		clientNotifier.questionFinished(correctAnswer, numAnswersA, numAnswersB, numAnswersC, numAnswersD);
	}

	@Override
	public void lectureFinished() {
		lecturer = null;
		clientNotifier.lectureFinished();
	}

	@Override
	public void sendGramification(String gramification, String splitRow, String splitColumn) {
		clientNotifier.sendGramification(gramification, splitRow, splitColumn);
	}

}
