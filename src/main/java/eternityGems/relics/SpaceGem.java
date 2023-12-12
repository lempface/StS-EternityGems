package eternityGems.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import eternityGems.cards.colorless.ManipulateSpace;

import static com.megacrit.cardcrawl.events.AbstractEvent.logMetricObtainCard;
import static eternityGems.EternityGems.makeID;

public class SpaceGem extends AbstractEternityGemRelic
{
    public static String NAME = "SpaceGem";
    public static String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.BOSS;
    private static final LandingSound SOUND = LandingSound.HEAVY;

    public SpaceGem() {
        super(ID, RARITY, SOUND);
        this.cardToPreview = new ManipulateSpace();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public void onEquip()
    {
        this.counter = 0;
        AbstractCard manipulateSpace = new ManipulateSpace();
        logMetricObtainCard("Space Gem", "Relic", manipulateSpace);
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(manipulateSpace, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
    }

    @Override
    public void onExhaust(AbstractCard card)
    {
        this.counter += 1;
    }

    @Override
    public void onVictory()
    {
        handleCardRemoval();
    }

    private void handleCardRemoval()
    {
        if (this.counter >= 20)
        {
            this.counter -= 20;
            this.flash();
            AbstractDungeon.gridSelectScreen.open(
                    CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()),
                    1,
                    "The Space Gem offers card removal. Select a Card to Remove.",
                    false,
                    false,
                    true,
                    true);
        }
    }

    @Override
    public void update()
    {
        super.update();
        if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty() && AbstractDungeon.gridSelectScreen.forPurge) {
            AbstractCard card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            CardCrawlGame.sound.play("CARD_EXHAUST");
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(card, (Settings.WIDTH / 2), (Settings.HEIGHT / 2)));
            AbstractDungeon.player.masterDeck.removeCard(card);
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            handleCardRemoval();
        }
    }
}