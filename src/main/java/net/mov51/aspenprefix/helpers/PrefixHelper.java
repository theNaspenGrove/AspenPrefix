package net.mov51.aspenprefix.helpers;

import org.bukkit.entity.Player;

public class PrefixHelper {

    private enum LuckPermsKeys{
        GlobalPrefix("AspenPrefix-"),
        Prefix(GlobalPrefix+"Prefix"),
        CurrentPrefix(GlobalPrefix+"CurrentPrefix"),
        SelectedPrefix(GlobalPrefix+"SelectedPrefix"),
        PrefixList(GlobalPrefix+"PrefixList");

        String key;

        LuckPermsKeys(String key){
            this.key = key;
        }
    }

    public String[] getPrefixList(Player p){
        //TODO Get the stored list of available prefixes for the player
        return null;
    }

    public String setPrefixList(Player p){
        //TODO Set the stored list of available prefixes for the player using getAllPrefixes
        return null;
    }

    public String[] getAllPrefixes(Player p){
        //TODO Get prefixes for player in order of weight
        return null;
    }

    public String getSelected(Player p){
        //TODO Get selected prefix for player if one exists
        // return empty string if one doesn't
        return "";
    }

    public String getCurrentPrefix(Player p){
        //TODO the current prefix for the player
        // return selected prefix if one exists
        // return highest weight prefix if none exists
        return null;
    }
}
