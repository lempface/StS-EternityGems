package eternityGems.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import java.util.function.Supplier;

public class WrapperAction extends AbstractGameAction
{
    private final Supplier<AbstractGameAction> action;
    private final QueueLocation queueLocation;

    public enum QueueLocation {
        BOTTOM,
        TOP
    }

    public WrapperAction(Supplier<AbstractGameAction> action, QueueLocation queueLocation) {
        this.action = action;
        this.queueLocation = queueLocation;
    }

    @Override
    public void update() {
        if (queueLocation == QueueLocation.TOP)
            addToTop(action.get());
        if (queueLocation == QueueLocation.BOTTOM)
            addToBot(action.get());

        this.isDone = true;
    }
}
