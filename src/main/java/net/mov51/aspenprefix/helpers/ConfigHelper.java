package net.mov51.aspenprefix.helpers;

import net.mov51.aspenprefix.AspenPrefix;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Objects;
import java.util.Set;

import static net.mov51.aspenprefix.AspenPrefix.logger;

public class ConfigHelper {

    public static final FileConfiguration c = AspenPrefix.plugin.getConfig();
    public static final Set<String> prefixes = Objects.requireNonNull(c.getConfigurationSection("Prefixes")).getKeys(false);
    public static String pluginPrefix = c.getString("chat-prefix") != null ?
            c.getString("chat-prefix") : "Aspen-Prefix";

    public static boolean isPrefixDefined(String prefix){
        return prefixes.contains(prefix);
    }

    public static String getPrefixValue(String requestedPrefixName){
        if(isPrefixDefined(requestedPrefixName)){
            return c.getString("Prefixes." + requestedPrefixName);
        }
        logger.warning("Requested prefix " + requestedPrefixName + " didn't exist!");
        return " ";
    }
}
