package eternityGems.relics;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import eternityGems.actions.ConjureAction;
import eternityGems.cards.colorless.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.megacrit.cardcrawl.events.AbstractEvent.logMetricObtainCard;
import static eternityGems.EternityGems.makeID;
import static eternityGems.actions.ConjureAction.getSelectionSet;

public class RealityGem extends AbstractEternityGemRelic implements CustomSavable<ArrayList<String>>
{
    public static String NAME = "RealityGem";
    public static String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.BOSS;
    private static final LandingSound SOUND = LandingSound.HEAVY;

    public RealityGem() {
        super(ID, RARITY, SOUND);
        this.cardToPreview = new WarpReality();
        this.cardToPreview2 = new TwistAether();
    }

    public CardGroup inevitablePile = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public void onEquip()
    {
        this.counter = 0;

        if (AbstractDungeon.player.chosenClass != AbstractPlayer.PlayerClass.DEFECT && AbstractDungeon.player.masterMaxOrbs == 0)
            AbstractDungeon.player.masterMaxOrbs = 1;

        AbstractCard warpReality = new WarpReality();
        logMetricObtainCard("RealityGem", "Relic", warpReality);
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(warpReality, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
        AbstractCard twistAether = new TwistAether();
        logMetricObtainCard("TwistAether", "Relic", twistAether);
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(twistAether, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
    }

    private boolean shouldSelect;
    private boolean isSelecting;

    @Override
    public void update()
    {
        super.update();
        if (shouldSelect) {
            selectCards();
        }
        if (AbstractDungeon.gridSelectScreen.selectedCards.size() == 1 && isSelecting)
        {
            RealityGem gem = (RealityGem) AbstractDungeon.player.getRelic(RealityGem.ID);
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
                gem.inevitablePile.addToTop(c.makeCopy());

            AbstractDungeon.gridSelectScreen.selectedCards.clear();

            isSelecting = false;
        }

    }

    private void selectCards()
    {
        isSelecting = true;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }

        AbstractDungeon.gridSelectScreen.open(getSelectionSet(), 1, "Select a Card to add to the Inevitable pile.", false);
        AbstractDungeon.gridSelectScreen.peekButton.hide();
        shouldSelect = false;
    }

    @Override
    public void onVictory()
    {
        counter += 1;
        if (counter == 3)
        {
            this.flash();
            shouldSelect = true;
            counter = 0;
        }
    }

    @Override
    public ArrayList<String> onSave() {
        ArrayList<String> cardStrings = inevitablePile.group.stream().map(x -> x.cardID).collect(Collectors.toCollection(ArrayList::new));
        return cardStrings;
    }

    @Override
    public void onLoad(ArrayList<String> strings) {
        CardGroup cardPool = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        cardPool.group.addAll(ConjureAction.getDefaultCardPool());

        ArrayList<AbstractCard> cards = cardPool.group.stream().filter(x -> strings.contains(x.cardID)).collect(Collectors.toCollection(ArrayList::new));

        cards.forEach((x) -> {
            if (x.canUpgrade())
                x.upgrade();
        });

        inevitablePile.group.addAll(cards);
    }
}
