package eternityGems.actions;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import eternityGems.relics.RealityGem;

public class WarpRealityAction extends AbstractGameAction {
    private final boolean upgrade;
    private AbstractPlayer p;

    public WarpRealityAction(boolean upgrade)
    {
        this.upgrade = upgrade;
        this.p = AbstractDungeon.player;
        setValues(this.p, AbstractDungeon.player, this.amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.hand.size() == 10) {
                AbstractDungeon.player.createHandIsFullDialog();
                this.isDone = true;
                return;
            }

            if (AbstractDungeon.player.hasRelic(RealityGem.ID))
            {
                RealityGem relic = (RealityGem) AbstractDungeon.player.getRelic(RealityGem.ID);
                AbstractDungeon.gridSelectScreen.open(relic.warpRealityCards, 1, "Select a Card to add to Your Hand", false);
            }
            tickDuration();
            return;
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                c.setCostForTurn(0);
                CardModifierManager.addModifier(c, new ExhaustMod());
                this.p.hand.addToHand(c);
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.p.hand.refreshHandLayout();
        }
        tickDuration();
    }
}
