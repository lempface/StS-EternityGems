package eternityGems.ui;

import basemod.TopPanelItem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import eternityGems.patches.ExhaustPileViewScreenPatches;
import eternityGems.relics.RealityGem;

import static eternityGems.EternityGems.makeID;

public class InevitableTopPanelItem extends TopPanelItem {
    private static final Texture IMG = new Texture("eternityGems/images/InevitablePileIcon.png");
    public static final String ID = makeID("InevitableTopPanel");
    private boolean isOpen;

    public InevitableTopPanelItem()
    {
        super(IMG, ID);
    }

    @Override
    public void render(SpriteBatch sb)
    {
        if (AbstractDungeon.player.hasRelic(RealityGem.ID))
        {
            super.render(sb);
            RealityGem gem = (RealityGem) AbstractDungeon.player.getRelic(RealityGem.ID);
            String msg = Integer.toString(gem.inevitablePile.size());
            FontHelper.renderFontCentered(sb, FontHelper.topPanelAmountFont, msg, this.x + 48.0F, this.y + 16.0F * Settings.scale, Color.WHITE);
        }
    }

    @Override
    protected void onHover()
    {
        super.onHover();
        if (AbstractDungeon.player.hasRelic(RealityGem.ID)) {
            TipHelper.renderGenericTip(1550.0F * Settings.scale, Settings.HEIGHT - 120.0F * Settings.scale, "Inevitable Pile", "View the cards in the Inevitable Pile.");
        }
    }

    @Override
    protected void onClick() {
        if (AbstractDungeon.player.hasRelic(RealityGem.ID)) {
            ExhaustPileViewScreenPatches.showCollection = true;
            if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD) {
                AbstractDungeon.closeCurrentScreen();
                AbstractDungeon.exhaustPileViewScreen.open();
                AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
                isOpen = true;
            } else if (!AbstractDungeon.isScreenUp) {
                AbstractDungeon.exhaustPileViewScreen.open();
                isOpen = true;
            } else if (isOpen && AbstractDungeon.screen == AbstractDungeon.CurrentScreen.EXHAUST_VIEW) {
                isOpen = false;
                AbstractDungeon.screenSwap = false;
                AbstractDungeon.closeCurrentScreen();
                CardCrawlGame.sound.play("DECK_CLOSE", 0.05F);
            } else if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.DEATH) {
                AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.DEATH;
                AbstractDungeon.deathScreen.hide();
                AbstractDungeon.exhaustPileViewScreen.open();
                isOpen = true;
            } else if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.BOSS_REWARD) {
                AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.BOSS_REWARD;
                AbstractDungeon.bossRelicScreen.hide();
                AbstractDungeon.exhaustPileViewScreen.open();
                isOpen = true;
            } else if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.SHOP) {
                AbstractDungeon.overlayMenu.cancelButton.hide();
                AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.SHOP;
                AbstractDungeon.exhaustPileViewScreen.open();
                isOpen = true;
            } else if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.MAP && !AbstractDungeon.dungeonMapScreen.dismissable) {
                AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.MAP;
                AbstractDungeon.exhaustPileViewScreen.open();
                isOpen = true;
            } else if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.SETTINGS || AbstractDungeon.screen == AbstractDungeon.CurrentScreen.MAP) {
                if (AbstractDungeon.previousScreen != null)
                    AbstractDungeon.screenSwap = true;
                AbstractDungeon.closeCurrentScreen();
                AbstractDungeon.exhaustPileViewScreen.open();
                isOpen = true;
            } else if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.CARD_REWARD) {
                AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.CARD_REWARD;
                AbstractDungeon.dynamicBanner.hide();
                AbstractDungeon.exhaustPileViewScreen.open();
                isOpen = true;
            } else if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID) {
                AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.GRID;
                AbstractDungeon.gridSelectScreen.hide();
                AbstractDungeon.exhaustPileViewScreen.open();
                isOpen = true;
            } else if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.HAND_SELECT) {
                AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.HAND_SELECT;
                AbstractDungeon.exhaustPileViewScreen.open();
                isOpen = true;
            }
            ExhaustPileViewScreenPatches.showCollection = false;
        }
    }
}
