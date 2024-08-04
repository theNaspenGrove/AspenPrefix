package mov.naspen.naspenprefix.commands;

import mov.naspen.naspenprefix.NaspenPrefix;
import mov.naspen.naspenprefix.helpers.PrefixHelper;
import mov.naspen.periderm.helpers.permissions.PermItem;
import net.kyori.adventure.text.Component;
import mov.naspen.periderm.chat.PredefinedMessage;
import org.bukkit.entity.Player;

import static mov.naspen.naspenprefix.helpers.ConfigHelper.getPrefixValue;

public class prefixList {

    private static final PredefinedMessage denyMessageOwn =new PredefinedMessage(Component.text(
            "You don't have permission to list your prefixes!!"));
    public static final PermItem prefixListOwn = new PermItem("prefix.list", denyMessageOwn, "list");

    public static boolean command(Player p, String[] args){
        if(NaspenPrefix.permHelper.hasPermission(p,prefixListOwn)){
            if(args.length == 1){
                //only the list subcommand was passed
                //list the prefixes for the current sender
                NaspenPrefix.chatHelper.sendChat(p, Component.text()
                        .content("These are the prefixes you have!")
                        .build());
                listToSelect(p);
                NaspenPrefix.chatHelper.sendChat(p,Component.text()
                        .content("Which one would you like to use?")
                        .build());

            }else{
                //todo command help
            }

        }
        return true;
    }

    private static void listToSelect(Player p){
        NaspenPrefix.chatHelper.sendBarMessage(p);
            if(NaspenPrefix.permHelper.hasPermission(p, prefixSetCustom.prefixSetCustomOwn,true)){
                if(PrefixHelper.hasCustomPrefix(p)){
                    NaspenPrefix.chatHelper.sendChat(p,(NaspenPrefix.chatHelper.buildRunCommandComponent("(Custom) " + PrefixHelper.getCustomPrefix(p), "/prefix setCustom",false, null)));
                }else{
                    NaspenPrefix.chatHelper.sendChat(p,(NaspenPrefix.chatHelper.buildRunCommandComponent("(Custom)", "/prefix setCustom",false, null)));
                }
            }
        for (String prefix :  PrefixHelper.getPlayerPrefixes(p)) {
            NaspenPrefix.chatHelper.sendChat(p,(NaspenPrefix.chatHelper.buildRunCommandComponent(getPrefixValue(prefix),"/prefix select " + prefix,false, null)));
        }
        NaspenPrefix.chatHelper.sendBarMessage(p);
    }
}
