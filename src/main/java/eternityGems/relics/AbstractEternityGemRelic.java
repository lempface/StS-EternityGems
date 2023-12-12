package eternityGems.relics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import eternityGems.util.GeneralUtils;

public abstract class AbstractEternityGemRelic extends BaseRelic {

    private float yOffset = -166.0F;
    private float xOffset = 524.0F;
    private float xBeforeLeftRender = 1100.0F;
    protected AbstractCard cardToPreview;
    public AbstractEternityGemRelic(String id, String imageName, AbstractCard.CardColor pool, RelicTier tier, LandingSound sfx) {
        super(id, imageName, pool, tier, sfx);
    }

    public AbstractEternityGemRelic(String id, RelicTier tier, LandingSound sfx) {
        this(id, GeneralUtils.removePrefix(id), tier, sfx);
    }
    public AbstractEternityGemRelic(String id, String imageName, RelicTier tier, LandingSound sfx) {
        super(id, imageName, tier, sfx);
    }

    public void renderTip(SpriteBatch sb) {
        this.cardToPreview.current_y = InputHelper.mY + yOffset * Settings.scale;
        this.cardToPreview.drawScale = 0.8F;
        if (InputHelper.mX < xBeforeLeftRender * Settings.scale) {
            this.cardToPreview.current_x = InputHelper.mX + xOffset * Settings.scale;
            if (CardCrawlGame.mainMenuScreen.screen == MainMenuScreen.CurScreen.RELIC_VIEW) {
                TipHelper.queuePowerTips(180.0F * Settings.scale, Settings.HEIGHT * 0.7F, this.tips);
            } else if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.SHOP && this.tips.size() > 2 &&
                    !AbstractDungeon.player.hasRelic(this.relicId)) {
                TipHelper.queuePowerTips(InputHelper.mX + 60.0F * Settings.scale, InputHelper.mY + 180.0F * Settings.scale, this.tips);
            } else if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(this.relicId)) {
                TipHelper.queuePowerTips(InputHelper.mX + 60.0F * Settings.scale, InputHelper.mY - 30.0F * Settings.scale, this.tips);
            } else if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD) {
                TipHelper.queuePowerTips(360.0F * Settings.scale, InputHelper.mY + 50.0F * Settings.scale, this.tips);
            } else {
                TipHelper.queuePowerTips(InputHelper.mX + 50.0F * Settings.scale, InputHelper.mY + 50.0F * Settings.scale, this.tips);
            }
        } else {
            TipHelper.queuePowerTips(InputHelper.mX - 350.0F * Settings.scale, InputHelper.mY - 30.0F * Settings.scale, this.tips);
            this.cardToPreview.current_x = InputHelper.mX + (-xOffset + 30.0f) * Settings.scale;
        }


        this.cardToPreview.render(sb);
    }




}
