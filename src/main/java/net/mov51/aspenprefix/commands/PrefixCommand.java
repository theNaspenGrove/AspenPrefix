package net.mov51.aspenprefix.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


import static net.mov51.aspenprefix.AspenPrefix.logger;
import static net.mov51.aspenprefix.AspenPrefix.playerResponseListener;
import static net.mov51.aspenprefix.helpers.PermissionsHelper.Permission.*;
import static net.mov51.aspenprefix.helpers.PermissionsHelper.getAllPrefixes;
import static net.mov51.aspenprefix.helpers.PermissionsHelper.hasPermission;
import static net.mov51.aspenprefix.helpers.ConfigHelper.isPrefixDefined;
import static net.mov51.aspenprefix.helpers.PrefixHelper.*;
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
                    if(getSelectedPrefix(p).isEmpty()){
                        sendChatMessage(p,Component.text().content("You don't have a selected prefix!").build());
                        sendChatMessage(p, Component.text()
                                .content("If you'd like to select one, ")
                                .append(buildCommandComponent("Click Here!","/prefix list"))
                                .build());
                    }else{
                        sendChatMessage(p,Component.text()
                                .content("your prefix is " + getSelectedPrefix(p) + "!")
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
                        //prefix list[0] <player>[1]

                        if(hasPermission(p,prefixListCommand)){
                            if(args.length <= 2){
                                //args should be a maximum of 2
                                // list[1] <player>[2]
                                if(args.length == 1){
                                    //only the list subcommand was passed
                                    // list the prefixes for the current sender
                                    sendChatMessage(p,Component.text()
                                            .content("These are the prefixes you have!")
                                            .build());
                                    sendChatMessage(p,Component.text()
                                            .content("Which one would you like to use?")
                                            .build());
                                    sendBarMessage(p);
                                    if(hasPermission(p,customPrefixOwn)){
                                        sendChatMessage(p,(buildCommandComponent("Custom", "/prefix setCustom")));
                                    }
                                    for (String prefix :  getAllPrefixes(p)) {
                                        sendChatMessage(p,(buildCommandComponent(prefix,"/prefix set " + prefix)));
                                    }
                                    sendBarMessage(p);
                                }else{
                                    //the list subcommand and a target were passed
                                    // test for a valid target and list their prefixes
                                    //todo list the prefixes for the target
                                    logger.info("Target " + args[1] + "selected for prefix list!");
                                    if(hasPermission(p,prefixListOther)){
                                        Player targetPlayer = Bukkit.getPlayer(args[1]);
                                        if(targetPlayer != null){
                                            //todo color the player name
                                            sendChatMessage(p,Component.text()
                                                    .content("These are the prefixes " + targetPlayer.getName() + " has!")
                                                    .build());
                                            sendChatMessage(p,Component.text()
                                                    .content("Which one would you like " + targetPlayer.getName() + " to use?")
                                                    .build());
                                            sendBarMessage(p);
                                            if(hasPermission(targetPlayer,customPrefixOwn)){
                                                sendChatMessage(p,(buildCommandComponent("Custom", "/prefix setCustom" + " " + targetPlayer.getName())));
                                            }
                                            for (String prefix :  getAllPrefixes(targetPlayer)) {
                                                sendChatMessage(p,(buildCommandComponent(prefix, "/prefix set " + prefix + " " + targetPlayer.getName())));
                                            }
                                            sendBarMessage(p);

                                        }else{
                                            sendChatMessage(p,Component.text()
                                                    .content("That player isn't online!")
                                                    .build());
                                        }
                                    }
                                }

                            }
                        }
                        break;
                    case "set":
                        //prefix set[0] <prefix>[1] <player>[2]

                        if(hasPermission(p,prefixSetCommand)){
                            //args should be a maximum of 3
                            // set[1] <prefix>[2] <player>[3]
                            if(args.length <= 3){
                                if(args.length == 1){
                                    //only the set subcommand was passed
                                    // check for permission and send help message
                                    sendChatMessage(p,Component.text()
                                            .content("Please use the 'prefix list' command to set select the prefix you want!")
                                            .build());

                                } else if(args.length == 2){
                                    //the set subcommand and the desired prefix were passed
                                    // check for permission and set the current senders prefix
                                    if(isPrefixDefined(args[1])){
                                        setSelectedPrefix(p,args[1]);
                                        sendChatMessage(p,Component.text()
                                                .content("You selected your " + args[1] + " prefix!")
                                                .build());
                                    }else{
                                        sendChatMessage(p,Component.text()
                                                .content("That prefix doesn't exist!!")
                                                .build());
                                        sendChatMessage(p,Component.text()
                                                .content("Please use the 'prefix list' command to set select the prefix you want!")
                                                .build());
                                    }

                                } else{
                                    //the set command, the desired prefix, and a target were passed
                                    // check for permission, a valid target, and set the targets prefix
                                    //todo check for set others perm
                                    // set other player
                                    logger.info("Target " + args[2] + "selected for prefix set!");
                                    if(hasPermission(p,prefixSetOther)){
                                        Player targetPlayer = Bukkit.getPlayer(args[2]);
                                        if(targetPlayer != null){
                                            if(isPrefixDefined(args[1])){
                                                setSelectedPrefix(targetPlayer,args[1]);
                                                sendChatMessage(p,Component.text()
                                                        .content("You set the prefix for " + targetPlayer.getName() + " to " + args[1] + "!")
                                                        .build());
                                                sendChatMessage(targetPlayer,Component.text()
                                                        .content("Your prefix has been set to " + args[1] + "!")
                                                        .build());
                                            }else{
                                                sendChatMessage(p,Component.text()
                                                        .content("That prefix doesn't exist!!")
                                                        .build());
                                                sendChatMessage(p,Component.text()
                                                        .content("Please use the 'prefix list <player>' command to set select the prefix you want!")
                                                        .build());
                                            }
                                        }else{
                                            sendChatMessage(p,Component.text()
                                                    .content("That player isn't online!")
                                                    .build());
                                        }
                                    }

                                }
                            } else{
                                //too many arguments were passed
                                // provide a help message
                                //todo fix error message
                                sendChatMessage(p,Component.text()
                                        .content("Too many arguments! Please only provide a player, and the prefix you'd like to switch to!")
                                        .build());
                            }

                        }
                        break;
                    case"setCustom":
                        //prefix setCustom[0] <player>[1]

                        if(args.length <= 2){
                            //args should be a maximum of 2
                            // setCustom[1] <player>[2]

                            if(args.length == 1){
                                //the setCustom subcommand was passed
                                // check for permission and send setCustom message

                                if(hasPermission(p,prefixSetCustomCommand)){
                                    //todo ask if they'd like to use their current custom prefix or set a new one?
                                    if(getCustomPrefix(p).isEmpty()){
                                        sendChatMessage(p,Component.text()
                                                .content("You don't have a Custom Prefix defined!")
                                                .build());
                                        sendChatMessage(p,Component.text().content("If you'd like to define one, ")
                                                        .append(buildCommandComponent("Click Here", "/prefix setCustom")
                                                        ).build());
                                    }
                                    playerResponseListener.watchPlayer(p);
                                }
                            }else{
                                //the setCustom subcommand and a target were passed
                                // test for a valid target and run the setCustom message with the target as the operator
                                //todo check for set others perm
                                // set other player

                                logger.info("Target " + args[1] + "selected for prefix setCustom!");
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
