package net.mov51.aspenprefix.helpers;

import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static net.mov51.aspenprefix.AspenPrefix.LPapi;
import static net.mov51.aspenprefix.AspenPrefix.logger;
import static net.mov51.aspenprefix.helpers.PermissionsHelper.Permission.prefixAddition;
import static net.mov51.aspenprefix.helpers.configHelper.getPrefix;
import static net.mov51.aspenprefix.helpers.configHelper.isDefined;
import static net.mov51.aspenprefix.helpers.messageHelper.sendChatMessage;

public class PermissionsHelper {

    public static final String permissionPrefix = "AspenPrefix";
    public static final String defaultDeny = "You don't have permission to run that command!";

    public enum Permission{
        prefixCommand(permissionPrefix + ".command.prefix", "You aren't allowed to run the prefix command!" ,"/prefix"),
        prefixListCommand(prefixCommand.key + ".list", "/prefix list"),
        prefixSetCommand(prefixCommand.key + ".set", "/prefix set"),
        prefixAddition(permissionPrefix + "\\.prefix\\..+"),
        customPrefixOwn(permissionPrefix + ".setOwnCustom"),
        customPrefixOther(permissionPrefix + ".setOtherCustom");


        public final String key;
        public String denyMessage;
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
            this.denyMessage = denyMessage;
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
                //todo check for prefix in config and verify it's existence
                // - if it doesn't exist, send the permission in red and send a console error
                // - if it does exist, show the prefix value
                String prefix = n.getKey().split("\\.")[2];
                if(isDefined(prefix)){
                    prefixes.add(getPrefix(prefix));
                }
            }
        }
        return prefixes;
    }
}
