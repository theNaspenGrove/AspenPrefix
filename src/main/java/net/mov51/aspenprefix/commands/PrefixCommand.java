package net.mov51.aspenprefix.commands;

import net.mov51.aspenprefix.AspenPrefix;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static net.mov51.aspenprefix.helpers.PermissionsHelper.Permission.*;
import static net.mov51.aspenprefix.helpers.PermissionsHelper.getAllPrefixes;
import static net.mov51.aspenprefix.helpers.PermissionsHelper.hasPermission;
import static net.mov51.aspenprefix.helpers.messageHelper.sendChatMessage;
import static net.mov51.aspenprefix.helpers.messageHelper.sendColoredChatMessage;

public class PrefixCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        //todo handle prefix command
        //todo select prefix via chat
        // doesn't use extra commands, it's ask and answer
        // maybe have a sub command system for faster selection

        if (sender instanceof Player) {
            Player p = (Player) sender;
            FileConfiguration c = AspenPrefix.plugin.getConfig();
            if(args.length == 0){
                if(hasPermission(p,prefixCommand)){
                    sendChatMessage(p,"your prefix is " + "none" + "!");
                }
            }else if(args.length == 1){
                switch (args[0]){
                    case "list":
                        //todo accept another player as an arg
                        if(hasPermission(p,prefixListCommand)){
                            for (String key :  Objects.requireNonNull(c.getConfigurationSection("Prefixes")).getKeys(false)) {
                                sendChatMessage(p,"These are the prefixes you have!");
                                sendColoredChatMessage(p,key);
                                sendColoredChatMessage(p,c.getString("Prefixes." + key));
                            }
                        }
                        break;
                    case "set":
                        //todo accept another player as an arg
                        if(hasPermission(p,prefixSetCommand)){
                            //todo ask user which prefix they'd like out of the ones they have permission to use
                            sendChatMessage(p,"You have permission to set your prefix!");
                            sendColoredChatMessage(p,getAllPrefixes(p));
                        }
                        break;
                }
            }
            return true;
        }
        return false;
    }
}
