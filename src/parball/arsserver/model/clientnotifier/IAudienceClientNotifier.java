package parball.arsserver.model.clientnotifier;

import parball.arsserver.common.EAnswer;
import parball.arsserver.common.ERefCheckedResolution;

public interface IAudienceClientNotifier {

	void refChecked(ERefCheckedResolution resolution);

	void sendRemainingTime(long remainingTimeMillis, EAnswer currentAnswer);

	void questionFinished(EAnswer correctAnswer);

	void questionStarted(long remainingTimeMillis);

	void lectureFinished();

}
