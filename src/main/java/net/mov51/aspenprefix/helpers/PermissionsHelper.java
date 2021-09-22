package net.mov51.aspenprefix.helpers;

import org.bukkit.entity.Player;

import static net.mov51.aspenprefix.AspenPrefix.logger;
import static net.mov51.aspenprefix.helpers.messageHelper.prefix;
import static net.mov51.aspenprefix.helpers.messageHelper.sendChatMessage;

public class PermissionsHelper {

    public static String permissionPrefix = "AspenPrefix.";
    public static String defaultDeny = "You don't have permission to run that command!";

    public enum Permission{
        prefixCommand(permissionPrefix + "command.prefix", "/prefix"),
        prefixListCommand(prefixCommand.permission + ".list", "/prefix list"),
        prefixSetCommand(prefixCommand.permission + ".set", "/prefix set");

        public String permission;
        public String denyMessage;
        public String command;

        Permission(String permission, String denyMessage, String command){
            this.permission = permission;
            this.denyMessage = denyMessage;
            this.command = command;
        }

        Permission(String permission, String command){
            this.permission = permission;
            this.denyMessage = defaultDeny;
            this.command = command;
        }
    }

    public static boolean hasPermission(Player p,Permission permission){
        if(p.hasPermission(permission.permission)){
            return true;
        }
        sendChatMessage(p,permission.denyMessage);
        logger.info("player " + p + " does not have permission " + permission.permission + " for command " + permission.command);
        return false;
    }
}
