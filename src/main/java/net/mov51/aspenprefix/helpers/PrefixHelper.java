package net.mov51.aspenprefix.helpers;

import mov.naspen.periderm.helpers.luckPerms.AspenMetaKey;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

import static mov.naspen.periderm.helpers.StringsHelper.StringToArrayListString;
import static mov.naspen.periderm.helpers.StringsHelper.arrayListStringToString;
import static net.mov51.aspenprefix.AspenPrefix.*;
import static net.mov51.aspenprefix.helpers.ConfigHelper.*;


public class PrefixHelper {

    public static final AspenMetaKey currentPrefix = new AspenMetaKey("CurrentPrefix");
    public static final AspenMetaKey customPrefix = new AspenMetaKey("CustomPrefix");
    public static final AspenMetaKey lastKnownPrefixes = new AspenMetaKey("PrefixList");

    public static String getSelectedPrefix(Player p){
        return metaHelper.getMetaValue(p,currentPrefix) != null ? metaHelper.getMetaValue(p,currentPrefix) : getLowestPriorityPrefixTarget(p);
    }

    private static String getLowestPriorityPrefixTarget(Player p){
        return metaHelper.getMetaValue(p,lastKnownPrefixes) != null ? StringToArrayListString(metaHelper.getMetaValue(p,lastKnownPrefixes)).get(0) : defaultPrefixTarget;
    }

    public static boolean hasNoPrefix(Player p){
        return getSelectedPrefix(p) == null;
    }

    public static void setSelectedPrefix(Player p, String Value){
        metaHelper.setMetaValue(p,currentPrefix,Value);
    }

    public static void clearSelectedPrefix(Player p){
        metaHelper.clearMetaValue(p,currentPrefix);
    }

    public static void setCustomPrefix(Player p, String Value){
        metaHelper.setMetaValue(p,customPrefix,Value);
    }

    public static String getCustomPrefix(Player p) {
        return metaHelper.getMetaValue(p,customPrefix);
    }

    public static boolean hasCustomPrefix(Player p) {
        return metaHelper.hasMetaValue(p,customPrefix);
    }

    public static TextComponent getPrefixAsComponent(String prefix){
        return LegacyComponentSerializer.legacyAmpersand().deserialize(prefix);
    }

    public static String getCurrentPrefix(Player p){
        if(hasCustomPrefix(p)){
            return getCustomPrefix(p);
        } else {
            return getSelectedPrefix(p);
        }
    }

    public static void loadPlayerPrefixList(Player p){
        //get LP-user.
        User user = metaHelper.getLPapi().getPlayerAdapter(Player.class).getUser(p);
        //define prefix TreeMap for unsorted prefixes to be added with their weight.
        TreeMap<String, Integer> unsortedPrefixes = new TreeMap<>();
        //Loop through nodes selected by query options.
        for(Node n : user.resolveInheritedNodes(metaHelper.getLPapi().getContextManager().getQueryOptions(p))){
            if (n.getKey().matches("AspenPrefix\\.prefix\\..+")){
                //get Prefix Name.
                String prefixName = n.getKey().split("\\.")[2];
                //check if prefix is defined in the config by name.
                if(isPrefixDefined(prefixName)){
                    //if it is defined, add the prefix, and it's weight, to the unsorted prefix map
                    unsortedPrefixes.put(prefixName,getPrefixWeight(prefixName));
                }else{
                    // if it isn't, warn the console that the prefix name isn't define but there is a node for it!
                    logger.warning(ChatColor.RED + "Prefix " + prefixName + " is not defined in the config but you have a permission node for it!");
                }
            }
        }

        if(unsortedPrefixes.isEmpty()){
            //if no prefixes have been added to the list, use the default prefix.
            unsortedPrefixes.put(defaultPrefixTarget,0);
        }
        ArrayList<String> sortedPrefixes = valueSortReverseToArray(unsortedPrefixes);
        if(metaHelper.hasMetaValue(p,lastKnownPrefixes)){
            ArrayList<String> currentPrefixList = StringToArrayListString(
                    metaHelper.getMetaValue(p,lastKnownPrefixes));
            if(!sortedPrefixes.equals(currentPrefixList)){
                chatHelper.sendChat(p,"Your prefixes have changed!");
            }
        }
        if(sortedPrefixes.size() > 1){
            metaHelper.setMetaValue(p,lastKnownPrefixes,arrayListStringToString(sortedPrefixes));
        }else{
            metaHelper.setMetaValue(p,lastKnownPrefixes,sortedPrefixes.get(0));
        }

    }

    public static <K, V extends Comparable<V> > ArrayList<K> valueSortReverseToArray(final Map<K, V> map){
        //sorts passed maps by value and converts them to an array of the key type.

        //define comparator
        Comparator<K> valueComparator = Comparator.comparing(map::get);
        //define new map with a reversed version of the comparator
        Map<K, V> sorted = new TreeMap<>(valueComparator.reversed());
        //put data from provided map into the sorted map
        sorted.putAll(map);

        //loop through sorted map and place keys into ArrayList
        ArrayList<K> outputArray = new ArrayList<>();
        for (Map.Entry<K, V> mapElement : sorted.entrySet()) {
            outputArray.add(mapElement.getKey());
        }

        return outputArray;
    }

    public static ArrayList<String> getPlayerPrefixes(Player p){
        return StringToArrayListString(metaHelper.getMetaValue(p,lastKnownPrefixes));
    }

}
