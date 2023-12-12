package eternityGems.cards.colorless;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import eternityGems.cards.BaseCard;
import eternityGems.util.CardStats;


public class ManipulateSpace extends BaseCard
{
    public static final String ID = makeID("ManipulateSpace");
    private static final CardStats info = new CardStats(CardColor.COLORLESS, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, 1);

    public ManipulateSpace() {
        super(ID, info);
        CardModifierManager.addModifier(this, new ExhaustMod());
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        int cardsInHand = AbstractDungeon.player.hand.size();

        if (cardsInHand == 0)
            return;

        for (int i = 0; i < cardsInHand; i++) {
            if (Settings.FAST_MODE) {
                addToTop(new ExhaustAction(1, true, true, false, Settings.ACTION_DUR_XFAST));
            } else {
                addToTop(new ExhaustAction(1, true, true));
            }
        }

        addToBot(new SelectCardsAction(
                AbstractDungeon.player.exhaustPile.group,
                cardsInHand,
                "Select up to " + cardsInHand + " Card(s) to add to Your Hand",
                true,
                c -> true,
                list -> list.forEach(c -> {
                    addToBot(
                        new FetchAction(
                                abstractPlayer.exhaustPile,
                                exhaustedCard -> exhaustedCard.uuid == c.uuid)
                    );
                })
            )
        );
    }

    public AbstractCard makeCopy() {
        return new ManipulateSpace();
    }
}

