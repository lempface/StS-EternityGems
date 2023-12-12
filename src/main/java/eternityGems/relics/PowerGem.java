package eternityGems.relics;

import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import eternityGems.cards.colorless.PowerRush;

import static com.megacrit.cardcrawl.events.AbstractEvent.logMetricObtainCard;
import static eternityGems.EternityGems.makeID;

public class PowerGem extends AbstractEternityGemRelic
{
    public static String NAME = "PowerGem";
    public static String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.BOSS;
    private static final LandingSound SOUND = LandingSound.HEAVY;

    public PowerGem() {
        super(ID, RARITY, SOUND);
        cardToPreview = new PowerRush();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atPreBattle()
    {
        CardGroup powerCards = AbstractDungeon.player.drawPile.getPowers();
        powerCards.group.forEach(card -> {
            card.updateCost(1);
            card.isCostModified = true;
        });
    }

    @Override
    public void onEquip()
    {
        PowerRush powerRush = new PowerRush();
        logMetricObtainCard("Power Gem", "Relic", powerRush);
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(powerRush, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
    }
}
