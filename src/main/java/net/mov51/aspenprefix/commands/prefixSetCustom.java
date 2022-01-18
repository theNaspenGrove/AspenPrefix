package net.mov51.aspenprefix.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import static net.mov51.aspenprefix.AspenPrefix.*;
import static net.mov51.aspenprefix.helpers.PermissionsHelper.Permission.prefixSetCustomCommand;
import static net.mov51.aspenprefix.helpers.PermissionsHelper.hasPermission;
import static net.mov51.aspenprefix.helpers.PrefixHelper.*;

public class prefixSetCustom {
    public static boolean command(Player p, String[] args){
        if(args.length <= 3){
            //args should be a maximum of 2
            // setCustom[1] <player>[2]

            if(args.length == 1){
                //the setCustom subcommand was passed
                // check for permission and send setCustom message

                if(hasPermission(p,prefixSetCustomCommand)){
                    //todo ask if they'd like to use their current custom prefix or set a new one?
                    if(hasCustomPrefix(p)){
                        chatHelper.sendChat(p, Component.text()
                                .content("You don't have a Custom Prefix defined!")
                                .build());
                        chatHelper.sendChat(p,
                                Component.text()
                                        .content("If you'd like to make one")
                                        .append(chatHelper.buildSuggestCommandComponent("Click Here!","/prefix setCustom new"))
                                        .build());
                    }else{
                        chatHelper.sendChat(p, Component.text()
                                .content("Your custom prefix is ")
                                .append(getPrefixAsComponent(getCustomPrefix(p)))
                                .build());
                        chatHelper.sendChat(p,
                                Component.text()
                                        .content("If you'd like to use that one, ")
                                        .append(chatHelper.buildRunCommandComponent("Click Here!","/prefix setCustom select"))
                                        .build());
                        chatHelper.sendChat(p,
                                Component.text()
                                        .content("If you'd like to make a new one, ")
                                        .append(chatHelper.buildSuggestCommandComponent("Click Here!","/prefix setCustom new"))
                                        .build());
                    }
                }
            }else{
                //the setCustom subcommand and a target were passed
                // test for a valid target and run the setCustom message with the target as the operator
                //todo check for set others perm
                // set other player
                switch (args[1]){
                    case "new":
                        if(args.length == 3){
                            setCustomPrefix(p,args[2]);
                            setSelectedPrefix(p,customPrefix.getKey());
                            chatHelper.sendChat(p,"Your prefix has been set to " + getCustomPrefix(p));
                        }else{
                            chatHelper.sendChat(p,"Please specify a new Custom Prefix!");
                        }
                        return true;
                    case "select":
                        setSelectedPrefix(p,customPrefix.getKey());
                        chatHelper.sendChat(p,"You've selected your custom prefix, " + getCustomPrefix(p) + "!");
                        return true;
                }
                logger.info("Target " + args[1] + "selected for prefix setCustom!");
            }

        }
        return true;
    }
}
