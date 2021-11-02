package net.mov51.aspenprefix.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


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
            if(args.length == 0){
                if(hasPermission(p,prefixCommand)){
                    if(getSelected(p).isEmpty()){
                        sendChatMessage(p,"You don't have a selected prefix!");
                        sendChatMessage(p,
                                Component.text("If you'd like to select one, ")
                                .append(buildCommandComponent("Click Here!","/prefix list"
                                )));
                    }else{
                        sendChatMessage(p,"your prefix is " + getSelected(p) + "!");
                        sendChatMessage(p,
                                Component.text("If you'd like to select another one, ")
                                .append(buildCommandComponent("Click Here!","/prefix list"
                                )));
                    }
                }
            }else {
                switch (args[0]){
                    case "list":
                        //todo accept another player as an arg
                        if(hasPermission(p,prefixListCommand)){
                            sendChatMessage(p,"These are the prefixes you have!");
                            sendChatMessage(p,"Which one would you like to use?");
                            sendChatBar(p);
                            for (String prefix :  getAllPrefixes(p)) {
                                sendChatMessage(p,buildCommandComponent(prefix,"/prefix set " + prefix));
                            }
                            sendChatBar(p);
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
                                    sendChatMessage(p,"Please use the 'prefix list' command to set select the prefix you want!");
                                    break;
                                case"1":
                                    if(isPrefixDefined(args[1])){
                                        sendChatMessage(p,"You selected your " + args[1] + " prefix!");
                                        setSelected(p,args[1]);
                                    }
                                    break;
                                case"2":
                                    //todo check for set others perm
                                    // set other player
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
