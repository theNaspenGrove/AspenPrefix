package net.mov51.aspenprefix.commands;

import mov.naspen.periderm.helpers.permissions.PermItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import mov.naspen.periderm.chat.PredefinedMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


import static net.mov51.aspenprefix.AspenPrefix.*;
import static net.mov51.aspenprefix.helpers.PrefixHelper.*;

public class PrefixCommand implements CommandExecutor {

    private final PredefinedMessage denyMessage = new PredefinedMessage(Component.text("You don't have permission to run that command!"));
    private final PermItem prefixCommand = new PermItem(
            "prefixCommand",
            denyMessage,"/prefix");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if(args.length == 0){
                if(permHelper.hasPermission(p,prefixCommand)){
                    if(hasNoPrefix(p)){
                        chatHelper.sendChat(p,Component.text().content("You don't have a prefix selected!").build());
                        chatHelper.sendChat(p,
                                Component.text()
                                        .append(chatHelper.buildRunCommandComponent("[Select One]","/prefix list", true))
                                        .build());
                    }else{
                        chatHelper.sendChat(p,Component.text()
                                .content("your prefix is: ")
                                .append(getPrefixAsComponent(getCurrentPrefix(p)))
                                        .append(Component.text("!"))
                                .build());
                        chatHelper.sendChat(p,
                                Component.text()
                                        .append(chatHelper.buildRunCommandComponent("[Select a New One]","/prefix list", true))
                                        .append(Component.text(" "))
                                        .append(chatHelper.buildRunCommandComponent("[Clear Selection]","/prefix select none", true, NamedTextColor.RED))
                                        .build());
                    }
                }
            }else {
                switch (args[0]){
                    case "list":
                        //prefix list[0] <player>[1]
                        return prefixList.command(p,args);
                    case "select":
                        //prefix select[0] <prefix>[1]
                        return prefixSelect.command(p,args);
                    case"setCustom":
                        //prefix setCustom[0] <option>[1] <prefix>[2]
                        return prefixSetCustom.command(p,args);
                    default:
                        //todo command help
                        return false;
                }
            }
            return true;
        }
        return false;
    }
}
