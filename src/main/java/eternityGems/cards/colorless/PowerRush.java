package eternityGems.cards.colorless;

import basemod.cardmods.RetainMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import eternityGems.actions.PowerRushAction;
import eternityGems.cards.BaseCard;
import eternityGems.util.CardStats;

public class PowerRush extends BaseCard
{
    public static final String ID = makeID("PowerRush");
    private static final CardStats info = new CardStats(CardColor.COLORLESS, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, 0);

    public PowerRush() {
        super(ID, info);
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            CardModifierManager.addModifier(this, new RetainMod());
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new PowerRushAction());
    }

    public AbstractCard makeCopy() {
        return new PowerRush();
    }
}

