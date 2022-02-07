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
import static net.mov51.aspenprefix.helpers.ConfigHelper.*;


public class PrefixHelper {

    public static AspenMetaKey currentPrefix = new AspenMetaKey("CurrentPrefix");
    public static AspenMetaKey customPrefix = new AspenMetaKey("CustomPrefix");

    public static String getSelectedPrefix(Player p){
        return metaHelper.getMetaValue(p,currentPrefix);
    }

    public static boolean hasSelectedPrefix(Player p){
        return getSelectedPrefix(p) != null;
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
        return metaHelper.getMetaValue(p,customPrefix) != null;
    }

    public static TextComponent getPrefixAsComponent(String prefix){
        return LegacyComponentSerializer.legacyAmpersand().deserialize(prefix);
    }

    public static String getCurrentPrefix(Player p){
        if(!hasSelectedPrefix(p)){
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
        //todo save this list to users on re-log/login.
        //get LP-user.
        User user = LPapi.getPlayerAdapter(Player.class).getUser(p);
        //define prefix arraylist.
        ArrayList<String> prefixes = new ArrayList<>();

        //get query options for LuckPerms.
        QueryOptions queryOptions = LPapi.getContextManager().getQueryOptions(p);
        //Loop through nodes selected by query options.
        for(Node n : user.resolveInheritedNodes(queryOptions)){
            if (n.getKey().matches("AspenPrefix\\.prefix\\..+")){
                //get Prefix Name separate from weight.
                String prefixName = n.getKey().split("\\.")[3];
                //add weight to prefix name for processing later on.
                String prefix = n.getKey().split("\\.")[2] + "." + prefixName;
                //check if prefix is defined in the config by name.
                if(isPrefixDefined(prefixName)){
                    //if it is defined, add the prefix, and it's weight, to the string arraylist.
                    prefixes.add(prefix);
                }else{
                    // if it isn't, warn the console that the prefix name isn't define but there is a node for it!
                    logger.warning(ChatColor.RED + "Prefix " + prefixName + " is not defined in the config but you have a permission node for it!");
                }
            }
        }
        if(prefixes.isEmpty()){
            //if no prefixes have been added to the list, use the default prefix.
            prefixes.add(defaultPrefixTarget);
        }
        //todo remove debug log.
        logger.warning("un-sorted prefixes" + ": " + prefixes);

        //sort prefixes with by their weight int.
        prefixes.sort((a, b) -> {
            String[] as = a.split("\\.");
            String[] bs = b.split("\\.");
            int result = Integer.valueOf(as[0]).compareTo(Integer.valueOf(bs[0]));
            if (result == 0)
                result = Integer.valueOf(as[0]).compareTo(Integer.valueOf(bs[0]));
            return result;
        });

        //todo remove debug log.
        logger.warning("sorted prefixes" + ": " + prefixes);

        //clip the weight off of the end of the prefix to get the prefix name.
        ArrayList<String> clippedPrefixes = new ArrayList<>();
        for (String prefix : prefixes) {
            clippedPrefixes.add(prefix.split("\\.")[1]);
        }

        //todo remove debug log.
        logger.warning("clipped prefixes" + ": " + clippedPrefixes);

        return clippedPrefixes;
    }
}
