package net.mov51.aspenprefix.helpers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static net.mov51.aspenprefix.AspenPrefix.LPapi;
import static net.mov51.aspenprefix.AspenPrefix.logger;
import static net.mov51.aspenprefix.helpers.PermissionsHelper.Permission.prefixAddition;
import static net.mov51.aspenprefix.helpers.ConfigHelper.isPrefixDefined;
import static net.mov51.aspenprefix.helpers.messageHelper.sendChatMessage;

public class PermissionsHelper {

    public static final String permissionPrefix = "AspenPrefix";
    public static final TextComponent defaultDeny = Component.text().content("You don't have permission to run that command!").build();

    public enum Permission{
        prefixCommand(permissionPrefix + ".command.prefix", "You aren't allowed to run the prefix command!" ,"/prefix"),
        prefixListCommand(prefixCommand.key + ".list", "/prefix list"),
        prefixSetCommand(prefixCommand.key + ".set", "/prefix set"),
        prefixAddition(permissionPrefix + "\\.prefix\\..+"),
        customPrefixOwn(permissionPrefix + ".setOwnCustom"),
        customPrefixOther(permissionPrefix + ".setOtherCustom");


        public final String key;
        public TextComponent denyMessage;
        public String command;

        Permission(String permission){
            this.key = permission;
        }

        Permission(String permission, String command){
            this.key = permission;
            this.denyMessage = defaultDeny;
            this.command = command;
        }

        Permission(String permission, String denyMessage, String command){
            this.key = permission;
            this.denyMessage = Component.text().content(denyMessage).build();
            this.command = command;
        }
    }

    public static boolean hasPermission(Player p,Permission permission){
        if(p.hasPermission(permission.key)){
            return true;
        }
        sendChatMessage(p,permission.denyMessage);
        logger.info("player " + p + " does not have permission " + permission.key + " for command " + permission.command);
        return false;
    }

    public static ArrayList<String> getAllPrefixes(Player p){
        User user = LPapi.getPlayerAdapter(Player.class).getUser(p);
        ArrayList<String> prefixes = new ArrayList<>();

        for(Node n : user.resolveInheritedNodes(QueryOptions.nonContextual())){
            if (n.getKey().matches(prefixAddition.key)){
                String prefix = n.getKey().split("\\.")[2];
                if(isPrefixDefined(prefix)){
                    prefixes.add(prefix);
                }else{
                    logger.warning(ChatColor.RED + "Prefix " + prefix + " is not defined in the config but you have a permission node for it!");
                }
            }
        }
        return prefixes;
    }
}
