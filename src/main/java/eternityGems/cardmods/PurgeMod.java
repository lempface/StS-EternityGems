package eternityGems.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;

import static eternityGems.EternityGems.makeID;

public class PurgeMod extends AbstractCardModifier {
    public static String ID = makeID("PurgeCardModifier");

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.purgeOnUse = true;
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new PurgeMod();
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription// 15
                + " NL "
                + "Purge"
                + (Settings.lineBreakViaCharacter ? " " : "")
                + LocalizedStrings.PERIOD;
    }
}
