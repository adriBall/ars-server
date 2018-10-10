package parball.arsserver.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;

import parball.arsserver.common.EAnswer;
import parball.arsserver.common.Question;

public class QuestionTimerTask extends TimerTask {

	private Question question;
	private Lecturer lecturer;
	private Map<EAnswer, Set<String>> answersAndUsernames;

	public QuestionTimerTask(Question question, Lecturer lecturer) {
		this.question = question;
		this.lecturer = lecturer;
		this.answersAndUsernames = new HashMap<>();
		answersAndUsernames.put(EAnswer.A, Collections.synchronizedSet(new HashSet<>()));
		answersAndUsernames.put(EAnswer.B, Collections.synchronizedSet(new HashSet<>()));
		answersAndUsernames.put(EAnswer.C, Collections.synchronizedSet(new HashSet<>()));
		answersAndUsernames.put(EAnswer.D, Collections.synchronizedSet(new HashSet<>()));
	}

	@Override
	public void run() {
		lecturer.stopQuestion();
	}

	public void answer(String username, EAnswer answer) {
		if (!finished()) {
			if (getUsernameAnswer(username).equals(EAnswer.NONE))
				answersAndUsernames.get(answer).add(username);
		}
	}

	public Question getQuestion() {
		return question;
	}

	public int numAnswers(EAnswer answer) {
		return answersAndUsernames.get(answer).size();
	}

	public boolean finished() {
		return getRemainingTime() == 0;
	}

	public long getRemainingTime() {
		return Math.max(scheduledExecutionTime() - System.currentTimeMillis(), 0);
	}

	public EAnswer getUsernameAnswer(String username) {
		for (EAnswer answer : answersAndUsernames.keySet())
			if (answersAndUsernames.get(answer).contains(username))
				return answer;
		return EAnswer.NONE;
	}

}
