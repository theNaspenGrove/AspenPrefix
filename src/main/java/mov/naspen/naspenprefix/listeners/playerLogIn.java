package mov.naspen.naspenprefix.listeners;

import mov.naspen.naspenprefix.helpers.PrefixHelper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class playerLogIn implements Listener {
    @EventHandler
    public void PlayerJoin(PlayerJoinEvent e) {
        PrefixHelper.loadPlayerPrefixList(e.getPlayer());
    }
}
