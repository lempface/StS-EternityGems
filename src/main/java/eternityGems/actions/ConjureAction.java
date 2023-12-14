package eternityGems.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.random.Random;
import eternityGems.cards.colorless.*;
import eternityGems.relics.RealityGem;

import java.util.ArrayList;

public class ConjureAction extends AbstractGameAction
{

    public ConjureAction()
    {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    private boolean isSelecting;

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST)
        {
            if (!isSelecting) {
                isSelecting = true;
                selectCards();
            }
            tickDuration();
            return;
        }
        if (AbstractDungeon.gridSelectScreen.selectedCards.size() == 2)
        {
            RealityGem gem = (RealityGem) AbstractDungeon.player.getRelic(RealityGem.ID);
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
                gem.inevitablePile.addToTop(c.makeCopy());

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            isSelecting = false;
            this.isDone = true;
        }
        tickDuration();
    }

    private void selectCards()
    {
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }

        AbstractDungeon.gridSelectScreen.open(getSelectionSet(), 2, "Select 2 Cards to add to the Inevitable pile.", false);
        AbstractDungeon.gridSelectScreen.peekButton.hide();
    }

    public static CardGroup getSelectionSet()
    {
        CardGroup cardPool = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        CardGroup seenCardPool = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        CardGroup selectionCardGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        cardPool.group.addAll(getDefaultCardPool());
        cardPool.group.forEach(c -> {
            if (c.isSeen)
                seenCardPool.addToTop(c);
        });

        for (int i = 0; i < 10; i++)
        {
            AbstractCard card;
            boolean getRare = AbstractDungeon.cardRng.randomBoolean(0.05f);
            boolean getUncommon = AbstractDungeon.cardRng.randomBoolean(0.15f);

            AbstractCard.CardRarity rarity = AbstractCard.CardRarity.COMMON;

            if (getRare)
                rarity = AbstractCard.CardRarity.RARE;
            else if (getUncommon)
                rarity = AbstractCard.CardRarity.UNCOMMON;

            card = seenCardPool.getRandomCard(AbstractDungeon.cardRng, rarity);

            selectionCardGroup.addToTop(card);
            seenCardPool.removeCard(card);
        }

        selectionCardGroup.group.forEach((x) -> {
            if (x.canUpgrade())
                x.upgrade();
        });

        return selectionCardGroup;
    }

    public static ArrayList<AbstractCard> getDefaultCardPool()
    {
        ArrayList<AbstractCard> tmpPool = new ArrayList<>();
        CardLibrary.addRedCards(tmpPool);
        CardLibrary.addGreenCards(tmpPool);
        CardLibrary.addBlueCards(tmpPool);
        CardLibrary.addPurpleCards(tmpPool);
        CardLibrary.addColorlessCards(tmpPool);

        ArrayList<AbstractCard> filteredPool = new ArrayList<>();

        for (int i = 0; i < tmpPool.size(); i++)
        {
            AbstractCard card = tmpPool.get(i);
            if (!card.cardID.equals(BefuddleMind.ID) &&
                    !card.cardID.equals(EmboldenSoul.ID) &&
                    !card.cardID.equals(EmpowerMind.ID) &&
                    !card.cardID.equals(ManipulateSpace.ID) &&
                    !card.cardID.equals(PowerRush.ID) &&
                    !card.cardID.equals(RewindTime.ID) &&
                    !card.cardID.equals(WarpReality.ID) &&
                    !card.cardID.equals(TwistAether.ID))
                filteredPool.add(card);
        }

        return filteredPool;
    }
}
