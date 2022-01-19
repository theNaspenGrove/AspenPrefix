package net.mov51.aspenprefix.commands;

import net.kyori.adventure.text.Component;
import net.mov51.periderm.paper.chat.PredefinedMessage;
import net.mov51.periderm.paper.permissions.Perm;
import org.bukkit.entity.Player;

import static net.mov51.aspenprefix.AspenPrefix.*;
import static net.mov51.aspenprefix.helpers.PrefixHelper.*;

public class prefixSetCustom {

    private static final PredefinedMessage denyMessageOwn =new PredefinedMessage(Component.text("You don't have permission to set a custom prefix!"));
    private static final Perm prefixSetCustomOwn = new Perm("prefixSetCustom", denyMessageOwn);

//    private static final PredefinedMessage denyMessageOther =new PredefinedMessage(Component.text("You don't have permission to set a custom prefix for others!"));
//    private static final Perm prefixSetCustomOther = new Perm("prefixSetCustomOther", denyMessageOther);

    public static boolean command(Player p, String[] args){
        if(args.length <= 3){
            //args should be a maximum of 2
            // setCustom[1] <player>[2]

            if(args.length == 1){
                //the setCustom subcommand was passed
                // check for permission and send setCustom message

                if(permHelper.hasPermission(p,prefixSetCustomOwn)){
                    //todo ask if they'd like to use their current custom prefix or set a new one?
                    if(hasCustomPrefix(p)){
                        chatHelper.sendChat(p, Component.text()
                                .content("You don't have a Custom Prefix defined!")
                                .build());
                        chatHelper.sendChat(p,
                                Component.text()
                                        .content("If you'd like to make one")
                                        .append(chatHelper.buildSuggestCommandComponent("Click Here!","/prefix setCustom new", true))
                                        .build());
                    }else{
                        chatHelper.sendChat(p, Component.text()
                                .content("Your custom prefix is ")
                                .append(getPrefixAsComponent(getCustomPrefix(p)))
                                .build());
                        chatHelper.sendChat(p,
                                Component.text()
                                        .content("If you'd like to use that one, ")
                                        .append(chatHelper.buildRunCommandComponent("Click Here!","/prefix setCustom select", true))
                                        .build());
                        chatHelper.sendChat(p,
                                Component.text()
                                        .content("If you'd like to make a new one, ")
                                        .append(chatHelper.buildSuggestCommandComponent("Click Here!","/prefix setCustom new", true))
                                        .build());
                    }
                }
            }else{
                switch (args[1]){
                    case "new":
                        //creating a new prefix and overriding the old one
                        if(args.length == 3){
                            setCustomPrefix(p,args[2]);
                            setSelectedPrefix(p,customPrefix.getKey());
                            chatHelper.sendChat(p,"Your prefix has been set to " + getCustomPrefix(p));
                        }else{
                            chatHelper.sendChat(p,"Please specify a new Custom Prefix!");
                        }
                        return true;
                    case "select":
                        //selecting the current custom prefix
                        setSelectedPrefix(p,customPrefix.getKey());
                        chatHelper.sendChat(p,"You've selected your custom prefix, " + getCustomPrefix(p) + "!");
                        return true;
                    default:
                        //todo command help
                        break;
                }
            }
        }
        return true;
    }
}
