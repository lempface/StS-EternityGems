package eternityGems.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.powers.RegenPower;
import eternityGems.cards.colorless.EmboldenSoul;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import static eternityGems.EternityGems.makeID;
import static com.megacrit.cardcrawl.events.AbstractEvent.logMetricObtainCard;

public class SoulGem extends AbstractEternityGemRelic
{
    public static String NAME = "SoulGem";
    public static String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.BOSS;
    private static final LandingSound SOUND = LandingSound.HEAVY;

    public SoulGem() {
        super(ID, RARITY, SOUND);
        this.cardToPreview = new EmboldenSoul();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public void onEquip()
    {
        this.counter = 0;
        AbstractCard emboldenSoul = new EmboldenSoul();
        logMetricObtainCard("Soul Gem", "Relic", emboldenSoul);
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(emboldenSoul, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
    }

    @Override
    public void onExhaust(AbstractCard card)
    {
        this.counter += 1;

        if (this.counter == 2) {
            this.beginLongPulse();
        }

        if (this.counter == 3)
        {
            this.counter = 0;
            this.flash();
            this.stopPulse();
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RegenPower(AbstractDungeon.player, 1), 1));
        }
    }
}