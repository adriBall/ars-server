package parball.arsserver.common;

public enum EAudienceServerActions {
	CHECK_REFERENCE, // Params: action, ref
	SET_USERNAME, // Params: action, username
	GET_REMAINING_TIME, // Params: action
	SEND_ANSWER, // Params: action, answer
	EXIT_LECTURE; // Params: action
}
