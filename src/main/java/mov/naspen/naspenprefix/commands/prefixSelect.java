package mov.naspen.naspenprefix.commands;

import mov.naspen.naspenprefix.NaspenPrefix;
import mov.naspen.periderm.helpers.permissions.PermItem;
import net.kyori.adventure.text.Component;
import mov.naspen.periderm.chat.PredefinedMessage;
import org.bukkit.entity.Player;

import static mov.naspen.naspenprefix.helpers.ConfigHelper.*;
import static mov.naspen.naspenprefix.helpers.PrefixHelper.*;

public class prefixSelect {

    private static final PredefinedMessage denyMessageOwn =new PredefinedMessage(Component.text(
            "You don't have permission to select a prefix!"));
    public static final PermItem prefixSelectOwn = new PermItem("prefix.select", denyMessageOwn, "select");
    public static final String customPrefixTarget = "custom-prefix";

    private static final PredefinedMessage useTheListCommand =new PredefinedMessage(Component.text()
            .content("Please use the ")
            .append(NaspenPrefix.chatHelper.buildRunCommandComponent("list", "/prefix list", true))
            .append(Component.text().content(" Command to select the prefix you want!"))
            .build());

    public static boolean command(Player p, String[] args) {
        if (NaspenPrefix.permHelper.hasPermission(p, prefixSelectOwn)) {
            // set[1] <prefix>[2]
            if (args.length <= 2) {
                if (args.length == 1) {
                    //only the set subcommand was passed
                    // check for permission and send help message
                    NaspenPrefix.chatHelper.sendChat(p,useTheListCommand);

                } else if (args.length == 2) {
                    //the set subcommand and the desired prefix were passed
                    // check for permission and set the current senders prefix
                    if(args[1].equalsIgnoreCase("none")){
                        clearSelectedPrefix(p);
                        NaspenPrefix.chatHelper.sendChat(p, Component.text()
                                .content("You've deselected your prefix and will automatically use your highest prefix!")
                                .build());
                    }else if(args[1].equalsIgnoreCase(customPrefixTarget)){
                        setSelectedPrefix(p,customPrefix.getKey());
                        NaspenPrefix.chatHelper.sendChat(p, Component.text()
                                .content("You selected your Custom prefix that looks like this: ")
                                .append(getCustomPrefix(p))
                                .build());
                    }else if(args[1].equalsIgnoreCase(defaultPrefixTarget)){
                        setSelectedPrefix(p,defaultPrefixTarget);
                        NaspenPrefix.chatHelper.sendChat(p, Component.text()
                                .content("You selected the Default prefix because you don't have access to any other prefixes. It looks like this: ")
                                .append(defaultPlayerPrefix)
                                .build());
                    } else{
                        if (isSelectedPrefixDefined(args[1])) {
                            Component selected = setSelectedPrefix(p, args[1]);
                            NaspenPrefix.chatHelper.sendChat(p, Component.text()
                                    .content("You selected your '")
                                    .append(selected)
                                    .append(Component.text("' prefix!"))
                                    .build());
                        } else {
                            NaspenPrefix.chatHelper.sendChat(p, Component.text()
                                    .content("That prefix doesn't exist!")
                                    .build());
                            NaspenPrefix.chatHelper.sendChat(p,useTheListCommand);
                        }
                    }
                }
                //todo command help
            }

        }
        return true;
    }
}
