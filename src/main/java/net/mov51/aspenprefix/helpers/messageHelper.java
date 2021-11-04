package net.mov51.aspenprefix.helpers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.mov51.aspenprefix.AspenPrefix;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

public class messageHelper {
    static String prefix = AspenPrefix.plugin.getConfig().getString("chat-prefix");
    static TextComponent componentPrefix =
            Component.text("[")
                    .color(NamedTextColor.GOLD)
                    .decoration(TextDecoration.BOLD, true)
            .append(Component.text(prefix != null ? prefix : "Aspen-Prefix")
                    .color(NamedTextColor.DARK_GREEN)
                    .decoration(TextDecoration.BOLD, false))
            .append(Component.text("] ")
                    .color(NamedTextColor.GOLD)
                    .decoration(TextDecoration.BOLD, true));

    public static void sendChatMessage(Player p, TextComponent message){
        p.sendMessage(Component.text().append(componentPrefix).append(message).build());
    }

    public static void sendBarMessage(Player p){
        sendChatMessage(p,Component.text()
                .content(StringUtils.center("-----",53))
                .build());
    }

    public static TextComponent buildCommandComponent(String message, String command){
        return Component.text(message)
                .clickEvent(ClickEvent.runCommand(command));
    }

}
