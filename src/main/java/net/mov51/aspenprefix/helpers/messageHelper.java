package net.mov51.aspenprefix.helpers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.mov51.aspenprefix.AspenPrefix;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class messageHelper {
    static final String prefix = AspenPrefix.plugin.getConfig().getString("chat-prefix");
    static final String colorPrefix = ChatColor.translateAlternateColorCodes('&', prefix != null ? prefix : "&6&l[&2Aspen-Prefix&6&l]&r");

    public static void sendChatMessage(Player p, String message){
        p.sendMessage(colorPrefix + " " + message);
    }

    public static void sendChatMessage(Player p, ArrayList<String> messages){
        for(String message : messages){
            p.sendMessage(colorPrefix + " " + message);
        }
    }

    public static void sendColoredChatMessage(Player p, String message){
        p.sendMessage(colorPrefix + " " + ChatColor.translateAlternateColorCodes('&',message));
    }

    public static void sendColoredChatMessage(Player p, ArrayList<String> messages) {
        for (String message : messages) {
            p.sendMessage(colorPrefix + " " + ChatColor.translateAlternateColorCodes('&', message));
        }
    }

    public static TextComponent buildCommandComponent(String message, String command){
        return Component.text()
                .content(message)
                .clickEvent(ClickEvent.runCommand(command))
                .build();
    }
}
