package mov.naspen.naspenprefix.helpers;

import mov.naspen.naspenprefix.NaspenPrefix;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Set;
import java.util.TreeMap;

public class ConfigHelper {

    public static final FileConfiguration c = NaspenPrefix.plugin.getConfig();
    public static final TreeMap<String, Prefix> prefixes = new TreeMap<>();

    public static final String PrefixConfigSection = "Prefixes";
    public static final String pluginPrefix = c.getString("chat-prefix") != null ?
            c.getString("chat-prefix") : "Naspen-Prefix";
    public static final String prefixFormat = c.getString("prefix-format") != null ?
            c.getString("prefix-format") : "&6[&r{#812409}$prefix&6]&r";
    public static final String defaultPlayerPrefix = c.getString("default-prefix");
    public static final String defaultPrefixTarget = "default-prefix";


    public static void loadPrefixes() {
        Set<String> prefixKeys = c.getConfigurationSection(PrefixConfigSection).getKeys(false);
        for (String key : prefixKeys) {
            String prefix = c.getString(PrefixConfigSection + "." + key + ".prefix");
            int weight = c.getInt(PrefixConfigSection + "." + key + ".weight");
            Prefix p = new Prefix(prefix, weight);
            prefixes.put(key, p);
        }
        NaspenPrefix.logger.info("Loaded " + prefixes.size() + " prefixes");
    }

    public static boolean isPrefixDefined(String prefix){
        return prefixes.containsKey(prefix);
    }

    public static int getPrefixWeight(String prefix){
        return prefixes.get(prefix).getWeight();
    }

    public static String getPrefixValue(String requestedPrefixName){
        if(isPrefixDefined(requestedPrefixName)){
            return prefixes.get(requestedPrefixName).getPrefix();
        }else if(requestedPrefixName.equals(defaultPrefixTarget)){
            return defaultPlayerPrefix;
        }
        NaspenPrefix.logger.warning("Requested prefix " + requestedPrefixName + " didn't exist!");
        return defaultPlayerPrefix;
    }
}
