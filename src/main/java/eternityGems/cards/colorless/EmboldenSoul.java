package eternityGems.cards.colorless;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;
import eternityGems.cards.BaseCard;
import eternityGems.util.CardStats;

public class EmboldenSoul extends BaseCard
{
    public static final String ID = makeID("EmboldenSoul");
    private static final CardStats info = new CardStats(CardColor.COLORLESS, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, 1);

    public EmboldenSoul() {
        super(ID, info);
        CardModifierManager.addModifier(this, new ExhaustMod());
        this.magicNumber = this.baseMagicNumber = 3;
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RegenPower(AbstractDungeon.player, this.magicNumber), this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return new EmboldenSoul();
    }
}