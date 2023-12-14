package eternityGems.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SetCardCostForCombatAction extends AbstractGameAction {

    AbstractCard card;
    private final int cost;

    public SetCardCostForCombatAction(AbstractCard card, int cost)
    {
        this.card = card;
        this.cost = cost;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST)
        {
            if (card.cost > cost)
            {
                card.costForTurn = cost;
                card.isCostModifiedForTurn = true;
                card.cost = cost;
                card.isCostModified = true;
            }
        }
        tickDuration();
    }
}
