package lania.com.mx.countdownview;

/**
 * @author Clemente Morales Fernandez
 * @since 1/23/2017.
 */

public class Milestone {
    private final MilestoneListener listener;
    private final MilestoneEvaluator evaluator;

    public Milestone(MilestoneListener listener, MilestoneEvaluator evaluator) {
        this.listener = listener;
        this.evaluator = evaluator;
    }

    public void onComplete() {
        listener.onComplete();
    }

    public boolean isCompleted(long remainingTime) {
        return evaluator.isCompleted(remainingTime);
    }

    public MilestoneListener getListener() {
        return listener;
    }

    public MilestoneEvaluator getEvaluator() {
        return evaluator;
    }
}
