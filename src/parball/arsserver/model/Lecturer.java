package parball.arsserver.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;

import parball.arsserver.common.EAnswer;
import parball.arsserver.common.Question;
import parball.arsserver.model.clientnotifier.ILecturerClientNotifier;
import parball.arsserver.model.observer.ILecturerObserver;
import parball.arsserver.model.observer.ILecturerSubject;
import parball.arsserver.model.observer.IObserver;

public class Lecturer implements ILecturerSubject {

	private String ref;
	private Gamification gamification;
	private Timer timer;
	private QuestionTimerTask questionTask;
	private Set<IObserver> observers;
	private ILecturerClientNotifier clientNotifier;

	public Lecturer(String ref) {
		super();
		this.ref = ref;
		this.observers = Collections.synchronizedSet(new HashSet<>());
		this.timer = new Timer();
		gamification = new Gamification();
	}

	public void setClientNotifier(ILecturerClientNotifier clientNotifier) {
		this.clientNotifier = clientNotifier;
	}

	public String getRef() {
		return ref;
	}

	public void lectureFinished() {
		timer.cancel();
		notifyLectureFinished();
		observers.clear();
	}

	public void sendRef() {
		clientNotifier.sendRef(ref);
	}

	public void stopQuestion() {
		if (questionTask != null) {
			questionTask.cancel();
			timer.purge();
			Question question = questionTask.getQuestion();
			clientNotifier.questionFinished();
			notifyQuestionFinished(question.getCorrectAnswer(), questionTask.numAnswers(EAnswer.A),
					questionTask.numAnswers(EAnswer.B), questionTask.numAnswers(EAnswer.C),
					questionTask.numAnswers(EAnswer.D));
			notifyGramification();
			questionTask = null;
		}
	}

	public void sendQuestion(Question q, int timeOutSec) {
		if (questionTask == null || questionTask.finished()) {
			questionTask = new QuestionTimerTask(q, this);
			timer.schedule(questionTask, timeOutSec * 1000L);
			notifyQuestionStarted(q, timeOutSec * 1000L);
		}
	}

	public void sendStatusTo(Audience audience) {
		if (questionTask != null && !questionTask.finished())
			audience.sendQuestionStatus(questionTask.getRemainingTime(),
					questionTask.getUsernameAnswer(audience.getUsername()));
		else
			audience.sendQuestionStatus(0L, EAnswer.NONE);
	}

	public void sendQuestionToMonitor(Monitor monitor) {
		if (questionTask != null && !questionTask.finished())
			monitor.sendCurrentQuestion(questionTask.getQuestion(), questionTask.getRemainingTime());
	}

	public void answerQuestion(Audience audience, EAnswer answer) {
		if (questionTask != null && !questionTask.finished()) {
			questionTask.answer(audience.getUsername(), answer);
			gamification.addResult(audience, questionTask.getQuestion().getCorrectAnswer().equals(answer));
		}
	}

	@Override
	public void registerObserver(IObserver o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(IObserver o) {
		observers.remove(o);
	}

	@Override
	public void notifyQuestionStarted(Question question, long remainingTimeMillis) {
		for (IObserver observer : observers)
			if (observer instanceof ILecturerObserver)
				((ILecturerObserver) observer).questionStarted(question, remainingTimeMillis);
	}

	@Override
	public void notifyQuestionFinished(EAnswer correctAnswer, int numAnswersA, int numAnswersB, int numAnswersC,
			int numAnswersD) {
		for (IObserver observer : observers)
			if (observer instanceof ILecturerObserver)
				((ILecturerObserver) observer).questionFinished(correctAnswer, numAnswersA, numAnswersB, numAnswersC,
						numAnswersD);
	}

	@Override
	public void notifyLectureFinished() {
		for (IObserver observer : observers)
			if (observer instanceof ILecturerObserver)
				((ILecturerObserver) observer).lectureFinished();

	}

	@Override
	@SuppressWarnings("static-access")
	public void notifyGramification() {
		for (IObserver observer : observers)
			if (observer instanceof Monitor)
				((ILecturerObserver) observer).sendGramification(gamification.toString(), Gamification.SPLIT_ROW,
						gamification.SPLIT_COLUMN);

	}

}
