package net.mov51.aspenprefix.helpers;

import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.MetaNode;
import org.bukkit.entity.Player;

import static net.mov51.aspenprefix.AspenPrefix.LPapi;

public class LPMetaHelper {
    public enum MetaKey {
        topLevelMetaKey("Aspen-Prefix_"),

        //list of prefixes the user has access to
        //-updated on join and prefix-list
        prefixList(topLevelMetaKey.key + "Prefix-List"),

        //the current prefix the user has selected
        //-defaults to the highest weight when user hasn't selected any
        //-value is "custom" when the custom prefix should be used
        currentPrefix(topLevelMetaKey.key + "Current-Prefix"),

        //the custom prefix that the use has defined
        customPrefix(topLevelMetaKey.key + "Custom-Prefix");

        final String key;

        MetaKey(String key){
            this.key = key;
        }
    }

    public static String getMetaValue(Player p, MetaKey metaKey){
        // obtain CachedMetaData - the easiest way is via the PlayerAdapter
        // of course, you can get it via a User too if the player is offline.
        CachedMetaData metaData = LPapi.getPlayerAdapter(Player.class).getMetaData(p);

        // query & parse the meta value
        return metaData.getMetaValue(metaKey.key);
    }

    public static void setMetaValue(Player p, MetaKey metaKey, String value){
        // obtain a User instance (by any means! see above for other ways)
        User user = LPapi.getPlayerAdapter(Player.class).getUser(p);

        // create a new MetaNode holding the level value
        // of course, this can have context/expiry/etc. too!
        MetaNode node = MetaNode.builder(metaKey.key, value).build();

        // clear any existing meta nodes with the same key - we want to override
        user.data().clear(NodeType.META.predicate(mn -> mn.getMetaKey().equals(metaKey.key)));
        // add the new node
        user.data().add(node);

        // save!
        LPapi.getUserManager().saveUser(user);
    }
}
