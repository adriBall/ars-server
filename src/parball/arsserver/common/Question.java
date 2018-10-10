package parball.arsserver.common;

public class Question {
	private String question;
	private String answerA;
	private String answerB;
	private String answerC;
	private String answerD;
	private EAnswer correctAnswer;

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getQuestion() {
		return question;
	}

	public void setAnswerA(String answerA) {
		this.answerA = answerA;
	}

	public String getAnswerA() {
		return answerA;
	}

	public void setAnswerB(String answerB) {
		this.answerB = answerB;
	}

	public String getAnswerB() {
		return answerB;
	}

	public void setAnswerC(String answerC) {
		this.answerC = answerC;
	}

	public String getAnswerC() {
		return answerC;
	}

	public void setAnswerD(String answerD) {
		this.answerD = answerD;
	}

	public String getAnswerD() {
		return answerD;
	}

	public void setCorrectAnswer(EAnswer correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public EAnswer getCorrectAnswer() {
		return correctAnswer;
	}

}
