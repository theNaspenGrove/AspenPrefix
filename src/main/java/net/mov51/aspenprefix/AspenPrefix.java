package net.mov51.aspenprefix;

import net.luckperms.api.LuckPerms;
import net.mov51.aspenprefix.commands.PrefixCommand;
import net.mov51.periderm.luckperms.AspenLuckPermsHelper;
import net.mov51.periderm.paper.chat.AspenChatHelper;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

import static net.mov51.aspenprefix.helpers.ConfigHelper.pluginPrefix;

public final class AspenPrefix extends JavaPlugin {

    //Register the LuckPerms API variable
    public static LuckPerms LPapi;
    //register plugin
    public static Logger logger;
    public static org.bukkit.plugin.Plugin plugin = null;
    public static AspenChatHelper chatHelper;
    public static AspenLuckPermsHelper metaHelper;

    @Override
    public void onEnable() {
        plugin=this;
        logger = AspenPrefix.plugin.getLogger();
        chatHelper = new AspenChatHelper(pluginPrefix);
        metaHelper = new AspenLuckPermsHelper(logger,"AspenPrefix");


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
        logger.info("You have been...");
        logger.info("PREFIXED!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
