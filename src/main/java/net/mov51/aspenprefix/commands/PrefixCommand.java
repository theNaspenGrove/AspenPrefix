package net.mov51.aspenprefix.commands;

import net.mov51.aspenprefix.AspenPrefix;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static net.mov51.aspenprefix.AspenPrefix.logger;
import static net.mov51.aspenprefix.helpers.PermissionsHelper.Permission.*;
import static net.mov51.aspenprefix.helpers.PermissionsHelper.getAllPrefixes;
import static net.mov51.aspenprefix.helpers.PermissionsHelper.hasPermission;
import static net.mov51.aspenprefix.helpers.PrefixHelper.getSelected;
import static net.mov51.aspenprefix.helpers.PrefixHelper.setSelected;
import static net.mov51.aspenprefix.helpers.ConfigHelper.isPrefixDefined;
import static net.mov51.aspenprefix.helpers.messageHelper.*;

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
                    sendChatMessage(p,"your prefix is " + getSelected(p) + "!");
                }
            }else {
                switch (args[0]){
                    case "list":
                        //todo accept another player as an arg
                        if(hasPermission(p,prefixListCommand)){
                            sendChatMessage(p,"These are the prefixes you have!");
                            for (String key :  Objects.requireNonNull(c.getConfigurationSection("Prefixes")).getKeys(false)) {
                                sendColoredChatMessage(p,key);
                            }
                        }
                        break;
                    case "set":
                        //todo accept another player as an arg
                        //prefix [0]set [1]Staff [2]mov51
                        //prefix #1 set #2 Staff # 3 mov51

                        if(hasPermission(p,prefixSetCommand)){
                            logger.info("Prefix args.length is " + args.length);
                            switch (String.valueOf(args.length - 1)){
                                case"0":
                                    //no args, show command help!
                                    //todo ask user which prefix they'd like out of the ones they have permission to use
                                    sendChatMessage(p,"You have permission to set your prefix!");
                                    sendChatMessage(p,"Which one would you like to use?");
                                    for (String prefix :  getAllPrefixes(p)) {
                                        sendChatMessage(p,buildCommandComponent(prefix,"/prefix set " + prefix));
                                    }

                                    break;
                                case"1":
                                    //todo check if that prefix is defined in the config!
                                    //todo Get prefix value from config
                                    if(isPrefixDefined(args[1])){
                                        sendChatMessage(p,"You selected your " + args[1] + " prefix!");
                                        setSelected(p,args[1]);
                                    }
                                    break;
                                case"2":
                                    logger.info("Player " + args[2] + "selected!");
                                    break;
                                default:
                                    //todo fix error message
                                    sendChatMessage(p,"Too many arguments! Please only provide a player, and the prefix you'd like to switch to!");
                            }
                        }
                        break;
                }
            }
            return true;
        }
        return false;
    }
}
