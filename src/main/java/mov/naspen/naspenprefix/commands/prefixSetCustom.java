package mov.naspen.naspenprefix.commands;

import mov.naspen.naspenprefix.NaspenPrefix;
import mov.naspen.naspenprefix.helpers.PrefixHelper;
import mov.naspen.periderm.helpers.permissions.PermItem;
import net.kyori.adventure.text.Component;
import mov.naspen.periderm.chat.PredefinedMessage;
import org.bukkit.entity.Player;

public class prefixSetCustom {

    private static final PredefinedMessage denyMessageOwn =new PredefinedMessage(Component.text("You don't have permission to set a custom prefix!"));
    public static final PermItem prefixSetCustomOwn = new PermItem("prefixSetCustom", denyMessageOwn,"setCustom");

    public static boolean command(Player p, String[] args){
        if(args.length <= 3){
            //args should be a maximum of 2
            // setCustom[1] <player>[2]

            if(args.length == 1){
                //the setCustom subcommand was passed
                // check for permission and send setCustom message

                if(NaspenPrefix.permHelper.hasPermission(p,prefixSetCustomOwn)){
                    if(PrefixHelper.hasCustomPrefix(p)){
                        NaspenPrefix.chatHelper.sendChat(p, Component.text()
                                .content("Your custom prefix is: ")
                                .append(PrefixHelper.getPrefixAsComponent(PrefixHelper.getCustomPrefix(p)))
                                .build());
                        NaspenPrefix.chatHelper.sendChat(p,
                                Component.text()
                                        .content("If you'd like to use that one, ")
                                        .append(NaspenPrefix.chatHelper.buildRunCommandComponent("Click Here!","/prefix setCustom select", true))
                                        .build());
                        NaspenPrefix.chatHelper.sendChat(p,
                                Component.text()
                                        .content("If you'd like to make a new one, ")
                                        .append(NaspenPrefix.chatHelper.buildSuggestCommandComponent("Click Here","/prefix setCustom new ", true))
                                        .append(Component.text(" and add the prefix you want to the end of the command!"))
                                        .build());
                    }else{
                        NaspenPrefix.chatHelper.sendChat(p, Component.text()
                                .content("You don't have a Custom Prefix defined!")
                                .build());
                        NaspenPrefix.chatHelper.sendChat(p,
                                Component.text()
                                        .content("If you'd like to make one, ")
                                        .append(NaspenPrefix.chatHelper.buildSuggestCommandComponent("Click Here!","/prefix setCustom new", true))
                                        .build());
                    }
                }
            }else{
                switch (args[1]){
                    case "new":
                        //creating a new prefix and overriding the old one
                        if(args.length == 3){
                            PrefixHelper.setCustomPrefix(p,args[2]);
                            PrefixHelper.setSelectedPrefix(p, PrefixHelper.customPrefix.getKey());
                            NaspenPrefix.chatHelper.sendChat(p,"Your prefix has been set to: " + PrefixHelper.getCustomPrefix(p));
                        }else{
                            NaspenPrefix.chatHelper.sendChat(p,"Please specify a new Custom Prefix!");
                        }
                        return true;
                    case "select":
                        //selecting the current custom prefix
                        PrefixHelper.setSelectedPrefix(p, PrefixHelper.customPrefix.getKey());
                        NaspenPrefix.chatHelper.sendChat(p,"You've selected your custom prefix: " + PrefixHelper.getCustomPrefix(p));
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
