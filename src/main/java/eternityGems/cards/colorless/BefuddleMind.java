package eternityGems.cards.colorless;

import basemod.ReflectionHacks;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;
import eternityGems.cards.BaseCard;
import eternityGems.util.CardStats;

public class BefuddleMind extends BaseCard
{
    public static final String ID = makeID("BefuddleMind");
    private static final CardStats info = new CardStats(CardColor.COLORLESS, CardType.SKILL, CardRarity.SPECIAL, CardTarget.ENEMY, 2);

    public BefuddleMind() {
        super(ID, info);
        CardModifierManager.addModifier(this, new ExhaustMod());
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster m) {
        if (m.getIntentBaseDmg() >= 0)
        {
            EnemyMoveInfo moveInfo = ReflectionHacks.getPrivate(m, AbstractMonster.class, "move");
            if (moveInfo.isMultiDamage)
            {
                for (int i = 0; i < moveInfo.multiplier; i++) {

                        addToBot(new DamageAction(m, new DamageInfo(m, m.getIntentDmg(), this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                }
            } else {
                addToBot(new DamageAction(m, new DamageInfo(m, m.getIntentDmg(), this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            }
        }

        addToBot(new StunMonsterAction(m, abstractPlayer));
    }

    public AbstractCard makeCopy() {
        return new BefuddleMind();
    }
}
