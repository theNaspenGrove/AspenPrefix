package net.mov51.aspenprefix.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static net.mov51.aspenprefix.AspenPrefix.permHelper;
import static net.mov51.aspenprefix.commands.prefixList.prefixListOwn;
import static net.mov51.aspenprefix.commands.prefixSelect.prefixSelectOwn;
import static net.mov51.aspenprefix.commands.prefixSetCustom.prefixSetCustomOwn;
import static net.mov51.aspenprefix.helpers.PrefixHelper.*;

public class PrefixTabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, Command command, @NotNull String aliasUsed, String[] args) {
        if(command.getName().equals("prefix")){
            if(sender instanceof Player) {
                Player p = (Player) sender;
                if(args.length == 1){
                    return whatCanRun(p);
                }else if(args.length == 2){
                    if(args[0].equals(prefixSelectOwn.getCommand())){
                        return whatPrefixes(p);
                    }
                }
            }
        }
        return null;
    }

    public static List<String> whatCanRun(Player p){
        ArrayList<String> l = new ArrayList<>();
        //list
        if (permHelper.hasPermission(p, prefixListOwn,true)) l.add(prefixListOwn.getCommand());
        //select
        if (permHelper.hasPermission(p, prefixSelectOwn,true)) l.add(prefixSelectOwn.getCommand());
        //setCustom
        if (permHelper.hasPermission(p, prefixSetCustomOwn,true)) l.add(prefixSetCustomOwn.getCommand());
        return l;
    }

    public static List<String> whatPrefixes(Player p){
        ArrayList<String> l = new ArrayList<>();
        if(permHelper.hasPermission(p, prefixSetCustomOwn,true) && hasCustomPrefix(p)){
            l.add("Custom");
        }
        l.addAll(getPlayerPrefixes(p));
        return l;
    }

}
