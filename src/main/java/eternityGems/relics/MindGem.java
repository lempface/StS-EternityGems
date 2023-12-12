package eternityGems.relics;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import eternityGems.cards.colorless.BefuddleMind;
import eternityGems.cards.colorless.EmpowerMind;

import static com.megacrit.cardcrawl.events.AbstractEvent.logMetricObtainCard;
import static eternityGems.EternityGems.makeID;

public class MindGem extends AbstractEternityGemRelic
{
    public static String NAME = "MindGem";
    public static String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.BOSS;
    private static final LandingSound SOUND = LandingSound.HEAVY;

    public MindGem() {
        super(ID, RARITY, SOUND);
        this.cardToPreview = new BefuddleMind();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public void onEquip()
    {
        BefuddleMind befuddleMind = new BefuddleMind();
        logMetricObtainCard("Space Gem", "Relic", befuddleMind);
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(befuddleMind, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
    }

    @Override
    public void atBattleStartPreDraw() {
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new MakeTempCardInHandAction(new EmpowerMind(), 1, false));
    }
}
