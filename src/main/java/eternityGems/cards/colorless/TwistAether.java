package eternityGems.cards.colorless;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import eternityGems.actions.ConjureAction;
import eternityGems.cards.BaseCard;
import eternityGems.util.CardStats;

public class TwistAether extends BaseCard
{
    public static final String ID = makeID("TwistAether");
    private static final CardStats info = new CardStats(CardColor.COLORLESS, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, 1);

    public TwistAether()
    {
        super(ID, info);
        CardModifierManager.addModifier(this, new ExhaustMod());
        this.magicNumber = this.baseMagicNumber = 2;
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToTop(new ConjureAction());
    }
}
