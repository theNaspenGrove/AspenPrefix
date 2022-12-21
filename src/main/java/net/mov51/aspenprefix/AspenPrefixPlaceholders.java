package net.mov51.aspenprefix;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static net.mov51.aspenprefix.helpers.ConfigHelper.getPrefixValue;
import static net.mov51.aspenprefix.helpers.ConfigHelper.prefixFormat;
import static net.mov51.aspenprefix.helpers.PrefixHelper.getCurrentPrefix;

public class AspenPrefixPlaceholders extends PlaceholderExpansion {

    private final AspenPrefix plugin;



    public AspenPrefixPlaceholders(AspenPrefix plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getAuthor() {
        return "mov51";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "AspenPrefix";
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

        if(params.equalsIgnoreCase("ActiveAspenPrefix")){
            return prefixFormat.replaceFirst("\\$prefix",getPrefixValue(getCurrentPrefix((Player) p)));
        }

        return null; // Placeholder is unknown by the Expansion
    }
}
