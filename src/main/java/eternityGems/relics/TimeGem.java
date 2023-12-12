package eternityGems.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import eternityGems.actions.BottomOfDiscardPileToHandAction;
import eternityGems.cards.colorless.RewindTime;

import static com.megacrit.cardcrawl.events.AbstractEvent.logMetricObtainCard;
import static eternityGems.EternityGems.makeID;

public class TimeGem extends AbstractEternityGemRelic
{
    public static String NAME = "TimeGem";
    public static String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.BOSS;
    private static final LandingSound SOUND = LandingSound.HEAVY;

    private boolean canDraw = false;

    private boolean disabledUntilEndOfTurn = false;

    public TimeGem() {
        super(ID, RARITY, SOUND);
        this.cardToPreview = new RewindTime();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public void onEquip()
    {
        AbstractCard rewindTime = new RewindTime();
        logMetricObtainCard("Time Gem", "Relic", rewindTime);
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(rewindTime, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
    }

    public void atPreBattle() {
        this.canDraw = false;
    }

    public void atTurnStart() {
        this.canDraw = true;
        this.disabledUntilEndOfTurn = false;
    }

    public void disableUntilTurnEnds() {
        this.disabledUntilEndOfTurn = true;
    }

    public void onRefreshHand() {
        if (AbstractDungeon.actionManager.actions.isEmpty()
                && AbstractDungeon.player.hand.isEmpty()
                && !AbstractDungeon.actionManager.turnHasEnded
                && this.canDraw
                && !AbstractDungeon.player.hasPower("No Draw")
                && !AbstractDungeon.isScreenUp)
            if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT
                    && !this.disabledUntilEndOfTurn
                    && !AbstractDungeon.player.discardPile.isEmpty()) {
                flash();
                addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                addToBot(new BottomOfDiscardPileToHandAction());
            }
    }
}