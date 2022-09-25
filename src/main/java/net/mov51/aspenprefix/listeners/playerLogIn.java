package net.mov51.aspenprefix.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static net.mov51.aspenprefix.helpers.ConfigHelper.prefixes;
import static net.mov51.aspenprefix.helpers.PrefixHelper.loadPlayerPrefixList;

public class playerLogIn implements Listener {
    @EventHandler
    public void PlayerJoin(PlayerJoinEvent e) {
        loadPlayerPrefixList(e.getPlayer());
    }
}
