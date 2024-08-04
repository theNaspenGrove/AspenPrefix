package mov.naspen.naspenprefix.commands;

import mov.naspen.naspenprefix.NaspenPrefix;
import mov.naspen.naspenprefix.helpers.PrefixHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static mov.naspen.naspenprefix.commands.prefixList.prefixListOwn;

public class PrefixTabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, Command command, @NotNull String aliasUsed, String[] args) {
        if(command.getName().equals("prefix")){
            if(sender instanceof Player) {
                Player p = (Player) sender;
                if(args.length == 1){
                    return whatCanRun(p);
                }else if(args.length == 2){
                    if(args[0].equals(prefixSelect.prefixSelectOwn.getCommand())){
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
        if (NaspenPrefix.permHelper.hasPermission(p, prefixListOwn,true)) l.add(prefixListOwn.getCommand());
        //select
        if (NaspenPrefix.permHelper.hasPermission(p, prefixSelect.prefixSelectOwn,true)) l.add(prefixSelect.prefixSelectOwn.getCommand());
        //setCustom
        if (NaspenPrefix.permHelper.hasPermission(p, prefixSetCustom.prefixSetCustomOwn,true)) l.add(prefixSetCustom.prefixSetCustomOwn.getCommand());
        return l;
    }

    public static List<String> whatPrefixes(Player p){
        ArrayList<String> l = new ArrayList<>();
        if(NaspenPrefix.permHelper.hasPermission(p, prefixSetCustom.prefixSetCustomOwn,true) && PrefixHelper.hasCustomPrefix(p)){
            l.add("Custom");
        }
        l.addAll(PrefixHelper.getPlayerPrefixes(p));
        return l;
    }

}
