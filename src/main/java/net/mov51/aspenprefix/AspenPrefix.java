package net.mov51.aspenprefix;

import net.luckperms.api.LuckPerms;
import net.mov51.aspenprefix.commands.PrefixCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public final class AspenPrefix extends JavaPlugin {

    //Register the LuckPerms API variable
    public static LuckPerms LPapi;
    //register plugin
    public static Logger logger;
    public static org.bukkit.plugin.Plugin plugin = null;

    @Override
    public void onEnable() {
        plugin=this;
        logger = AspenPrefix.plugin.getLogger();

        plugin.saveDefaultConfig();

        //get the LuckPerms API
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            LPapi = provider.getProvider();
            logger.info("LuckPerms dependency loaded!");
        }

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new AspenPrefixPlaceholders(this).register();
        }

        Objects.requireNonNull(this.getCommand("prefix")).setExecutor(new PrefixCommand());
        logger.info("You've been PREFIXED!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
