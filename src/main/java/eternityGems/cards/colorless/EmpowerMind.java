package eternityGems.cards.colorless;

import basemod.cardmods.ExhaustMod;
import basemod.cardmods.RetainMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import eternityGems.cards.BaseCard;
import eternityGems.util.CardStats;

public class EmpowerMind extends BaseCard {

    public static final String ID = makeID("EmpowerMind");
    private static final CardStats info = new CardStats(CardColor.COLORLESS, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, 0);

    public EmpowerMind() {
        super(ID, info);
        CardModifierManager.addModifier(this, new RetainMod());
        CardModifierManager.addModifier(this, new ExhaustMod());
        this.magicNumber = this.baseMagicNumber = 2;
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToTop(new GainEnergyAction(this.magicNumber));
    }
}
