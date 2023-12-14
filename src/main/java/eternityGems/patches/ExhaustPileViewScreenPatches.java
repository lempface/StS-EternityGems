package eternityGems.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.ExhaustPileViewScreen;
import eternityGems.relics.RealityGem;
import javassist.CtBehavior;


public class ExhaustPileViewScreenPatches {

    public static boolean showCollection = false;
    private static boolean showingCollection = false;

    @SpirePatch(
            clz = ExhaustPileViewScreen.class,
            method = "open"
    )
    public static class OpenExhaustPileViewScreenPatch {
        @SpireInsertPatch(locator = OpenExhaustPileViewScreenPatchLocator.class)
        public static void Insert(ExhaustPileViewScreen _instance) {
            if (showCollection && AbstractDungeon.player.hasRelic(RealityGem.ID)) {
                CardGroup group = ReflectionHacks.getPrivate(_instance, ExhaustPileViewScreen.class, "exhaustPileCopy");
                RealityGem gem = (RealityGem) AbstractDungeon.player.getRelic(RealityGem.ID);
                group.clear();
                group.group.addAll(gem.inevitablePile.group);
                showCollection = false;
                showingCollection = true;
            } else {
                showingCollection = false;
            }
        }
    }

    private static class OpenExhaustPileViewScreenPatchLocator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(ExhaustPileViewScreen.class, "hideCards");
            return LineFinder.findInOrder(ctBehavior, matcher);
        }
    }

    @SpirePatch(
            clz = ExhaustPileViewScreen.class,
            method = "render"
    )
    public static class RenderExhaustPileViewScreenPatch {
        @SpireInsertPatch(locator = RenderExhaustPileViewScreenPatchLocator.class)
        public static SpireReturn<Void> Insert(ExhaustPileViewScreen _instance, SpriteBatch sb) {
            if (showingCollection) {
                FontHelper.renderDeckViewTip(sb, "Cards available for Warped Reality.", 96.0F * Settings.scale, Settings.CREAM_COLOR);// 311
                return SpireReturn.Return(null);
            } else {
                return SpireReturn.Continue();
            }
        }
    }

    private static class RenderExhaustPileViewScreenPatchLocator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(FontHelper.class, "renderDeckViewTip");
            return LineFinder.findInOrder(ctBehavior, matcher);
        }
    }
}
