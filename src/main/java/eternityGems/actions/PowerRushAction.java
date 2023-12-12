package eternityGems.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PowerRushAction extends AbstractGameAction {
    private AbstractPlayer p;
    public PowerRushAction()
    {
        this.p = AbstractDungeon.player;
        setValues((AbstractCreature)this.p, (AbstractCreature)AbstractDungeon.player, this.amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        CardGroup powers = p.discardPile.getPowers();

        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.hand.size() == 10) {
                AbstractDungeon.player.createHandIsFullDialog();
                this.isDone = true;
                return;
            }
            if (powers.isEmpty()) {
                this.isDone = true;
                return;
            }
            if (powers.size() == 1) {
                AbstractCard c = powers.getTopCard();
                addToBot(new MoveCardsAction(p.hand, p.discardPile, x -> x.uuid == c.uuid));
                addToTop(new GainEnergyAction(1));
                this.isDone = true;
                return;
            }
            AbstractDungeon.gridSelectScreen.open(powers, 1, "Select a Card to Return to Your Hand", false);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            addToBot(new MoveCardsAction(p.hand, p.discardPile, x -> x.uuid == c.uuid));
            addToTop(new GainEnergyAction(1));
            this.isDone = true;
        }
        tickDuration();
    }
}
