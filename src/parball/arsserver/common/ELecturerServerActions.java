package parball.arsserver.common;

public enum ELecturerServerActions {
	GET_REF, // Params: action
	SEND_QUESTION, // Params: action, question, answerA, answerB, answerC, answerD, correctAnswer(A,B,C,D), timeOutSec
	STOP_QUESTION; // Params: action
}
