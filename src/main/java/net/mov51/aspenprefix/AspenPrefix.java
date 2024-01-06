package net.mov51.aspenprefix;

import net.kyori.adventure.text.Component;
import net.luckperms.api.LuckPerms;
import net.mov51.aspenprefix.commands.PrefixCommand;
import net.mov51.aspenprefix.commands.PrefixTabComplete;
import net.mov51.aspenprefix.listeners.playerLogIn;
import mov.naspen.periderm.helpers.luckPerms.AspenLuckPermsHelper;
import mov.naspen.periderm.chat.AspenChatHelper;
import mov.naspen.periderm.chat.PredefinedMessage;
import mov.naspen.periderm.helpers.permissions.PermissionHelper;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

import static net.mov51.aspenprefix.helpers.ConfigHelper.loadPrefixes;
import static net.mov51.aspenprefix.helpers.ConfigHelper.pluginPrefix;

public final class AspenPrefix extends JavaPlugin {

    //register plugin
    public static Logger logger;
    public static org.bukkit.plugin.Plugin plugin = null;
    public static AspenChatHelper chatHelper;
    public static AspenLuckPermsHelper metaHelper;
    public static PermissionHelper permHelper;

    @Override
    public void onEnable() {
        plugin=this;
        //get logger
        logger = AspenPrefix.plugin.getLogger();
        //create periderm chat helper
        chatHelper = new AspenChatHelper(pluginPrefix);
        //create periderm perms helper
        permHelper = new PermissionHelper("AspenPrefix.",
                new PredefinedMessage(Component.text("You don't have permission to run that command!")),chatHelper);

        //create default config file
        plugin.saveDefaultConfig();


        //get the LuckPerms API
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            //Register the LuckPerms metaHelper variable
            metaHelper = new AspenLuckPermsHelper(logger,"AspenPrefix");
        }

        //create PlaceHolder API expansion
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new AspenPrefixPlaceholders(this).register();
            logger.info("Placeholders holding!");
        }

        //create command execute
        Objects.requireNonNull(this.getCommand("prefix")).setExecutor(new PrefixCommand());
        //create command tab completer
        Objects.requireNonNull(getCommand("prefix")).setTabCompleter(new PrefixTabComplete());
        //register player log in event
        getServer().getPluginManager().registerEvents(new playerLogIn(), this);
        //load prefixes
        loadPrefixes();
        //create log message
        logger.info("You have been...");
        logger.info("PREFIXED!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
