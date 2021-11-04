package net.mov51.aspenprefix.commands;

import net.kyori.adventure.text.Component;
import org.apache.commons.lang.StringUtils;
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
                        sendChatMessage(p,Component.text().content("You don't have a selected prefix!").build());
                        sendChatMessage(p, Component.text()
                                .content("If you'd like to select one, ")
                                .append(buildCommandComponent("Click Here!","/prefix list"))
                                .build());
                    }else{
                        sendChatMessage(p,Component.text()
                                .content("your prefix is " + getSelected(p) + "!")
                                .build());
                        sendChatMessage(p, Component.text()
                                .content("If you'd like to select another one, ")
                                .append(buildCommandComponent("Click Here!","/prefix list"))
                                .build());
                    }
                }
            }else {
                switch (args[0]){
                    case "list":
                        //todo accept another player as an arg
                        if(hasPermission(p,prefixListCommand)){
                            sendChatMessage(p,Component.text()
                                    .content("These are the prefixes you have!")
                                    .build());
                            sendChatMessage(p,Component.text()
                                    .content("Which one would you like to use?")
                                    .build());
                            sendBarMessage(p);
                            for (String prefix :  getAllPrefixes(p)) {
                                sendChatMessage(p,(buildCommandComponent(prefix, StringUtils.center("/prefix set " + prefix,53))));
                            }
                            sendBarMessage(p);
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
                                    sendChatMessage(p,Component.text()
                                            .content("Please use the 'prefix list' command to set select the prefix you want!")
                                            .build());
                                    break;
                                case"1":
                                    if(isPrefixDefined(args[1])){
                                        sendChatMessage(p,Component.text()
                                                .content("You selected your " + args[1] + " prefix!")
                                                .build());
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
                                    sendChatMessage(p,Component.text()
                                            .content("Too many arguments! Please only provide a player, and the prefix you'd like to switch to!")
                                            .build());
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
