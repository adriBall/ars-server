package parball.arsserver.common;

public enum EAudienceClientActions {
	REF_CHECKED, // Params: action, resolution
	SEND_REMAINING_TIME, // Params: action, remainingTimeMillis, currentAnswer (NONE if not answered yet)
	QUESTION_FINISHED, // Params: action, correctAnswer
	QUESTION_STARTED, // Params: action, remainingTimeMillis
	LECTURE_FINISHED; // Params: action
}
