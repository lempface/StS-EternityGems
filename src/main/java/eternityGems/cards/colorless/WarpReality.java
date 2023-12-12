package eternityGems.cards.colorless;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import eternityGems.actions.WarpRealityAction;
import eternityGems.cards.BaseCard;
import eternityGems.util.CardStats;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class WarpReality extends BaseCard
{
    public static final String ID = makeID("WarpReality");
    private static final CardStats info = new CardStats(CardColor.COLORLESS, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, 3);

    public WarpReality() {
        super(ID, info);
        CardModifierManager.addModifier(this, new ExhaustMod());
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(2);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new WarpRealityAction(false));
    }

    public AbstractCard makeCopy() {
        return new WarpReality();
    }
}