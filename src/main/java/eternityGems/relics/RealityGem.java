package eternityGems.relics;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import eternityGems.cards.colorless.WarpReality;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.megacrit.cardcrawl.events.AbstractEvent.logMetricObtainCard;
import static eternityGems.EternityGems.makeID;

public class RealityGem extends AbstractEternityGemRelic implements CustomSavable<ArrayList<String>>
{
    public static String NAME = "RealityGem";
    public static String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.BOSS;
    private static final LandingSound SOUND = LandingSound.HEAVY;

    public final CardGroup warpRealityCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    public RealityGem() {
        super(ID, RARITY, SOUND);
        this.cardToPreview = new WarpReality();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public void onEquip()
    {
        AbstractCard warpReality = new WarpReality();
        logMetricObtainCard("RealityGem", "Relic", warpReality);
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(warpReality, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));

        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.INCOMPLETE;

        CardGroup cardPool = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        AbstractDungeon.commonCardPool.group.stream().filter(x -> x.isSeen).forEach(x -> cardPool.addToTop(x.makeCopy()));
        AbstractDungeon.uncommonCardPool.group.stream().filter(x -> x.isSeen).forEach(x -> cardPool.addToTop(x.makeCopy()));
        AbstractDungeon.rareCardPool.group.stream().filter(x -> x.isSeen).forEach(x -> cardPool.addToTop(x.makeCopy()));

        CardGroup selectionCardGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        for (int i = 0; i < 12; i++)
        {
            AbstractCard card = cardPool.getRandomCard(AbstractDungeon.cardRng);
            selectionCardGroup.addToTop(card);
            cardPool.removeCard(card);
        }

        for (int i = 0; i < 3; i++)
        {
            AbstractCard card = cardPool.getRandomCard(AbstractDungeon.cardRng, AbstractCard.CardRarity.RARE);
            selectionCardGroup.addToTop(card);
            cardPool.removeCard(card);
        }

        selectionCardGroup.group.forEach((x) -> {
            if (x.canUpgrade())
                x.upgrade();
        });

        AbstractDungeon.gridSelectScreen.open(selectionCardGroup, 3, "Select 3 Cards to add to your Reality Gem.", false);
    }

    public void update() {
        super.update();
        if (AbstractDungeon.gridSelectScreen.selectedCards.size() == 3)
        {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
                warpRealityCards.addToTop(c);

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.COMPLETE;
        }
    }

    @Override
    public ArrayList<String> onSave() {
        return warpRealityCards.group.stream().map(x -> x.cardID).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public void onLoad(ArrayList<String> strings) {
        CardGroup cardPool = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        AbstractDungeon.commonCardPool.group.stream().filter(x -> x.isSeen).forEach(x -> cardPool.addToTop(x.makeCopy()));
        AbstractDungeon.uncommonCardPool.group.stream().filter(x -> x.isSeen).forEach(x -> cardPool.addToTop(x.makeCopy()));
        AbstractDungeon.rareCardPool.group.stream().filter(x -> x.isSeen).forEach(x -> cardPool.addToTop(x.makeCopy()));

        ArrayList<AbstractCard> cards = cardPool.group.stream().filter(x -> strings.contains(x.cardID)).collect(Collectors.toCollection(ArrayList::new));

        warpRealityCards.group.addAll(cards);
    }
}