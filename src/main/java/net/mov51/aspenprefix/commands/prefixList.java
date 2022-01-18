package net.mov51.aspenprefix.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static net.mov51.aspenprefix.AspenPrefix.chatHelper;
import static net.mov51.aspenprefix.AspenPrefix.logger;
import static net.mov51.aspenprefix.helpers.ConfigHelper.getPrefixValue;
import static net.mov51.aspenprefix.helpers.PermissionsHelper.Permission.*;
import static net.mov51.aspenprefix.helpers.PermissionsHelper.Permission.customPrefixOwn;
import static net.mov51.aspenprefix.helpers.PermissionsHelper.getAllPrefixes;
import static net.mov51.aspenprefix.helpers.PermissionsHelper.hasPermission;
import static net.mov51.aspenprefix.helpers.PrefixHelper.*;

public class prefixList {
    public static boolean command(Player p, String[] args){
        if(hasPermission(p,prefixListCommand)){
            if(args.length <= 2){
                //args should be a maximum of 2
                // list[1] <player>[2]
                if(args.length == 1){
                    //only the list subcommand was passed
                    // list the prefixes for the current sender
                    chatHelper.sendChat(p, Component.text()
                            .content("These are the prefixes you have!")
                            .build());
                    chatHelper.sendChat(p,Component.text()
                            .content("Which one would you like to use?")
                            .build());
                    chatHelper.sendBarMessage(p);
                    if(hasPermission(p,customPrefixOwn)){
                        if(hasCustomPrefix(p)){
                            chatHelper.sendChat(p,(chatHelper.buildRunCommandComponent("Custom", "/prefix setCustom " + p.getName())));
                        }else{
                            chatHelper.sendChat(p,(chatHelper.buildRunCommandComponent("(Custom) " + getCustomPrefix(p), "/prefix setCustom")));
                        }
                    }
                    for (String prefix :  getAllPrefixes(p)) {
                        chatHelper.sendChat(p,(chatHelper.buildRunCommandComponent(getPrefixValue(prefix),"/prefix set " + prefix)));
                    }
                    chatHelper.sendBarMessage(p);
                }else{
                    //the list subcommand and a target were passed
                    // test for a valid target and list their prefixes
                    //todo list the prefixes for the target
                    logger.info("Target " + args[1] + "selected for prefix list!");
                    if(hasPermission(p,prefixListOther)){
                        Player targetPlayer = Bukkit.getPlayer(args[1]);
                        if(targetPlayer != null){
                            //todo color the player name
                            chatHelper.sendChat(p,Component.text()
                                    .content("These are the prefixes " + targetPlayer.getName() + " has!")
                                    .build());
                            chatHelper.sendChat(p,Component.text()
                                    .content("Which one would you like " + targetPlayer.getName() + " to use?")
                                    .build());
                            chatHelper.sendBarMessage(p);
                            if(hasPermission(targetPlayer,customPrefixOwn)){
                                if(hasCustomPrefix(p)){
                                    chatHelper.sendChat(p,(chatHelper.buildRunCommandComponent("Custom", "/prefix setCustom " + targetPlayer.getName())));
                                }else{
                                    chatHelper.sendChat(p,(chatHelper.buildRunCommandComponent("(Custom) " + getCustomPrefix(p), "/prefix setCustom select " + targetPlayer.getName())));
                                }
                            }
                            for (String prefix :  getAllPrefixes(targetPlayer)) {
                                chatHelper.sendChat(p,(chatHelper.buildRunCommandComponent(prefix, "/prefix set " + prefix + " " + targetPlayer.getName())));
                            }
                            chatHelper.sendBarMessage(p);

                        }else{
                            chatHelper.sendChat(p,Component.text()
                                    .content("That player isn't online!")
                                    .build());
                        }
                    }
                }

            }
        }
        return true;
    }
}
