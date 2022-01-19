package net.mov51.aspenprefix.helpers;

import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.query.QueryOptions;
import net.mov51.periderm.luckperms.AspenMetaKey;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static net.mov51.aspenprefix.AspenPrefix.*;
import static net.mov51.aspenprefix.helpers.ConfigHelper.getPrefixValue;
import static net.mov51.aspenprefix.helpers.ConfigHelper.isPrefixDefined;


public class PrefixHelper {

    public static AspenMetaKey currentPrefix = new AspenMetaKey("CurrentPrefix");
    public static AspenMetaKey customPrefix = new AspenMetaKey("CustomPrefix");

    public static String getSelectedPrefix(Player p){
        return metaHelper.getMetaValue(p,currentPrefix);
    }

    public static boolean hasSelectedPrefix(Player p){
        return getSelectedPrefix(p) == null;
    }

    public static void setSelectedPrefix(Player p, String Value){
        metaHelper.setMetaValue(p,currentPrefix,Value);
    }

    public static void setCustomPrefix(Player p, String Value){
        metaHelper.setMetaValue(p,customPrefix,Value);
    }

    public static String getCustomPrefix(Player p) {
        return metaHelper.getMetaValue(p,customPrefix);
    }

    public static boolean hasCustomPrefix(Player p) {
        return metaHelper.getMetaValue(p,customPrefix) == null;
    }

    public static TextComponent getPrefixAsComponent(String prefix){
        return LegacyComponentSerializer.legacyAmpersand().deserialize(prefix);
    }

    public static String getCurrentPrefix(Player p){
        if(hasSelectedPrefix(p)){
            //if no prefix is selected,
            // get the value of the first prefix sorted by weight
            return getPrefixValue(getAllPrefixes(p).get(0));
        }else if(getSelectedPrefix(p).equals(customPrefix.getKey())){
            //if the selected prefix is equal to the customPrefixKey
            // then return the value of the custom prefix
            return getCustomPrefix(p);
        }else
            //return the value of the selected prefix
            return getPrefixValue(getSelectedPrefix(p));
    }

    public static ArrayList<String> getAllPrefixes(Player p){
        User user = LPapi.getPlayerAdapter(Player.class).getUser(p);
        ArrayList<String> prefixes = new ArrayList<>();

        for(Node n : user.resolveInheritedNodes(QueryOptions.nonContextual())){
            if (n.getKey().matches("AspenPrefix\\.prefix\\..+")){
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
