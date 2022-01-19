package net.mov51.aspenprefix.commands;

import net.kyori.adventure.text.Component;
import net.mov51.periderm.paper.chat.PredefinedMessage;
import net.mov51.periderm.paper.permissions.Perm;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


import static net.mov51.aspenprefix.AspenPrefix.*;
import static net.mov51.aspenprefix.helpers.PrefixHelper.*;

public class PrefixCommand implements CommandExecutor {

    private final PredefinedMessage denyMessage = new PredefinedMessage(Component.text("You don't have permission to run that command!"));
    private final Perm prefixCommand = new Perm(
            "prefixCommand",
            denyMessage,"/prefix");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        //todo handle prefix command
        //todo select prefix via chat
        // doesn't use extra commands, it's ask and answer
        // maybe have a sub command system for faster selection


        if (sender instanceof Player) {
            Player p = (Player) sender;
            if(args.length == 0){
                if(permHelper.hasPermission(p,prefixCommand)){
                    if(hasSelectedPrefix(p)){
                        chatHelper.sendChat(p,Component.text().content("You don't have a prefix selected!").build());
                        chatHelper.sendChat(p,
                                Component.text()
                                        .content("If you'd like to select one ")
                                        .append(chatHelper.buildRunCommandComponent("Click Here!","/prefix list", true))
                                        .build());
                    }else{
                        chatHelper.sendChat(p,Component.text()
                                .content("your prefix is ")
                                .append(getPrefixAsComponent(getCurrentPrefix(p)))
                                        .append(Component.text("!"))
                                .build());
                        chatHelper.sendChat(p,
                                Component.text()
                                        .content("If you'd like to select a different prefix ")
                                        .append(chatHelper.buildRunCommandComponent("Click Here!","/prefix list", true))
                                        .build());
                    }
                }
            }else {
                switch (args[0]){
                    case "list":
                        //prefix list[0] <player>[1]
                        return prefixList.command(p,args);
                    case "set":
                        //prefix set[0] <prefix>[1] <player>[2]
                        return prefixSelect.command(p,args);
                    case"setCustom":
                        //prefix setCustom[0] <player>[1]
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
