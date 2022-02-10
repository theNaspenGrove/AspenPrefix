package net.mov51.aspenprefix.commands;

import net.kyori.adventure.text.Component;
import net.mov51.periderm.paper.chat.PredefinedMessage;
import net.mov51.periderm.paper.permissions.Perm;
import org.bukkit.entity.Player;

import static net.mov51.aspenprefix.AspenPrefix.*;
import static net.mov51.aspenprefix.commands.prefixSetCustom.prefixSetCustomOwn;
import static net.mov51.aspenprefix.helpers.ConfigHelper.getPrefixValue;
import static net.mov51.aspenprefix.helpers.PrefixHelper.*;

public class prefixList {

    private static final PredefinedMessage denyMessageOwn =new PredefinedMessage(Component.text(
            "You don't have permission to list your prefixes!!"));
    public static final Perm prefixListOwn = new Perm("prefixList", denyMessageOwn, "list");

//    private static final PredefinedMessage denyMessageOther =new PredefinedMessage(Component.text(
//            "You don't have permission to list the prefixes of another player!"));
//    private static final Perm prefixListOther = new Perm("prefixListOther", denyMessageOther);

    public static boolean command(Player p, String[] args){
        if(permHelper.hasPermission(p,prefixListOwn)){
            if(args.length == 1){
                //only the list subcommand was passed
                //list the prefixes for the current sender
                chatHelper.sendChat(p, Component.text()
                        .content("These are the prefixes you have!")
                        .build());
                listToSelect(p);
                chatHelper.sendChat(p,Component.text()
                        .content("Which one would you like to use?")
                        .build());

            }else{
                //todo command help
            }

        }
        return true;
    }

    private static void listToSelect(Player p){
        chatHelper.sendBarMessage(p);
            if(permHelper.hasPermission(p,prefixSetCustomOwn,true)){
                if(hasCustomPrefix(p)){
                    chatHelper.sendChat(p,(chatHelper.buildRunCommandComponent("(Custom) " + getCustomPrefix(p), "/prefix setCustom",false, null)));
                }else{
                    chatHelper.sendChat(p,(chatHelper.buildRunCommandComponent("(Custom)", "/prefix setCustom",false, null)));
                }
            }
        for (String prefix :  getPlayerPrefixes(p)) {
            chatHelper.sendChat(p,(chatHelper.buildRunCommandComponent(getPrefixValue(prefix),"/prefix select " + prefix,false, null)));
        }
        chatHelper.sendBarMessage(p);
    }
}
