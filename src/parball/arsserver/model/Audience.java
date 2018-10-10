package parball.arsserver.model;

import parball.arsserver.common.EAnswer;
import parball.arsserver.common.ERefCheckedResolution;
import parball.arsserver.common.Question;
import parball.arsserver.model.clientnotifier.IAudienceClientNotifier;
import parball.arsserver.model.exception.AlreadyInLectureException;
import parball.arsserver.model.observer.ILecturerObserver;

public class Audience implements ILecturerObserver {

	private String username;
	private Lecturer lecturer;
	private IAudienceClientNotifier clientNotifier;

	public Audience() {
		super();
	}

	public void refChecked(ERefCheckedResolution resolution) {
		clientNotifier.refChecked(resolution);
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setClientNotifier(IAudienceClientNotifier clientNotifier) {
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

	public void answer(EAnswer answer) {
		lecturer.answerQuestion(this, answer);
	}

	public void requestForRemainingTime() {
		if (lecturer != null)
			lecturer.sendStatusTo(this);
	}

	public void sendQuestionStatus(long remainingTimeMillis, EAnswer currentAnswer) {
		clientNotifier.sendRemainingTime(remainingTimeMillis, currentAnswer);
	}

	@Override
	public void questionStarted(Question question, long remainingTimeMillis) {
		clientNotifier.questionStarted(remainingTimeMillis);
	}

	@Override
	public void questionFinished(EAnswer correctAnswer, int numAnswersA, int numAnswersB, int numAnswersC,
			int numAnswersD) {
		clientNotifier.questionFinished(correctAnswer);
	}

	@Override
	public void lectureFinished() {
		lecturer = null;
		clientNotifier.lectureFinished();
	}

	@Override
	public void sendGramification(String gramification, String splitRow, String splitColumn) {
		throw new UnsupportedOperationException();
	}

}
