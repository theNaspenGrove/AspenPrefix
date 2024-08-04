package mov.naspen.naspenprefix;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static mov.naspen.naspenprefix.helpers.ConfigHelper.getPrefixValue;
import static mov.naspen.naspenprefix.helpers.ConfigHelper.prefixFormat;
import static mov.naspen.naspenprefix.helpers.PrefixHelper.getCurrentPrefix;

public class NaspenPrefixPlaceholders extends PlaceholderExpansion {

    private final NaspenPrefix plugin;

    public NaspenPrefixPlaceholders(NaspenPrefix plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getAuthor() {
        return "naspen";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "NaspenPrefix";
    }

    @Override
    public @NotNull String getVersion() {
        return "${project.version}";
    }

    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    @Override
    public String onRequest(OfflinePlayer p, String params) {

        if(params.equalsIgnoreCase("ActivePrefix")){
            return prefixFormat.replaceFirst("\\$prefix",getPrefixValue(getCurrentPrefix((Player) p)));
        }

        return null; // Placeholder is unknown by the Expansion
    }
}
