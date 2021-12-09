package net.mov51.aspenprefix.conversations;

import net.mov51.aspenprefix.AspenPrefix;
import net.mov51.aspenprefix.helpers.PlayerResponseListener;
import org.bukkit.ChatColor;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PrefixConversation implements ConversationAbandonedListener {

    private static PrefixConversation single_instance = null;

    public static PrefixConversation getInstance()
    {
        if (single_instance == null)
            single_instance = new PrefixConversation();

        return single_instance;
    }

    public ConversationFactory PrefixOpener = new ConversationFactory(AspenPrefix.plugin)
            .withModality(true)
            .withPrefix(new SummoningConversationPrefix())
            .withFirstPrompt(new Prompts.OpenPrefixPrompt())
            .withEscapeSequence("/quit")
            .withTimeout(10)
            .thatExcludesNonPlayersWithMessage("Go away evil console!")
            .addConversationAbandonedListener(this);


    @Override
    public void conversationAbandoned(@NotNull ConversationAbandonedEvent conversationAbandonedEvent) {
        if (conversationAbandonedEvent.gracefulExit()) {
            conversationAbandonedEvent.getContext().getForWhom().sendRawMessage("Conversation exited gracefully.");
        } else {
            conversationAbandonedEvent.getContext().getForWhom().sendRawMessage("Conversation abandoned by" + Objects.requireNonNull(conversationAbandonedEvent.getCanceller()).getClass().getName());
        }
    }

    private static class SummoningConversationPrefix implements ConversationPrefix {

    public @NotNull String getPrefix(ConversationContext context) {
        String what = (String)context.getSessionData("type");
        Integer count = (Integer)context.getSessionData("count");
        Player who = (Player)context.getSessionData("who");

        if (what != null && count == null && who == null) {
            return ChatColor.GREEN + "Summon " + what + ": " + ChatColor.WHITE;
        }
        if (what != null && count != null && who == null) {
            return ChatColor.GREEN + "Summon " + count + " " + what + ": " + ChatColor.WHITE;
        }
        if (what != null && count != null) {
            return ChatColor.GREEN + "Summon " + count + " " + what + " to " + who.getName() + ": " + ChatColor.WHITE;
        }
        return ChatColor.GREEN + "Summon: " + ChatColor.WHITE;
    }
}
}
