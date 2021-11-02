package net.mov51.aspenprefix.helpers;

import org.bukkit.entity.Player;

import static net.mov51.aspenprefix.helpers.LPMetaHelper.MetaKey.currentPrefix;
import static net.mov51.aspenprefix.helpers.LPMetaHelper.getMetaValue;
import static net.mov51.aspenprefix.helpers.LPMetaHelper.setMetaValue;
import static net.mov51.aspenprefix.helpers.ConfigHelper.getPrefix;
import static net.mov51.aspenprefix.helpers.PermissionsHelper.getAllPrefixes;

public class PrefixHelper {

    public static String getSelected(Player p){
        return (getMetaValue(p,currentPrefix) == null) ? "" : getMetaValue(p,currentPrefix);
    }

    public static String getSelectedValue(Player p){
        //TODO Get selected prefix for player if one exists
        // return empty string if one doesn't
        return getPrefix(getSelected(p));
    }

    public static void setSelected(Player p, String key){
        //TODO Get selected prefix for player if one exists
        // return empty string if one doesn't
        setMetaValue(p,currentPrefix,key);
    }

    public static String getCurrentPrefix(Player p){
        //TODO the current prefix for the player
        // return selected prefix if one exists
        // return highest weight prefix if none exists
        if(getSelected(p).isEmpty()){
            return getPrefix(getAllPrefixes(p).get(0));
        }else
        return getPrefix(getSelected(p));
    }

}
