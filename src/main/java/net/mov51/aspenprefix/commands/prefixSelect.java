package net.mov51.aspenprefix.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static net.mov51.aspenprefix.AspenPrefix.chatHelper;
import static net.mov51.aspenprefix.AspenPrefix.logger;
import static net.mov51.aspenprefix.helpers.ConfigHelper.isPrefixDefined;
import static net.mov51.aspenprefix.helpers.PermissionsHelper.Permission.prefixSelectCommand;
import static net.mov51.aspenprefix.helpers.PermissionsHelper.Permission.prefixSetOther;
import static net.mov51.aspenprefix.helpers.PermissionsHelper.hasPermission;
import static net.mov51.aspenprefix.helpers.PrefixHelper.setSelectedPrefix;

public class prefixSelect {
    public static boolean command(Player p, String[] args){
        if(hasPermission(p, prefixSelectCommand)){
            //args should be a maximum of 3
            // set[1] <prefix>[2] <player>[3]
            if(args.length <= 3){
                if(args.length == 1){
                    //only the set subcommand was passed
                    // check for permission and send help message
                    chatHelper.sendChat(p,
                            Component.text()
                                    .content("Please use the ")
                                    .append(chatHelper.buildRunCommandComponent("list","/prefix list"))
                                    .append(Component.text().content(" Command to select the prefix you want!"))
                                    .build());

                } else if(args.length == 2){
                    //the set subcommand and the desired prefix were passed
                    // check for permission and set the current senders prefix
                    if(isPrefixDefined(args[1])){
                        setSelectedPrefix(p,args[1]);
                        chatHelper.sendChat(p,Component.text()
                                .content("You selected your " + args[1] + " prefix!")
                                .build());
                    }else{
                        chatHelper.sendChat(p,Component.text()
                                .content("That prefix doesn't exist!!")
                                .build());
                        chatHelper.sendChat(p,
                                Component.text()
                                        .content("Please use the ")
                                        .append(chatHelper.buildRunCommandComponent("list","/prefix list"))
                                        .append(Component.text().content(" Command to select the prefix you want!"))
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
                                chatHelper.sendChat(p,Component.text()
                                        .content("You set the prefix for " + targetPlayer.getName() + " to " + args[1] + "!")
                                        .build());
                                chatHelper.sendChat(targetPlayer,Component.text()
                                        .content("Your prefix has been set to " + args[1] + "!")
                                        .build());
                            }else{
                                chatHelper.sendChat(p,Component.text()
                                        .content("That prefix doesn't exist!!")
                                        .build());
                                chatHelper.sendChat(p,
                                        Component.text()
                                                .content("Please use the ")
                                                .append(chatHelper.buildRunCommandComponent("list","/prefix list"))
                                                .append(Component.text().content(" Command to select the prefix you want!"))
                                                .build());
                            }
                        }else{
                            chatHelper.sendChat(p,Component.text()
                                    .content("That player isn't online!")
                                    .build());
                        }
                    }

                }
            } else{
                //too many arguments were passed
                // provide a help message
                //todo fix error message
                chatHelper.sendChat(p,Component.text()
                        .content("Too many arguments! Please only provide a player, and the prefix you'd like to switch to!")
                        .build());
            }

        }
        return true;
    }
}
