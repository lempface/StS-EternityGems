package eternityGems.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import eternityGems.relics.PowerGem;
import javassist.CtBehavior;

import java.util.ArrayList;

public class UseCardActionPatches {
    @SpirePatch(clz = UseCardAction.class, method = "update")
    public static class updatePatch {
        @SpireInsertPatch(locator = Locator.class)
        public static void playerHasPowerGem(UseCardAction __instance) {
            if (AbstractDungeon.player.hasRelic(PowerGem.ID)) {
                AbstractCard targetCard = ReflectionHacks.getPrivate(__instance, UseCardAction.class, "targetCard");

                AbstractDungeon.player.hand.moveToDiscardPile(targetCard);
            }
        }

        public static class Locator extends SpireInsertLocator {

            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                ArrayList<Matcher> matchers = new ArrayList<>();
                matchers.add(new Matcher.FieldAccessMatcher(AbstractCard.class, "type"));
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "cardInUse");

                return LineFinder.findInOrder(ctBehavior, matchers, finalMatcher);
            }
        }
    }
}
