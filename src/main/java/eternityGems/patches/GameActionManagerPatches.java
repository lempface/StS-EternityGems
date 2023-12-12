package eternityGems.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import eternityGems.relics.TimeGem;
import javassist.CtBehavior;

public class GameActionManagerPatches
{
    @SpirePatch(clz= GameActionManager.class, method="getNextAction")
    public static class getNextActionPatch
    {
        @SpireInsertPatch(locator=Locator.class)
        public static void timeGemLogic() {
            AbstractRelic gem = AbstractDungeon.player.getRelic(TimeGem.ID);
            if (gem != null)
                ((TimeGem)gem).disableUntilTurnEnds();
        }

        public static class Locator extends SpireInsertLocator
        {

            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "getRelic");

                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }
}
