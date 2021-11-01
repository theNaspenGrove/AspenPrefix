package net.mov51.aspenprefix.helpers;

import net.mov51.aspenprefix.AspenPrefix;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Objects;
import java.util.Set;

import static net.mov51.aspenprefix.AspenPrefix.logger;

public class ConfigHelper {

    public static final FileConfiguration c = AspenPrefix.plugin.getConfig();
    public static final Set<String> prefixes = Objects.requireNonNull(c.getConfigurationSection("Prefixes")).getKeys(false);

    public static boolean isPrefixDefined(String prefix){
        return prefixes.contains(prefix);
    }

    public static String getPrefix(String requestedPrefix){
        if(isPrefixDefined(requestedPrefix)){
            return c.getString("Prefixes." + requestedPrefix);
        }
        logger.warning("Requested prefix " + requestedPrefix + " didn't exist!");
        return " ";
    }
}
