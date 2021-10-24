package net.mov51.aspenprefix.helpers;

import org.bukkit.entity.Player;

import static net.mov51.aspenprefix.helpers.LPMetaHelper.MetaKey.currentPrefix;
import static net.mov51.aspenprefix.helpers.LPMetaHelper.getMetaValue;

public class PrefixHelper {

    public static String[] getPrefixList(Player p){
        //TODO Get the stored list of available prefixes for the player
        return null;
    }


    public static String getSelected(Player p){
        //TODO Get selected prefix for player if one exists
        // return empty string if one doesn't
        return (getMetaValue(p,currentPrefix).isEmpty()) ? "None" : getMetaValue(p,currentPrefix);
    }



    public static String getCurrentPrefix(Player p){
        //TODO the current prefix for the player
        // return selected prefix if one exists
        // return highest weight prefix if none exists
        return null;
    }

    public static boolean doesExist(Player p){
        //TODO Check if prefix exists in the config
        return false;
    }
}
