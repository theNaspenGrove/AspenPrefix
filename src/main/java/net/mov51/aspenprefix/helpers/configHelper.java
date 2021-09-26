package net.mov51.aspenprefix.helpers;

import net.mov51.aspenprefix.AspenPrefix;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Objects;
import java.util.Set;

import static net.mov51.aspenprefix.AspenPrefix.logger;

public class configHelper {

    public static final FileConfiguration c = AspenPrefix.plugin.getConfig();
    public static final Set<String> prefixes = Objects.requireNonNull(c.getConfigurationSection("Prefixes")).getKeys(false);

    public static boolean isDefined(String prefix){
        if(prefixes.contains(prefix)){
            return true;
        }
        logger.warning(ChatColor.RED + "Prefix " + prefix + " is not defined in the config but you have a permission node for it!");
        return false;
    }

    public static String getPrefix(String requestedPrefix){
        if(isDefined(requestedPrefix)){
            return c.getString("Prefixes." + requestedPrefix);
        }
        logger.warning("Requested prefix " + requestedPrefix + " didn't exist!");
        return " ";
    }
}
