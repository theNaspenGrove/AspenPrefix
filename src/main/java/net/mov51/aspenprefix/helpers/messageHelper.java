package net.mov51.aspenprefix.helpers;

import net.mov51.aspenprefix.AspenPrefix;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class messageHelper {
    static String prefix = AspenPrefix.plugin.getConfig().getString("chat-prefix");
    static String colorPrefix = ChatColor.translateAlternateColorCodes('&', prefix != null ? prefix : "&6&l[&2Aspen-Prefix&6&l]&r");

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
}
