package net.mov51.aspenprefix.commands;

import net.kyori.adventure.text.Component;
import net.mov51.periderm.paper.chat.PredefinedMessage;
import net.mov51.periderm.paper.permissions.Perm;
import org.bukkit.entity.Player;

import static net.mov51.aspenprefix.AspenPrefix.*;
import static net.mov51.aspenprefix.helpers.ConfigHelper.*;
import static net.mov51.aspenprefix.helpers.PrefixHelper.*;

public class prefixSelect {

    private static final PredefinedMessage denyMessageOwn =new PredefinedMessage(Component.text(
            "You don't have permission to select a prefix!"));
    public static final Perm prefixSelectOwn = new Perm("prefixSelect", denyMessageOwn, "select");
    public static final String customPrefixTarget = "custom-prefix";

//    private static final PredefinedMessage denyMessageOther =new PredefinedMessage(Component.text(
//            "You don't have permission to select a prefix for others!"));
//    private static final Perm prefixSelectOthers = new Perm("prefixSelectOthers", denyMessageOther);

    private static final PredefinedMessage useTheListCommand =new PredefinedMessage(Component.text()
            .content("Please use the ")
            .append(chatHelper.buildRunCommandComponent("list", "/prefix list", true))
            .append(Component.text().content(" Command to select the prefix you want!"))
            .build());

    public static boolean command(Player p, String[] args) {
        if (permHelper.hasPermission(p, prefixSelectOwn)) {
            // set[1] <prefix>[2]
            if (args.length <= 2) {
                if (args.length == 1) {
                    //only the set subcommand was passed
                    // check for permission and send help message
                    chatHelper.sendChat(p,useTheListCommand);

                } else if (args.length == 2) {
                    //the set subcommand and the desired prefix were passed
                    // check for permission and set the current senders prefix
                    if(args[1].equalsIgnoreCase(defaultPrefixTarget)){
                        setSelectedPrefix(p,customPrefix.getKey());
                        chatHelper.sendChat(p, Component.text()
                                .content("You selected your Custom prefix that looks like this: ")
                                .append(getPrefixAsComponent(getCustomPrefix(p)))
                                .build());
                    }else if(args[1].equalsIgnoreCase(customPrefixTarget)){
                        setSelectedPrefix(p,defaultPlayerPrefix);
                        chatHelper.sendChat(p, Component.text()
                                .content("You selected the Default prefix because you don't have access to any other prefixes. It looks like this: ")
                                .append(getPrefixAsComponent(defaultPlayerPrefix))
                                .build());
                    } else{
                        if (isPrefixDefined(args[1])) {
                            setSelectedPrefix(p, args[1]);
                            chatHelper.sendChat(p, Component.text()
                                    .content("You selected your '")
                                    .append(getPrefixAsComponent(getPrefixValue(args[1])))
                                    .append(Component.text("' prefix!"))
                                    .build());
                        } else {
                            chatHelper.sendChat(p, Component.text()
                                    .content("That prefix doesn't exist!")
                                    .build());
                            chatHelper.sendChat(p,useTheListCommand);
                        }
                    }
                }
                //todo command help
            }

        }
        return true;
    }
}
