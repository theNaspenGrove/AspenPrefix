package mov.naspen.naspenprefix.helpers;

import mov.naspen.naspenprefix.NaspenPrefix;
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
import static mov.naspen.naspenprefix.helpers.ConfigHelper.*;


public class PrefixHelper {

    public static final AspenMetaKey currentPrefix = new AspenMetaKey("current-prefix");
    public static final AspenMetaKey customPrefix = new AspenMetaKey("custom-prefix");
    public static final AspenMetaKey lastKnownPrefixes = new AspenMetaKey("prefix-list");

    public static TextComponent defaultPrefix = getPrefixFromTarget(defaultPrefixTarget);

    private static String getLowestPriorityPrefixTarget(Player p){
        return NaspenPrefix.metaHelper.getMetaValue(p,lastKnownPrefixes) != null ? StringToArrayListString(NaspenPrefix.metaHelper.getMetaValue(p,lastKnownPrefixes)).get(0) : defaultPrefixTarget;
    }

    public static TextComponent setSelectedPrefix(Player p, String value){
        NaspenPrefix.metaHelper.setMetaValue(p,currentPrefix,value);
        return value.equals(customPrefix.getKey()) ? getCustomPrefix(p) : getPrefixFromTarget(value);
    }

    public static void clearSelectedPrefix(Player p){
        NaspenPrefix.metaHelper.clearMetaValue(p,currentPrefix);
    }

    public static void setCustomPrefix(Player p, String Value){
        NaspenPrefix.metaHelper.setMetaValue(p,customPrefix,Value);
    }

    public static TextComponent getCustomPrefix(Player p) {
        String string = NaspenPrefix.metaHelper.getMetaValue(p,customPrefix);
        if(string != null){
            return LegacyComponentSerializer.legacyAmpersand().deserialize(string);
        }else{
            return defaultPrefix;
        }
    }

    public static boolean isSelectedPrefixCustom(Player p) {
        return(Objects.equals(NaspenPrefix.metaHelper.getMetaValue(p, currentPrefix), customPrefix.getKey()));
    }

    public static boolean hasCustomPrefix(Player p) {
        return NaspenPrefix.metaHelper.getMetaValue(p, customPrefix) != null;
    }

    public static TextComponent getActivePrefixAsComponent(Player p){
        if(isSelectedPrefixCustom(p)){
            return getCustomPrefix(p);
        } else {
            String prefixTarget = NaspenPrefix.metaHelper.getMetaValue(p,currentPrefix) != null ? NaspenPrefix.metaHelper.getMetaValue(p,currentPrefix) : getLowestPriorityPrefixTarget(p);
            return getPrefixFromTarget(prefixTarget);
        }
    }

    public static void loadPlayerPrefixList(Player p){
        //get LP-user.
        User user = NaspenPrefix.metaHelper.getLPapi().getPlayerAdapter(Player.class).getUser(p);
        //define prefix TreeMap for unsorted prefixes to be added with their weight.
        TreeMap<String, Integer> unsortedPrefixes = new TreeMap<>();
        //Loop through nodes selected by query options.
        for(Node n : user.resolveInheritedNodes(NaspenPrefix.metaHelper.getLPapi().getContextManager().getQueryOptions(p))){
            if (n.getKey().toLowerCase().matches("naspenprefix\\.prefix\\..+")){
                //get Prefix Name.
                String prefixName = n.getKey().split("\\.")[2];
                //check if prefix is defined in the config by name.
                if(isSelectedPrefixDefined(prefixName)){
                    //if it is defined, add the prefix, and it's weight, to the unsorted prefix map
                    unsortedPrefixes.put(prefixName,getPrefixWeight(prefixName));
                }else{
                    // if it isn't, warn the console that the prefix name isn't define but there is a node for it!
                    NaspenPrefix.logger.warning(ChatColor.RED + "Prefix " + prefixName + " is not defined in the config but you have a permission node for it!");
                }
            }
        }

        if(unsortedPrefixes.isEmpty()){
            //if no prefixes have been added to the list, use the default prefix.
            unsortedPrefixes.put(defaultPrefixTarget,0);
        }
        ArrayList<String> sortedPrefixes = valueSortReverseToArray(unsortedPrefixes);
        if(NaspenPrefix.metaHelper.hasMetaValue(p,lastKnownPrefixes)){
            ArrayList<String> currentPrefixList = StringToArrayListString(
                    NaspenPrefix.metaHelper.getMetaValue(p,lastKnownPrefixes));
            if(!sortedPrefixes.equals(currentPrefixList)){
                NaspenPrefix.chatHelper.sendChat(p,"Your prefixes have changed!");
            }
        }
        if(sortedPrefixes.size() > 1){
            NaspenPrefix.metaHelper.setMetaValue(p,lastKnownPrefixes,arrayListStringToString(sortedPrefixes));
        }else{
            NaspenPrefix.metaHelper.setMetaValue(p,lastKnownPrefixes,sortedPrefixes.get(0));
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
        return StringToArrayListString(NaspenPrefix.metaHelper.getMetaValue(p,lastKnownPrefixes));
    }

}
