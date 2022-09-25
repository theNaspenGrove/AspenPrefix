package net.mov51.aspenprefix.helpers;

import net.mov51.aspenprefix.AspenPrefix;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import static net.mov51.aspenprefix.AspenPrefix.logger;

public class ConfigHelper {

    public static final FileConfiguration c = AspenPrefix.plugin.getConfig();
    public static TreeMap<String, Prefix> prefixes = new TreeMap<>();

    public static final String PrefixConfigSection = "Prefixes";
    public static String pluginPrefix = c.getString("chat-prefix") != null ?
            c.getString("chat-prefix") : "Aspen-Prefix";
    public static String prefixFormat = c.getString("prefix-format") != null ?
            c.getString("prefix-format") : "&6[&r{#812409}$prefix&6]&r";
    public static String defaultPlayerPrefix = c.getString("default-prefix");
    public static final String defaultPrefixTarget = "default-prefix";


    public static void loadPrefixes() {
        Set<String> prefixKeys = c.getConfigurationSection(PrefixConfigSection).getKeys(false);
        for (String key : prefixKeys) {
            String prefix = c.getString(PrefixConfigSection + "." + key + ".prefix");
            int weight = c.getInt(PrefixConfigSection + "." + key + ".weight");
            Prefix p = new Prefix(prefix, weight);
            prefixes.put(key, p);
        }
        logger.info("Loaded " + prefixes.size() + " prefixes");
    }

    public static boolean isPrefixDefined(String prefix){
        return prefixes.containsKey(prefix);
    }

    public static int getPrefixWeight(String prefix){
        return prefixes.get(prefix).weight;
    }

    public static String getPrefixValue(String requestedPrefixName){
        if(isPrefixDefined(requestedPrefixName)){
            return prefixes.get(requestedPrefixName).prefix;
        }else if(requestedPrefixName.equals(defaultPrefixTarget)){
            return defaultPlayerPrefix;
        }
        logger.warning("Requested prefix " + requestedPrefixName + " didn't exist!");
        return " ";
    }
}
