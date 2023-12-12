package eternityGems.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BottomOfDiscardPileToHandAction extends AbstractGameAction {
    @Override
    public void update() {
        if (!AbstractDungeon.player.discardPile.isEmpty())
        {
            CardGroup discardPile = AbstractDungeon.player.discardPile;
            AbstractCard targetCard = discardPile.group.get(0);

            addToTop(new FetchAction(
                    AbstractDungeon.player.discardPile,
                    c -> c.uuid == targetCard.uuid
                )
            );
        }

        this.isDone = true;
    }
}
