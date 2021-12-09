package net.mov51.aspenprefix.conversations;

import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.FixedSetPrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static net.mov51.aspenprefix.helpers.PrefixHelper.getSelectedPrefix;
import static net.mov51.aspenprefix.helpers.PrefixHelper.hasSelectedPrefix;

public class Prompts {
    //todo prefix opener
    // - if no prefix
    // - what current prefix is
    // - if you'd like to select one if possible
    //  - notify if not possible

    public static class OpenPrefixPrompt extends FixedSetPrompt {
        public OpenPrefixPrompt() {

        }

        @NotNull
        public String getPromptText(@NotNull ConversationContext context) {
            Player p = null;
            if (context.getForWhom() instanceof Player){
                p = (Player) context.getForWhom();
            }

            if(hasSelectedPrefix(p)){
                return "You don't have a selected prefix! Would you like to select one? Yes/No";
            }else{
                return "your prefix is " + getSelectedPrefix(p) + "! Would you like to select one? Yes/No";
            }
        }

        @Override @NotNull
        protected Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String s) {
            if (s.equalsIgnoreCase("Yes")) {
                return Prompt.END_OF_CONVERSATION;
            }
            context.setSessionData("type", s);
            return new SelectionPrompt();
        }
    }

    //todo prefix list/selection
    // list available prefixes
    // if none say so

    public static class SelectionPrompt extends FixedSetPrompt {
        public SelectionPrompt() {

        }

        @NotNull
        public String getPromptText(@NotNull ConversationContext context) {
            return "What would you like to summon? " + formatFixedSet();
        }

        @Override
        protected Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String s) {
            return Prompt.END_OF_CONVERSATION;
        }
    }

    //todo custom prefix
}

