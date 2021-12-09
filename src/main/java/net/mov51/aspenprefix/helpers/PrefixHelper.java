package net.mov51.aspenprefix.helpers;

import org.bukkit.entity.Player;

import static net.mov51.aspenprefix.helpers.LPMetaHelper.MetaKey.currentPrefix;
import static net.mov51.aspenprefix.helpers.LPMetaHelper.MetaKey.customPrefix;
import static net.mov51.aspenprefix.helpers.LPMetaHelper.getMetaValue;
import static net.mov51.aspenprefix.helpers.LPMetaHelper.setMetaValue;
import static net.mov51.aspenprefix.helpers.ConfigHelper.getPrefixValue;
import static net.mov51.aspenprefix.helpers.PermissionsHelper.getAllPrefixes;

public class PrefixHelper {

    private static final String customPrefixKey = "CustomPrefix";

    public static String getSelectedPrefix(Player p){
        return (getMetaValue(p,currentPrefix) == null) ? "" : getMetaValue(p,currentPrefix);
    }

    public static boolean hasSelectedPrefix(Player p){
        return !getSelectedPrefix(p).isEmpty();
    }

    public static void setSelectedPrefix(Player p, String Value){
        setMetaValue(p,currentPrefix,Value);
    }

    public static void setCustomPrefix(Player p, String Value){
        setMetaValue(p,customPrefix,Value);
    }

    public static String getCustomPrefix(Player p) {
        return (getMetaValue(p,customPrefix) == null) ? "" : getMetaValue(p,customPrefix);
    }

    public static String getCurrentPrefix(Player p){
        if(getSelectedPrefix(p).isEmpty()){
            //if no prefix is selected,
            // get the value of the first prefix sorted by weight
            return getPrefixValue(getAllPrefixes(p).get(0));
        }else if(getSelectedPrefix(p).equals(customPrefixKey)){
            //if the selected prefix is equal to the customPrefixKey
            // then return the value of the custom prefix
            return getPrefixValue(getCustomPrefix(p));
        }else
            //return the value of the selected prefix
            return getPrefixValue(getSelectedPrefix(p));
    }

}
