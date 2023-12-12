package eternityGems.cards.colorless;

import basemod.BaseMod;
import basemod.cardmods.ExhaustMod;
import basemod.cardmods.RetainMod;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import eternityGems.cards.BaseCard;
import eternityGems.util.CardStats;

import java.util.Collections;

public class RewindTime extends BaseCard
{
    public static final String ID = makeID("RewindTime");
    private static final CardStats info = new CardStats(CardColor.COLORLESS, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, 1);

    public RewindTime() {
        super(ID, info);
        CardModifierManager.addModifier(this, new ExhaustMod());
        CardModifierManager.addModifier(this, new RetainMod());
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        int availableCardSlotsInHand = BaseMod.MAX_HAND_SIZE - AbstractDungeon.player.hand.size();
        int maxDraw = Math.min(availableCardSlotsInHand, 5);

        for (int i = 0; i < maxDraw && i < AbstractDungeon.player.discardPile.group.size(); i++)
        {
            CardGroup tempGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            tempGroup.group.addAll(AbstractDungeon.player.discardPile.group);
            Collections.reverse(tempGroup.group);
            AbstractCard card = tempGroup.group.get(i);
            addToBot(
                new FetchAction(
                    AbstractDungeon.player.discardPile,
                    c -> c.uuid == card.uuid,
                    list -> list.forEach(c -> {
                        if (c.costForTurn > 1) {
                            c.costForTurn = 1;
                            c.isCostModifiedForTurn = true;
                        }
                    })
                )
            );
        }
    }

    public AbstractCard makeCopy() {
        return new RewindTime();
    }
}