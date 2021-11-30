package net.mov51.aspenprefix.helpers;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.ArrayList;

import static net.mov51.aspenprefix.AspenPrefix.logger;
import static net.mov51.aspenprefix.helpers.messageHelper.sendChatMessage;

public class PlayerResponseListener implements Listener {

    private static PlayerResponseListener single_instance = null;

    public static PlayerResponseListener getInstance()
    {
        if (single_instance == null)
            single_instance = new PlayerResponseListener();

        return single_instance;
    }

    //todo move to Conversation API
    ArrayList<Player> PlayersToWatch = new ArrayList<>();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChatMessage(AsyncChatEvent e) {

        if(single_instance.PlayersToWatch.contains(e.getPlayer())){

            logger.info(e.getPlayer().getName() + " said: " + e.message());
            //remove player from watch list from within the singleton
            single_instance.stopWatchingPlayer(e.getPlayer());
        }
    }

    public void watchPlayer(Player p){
        logger.info(p.getPlayer() + " was added to the response watch list!");
        this.PlayersToWatch.add(p);
        sendChatMessage(p, Component.text()
                .content("Type the custom prefix you want to use in chat!")
                .build());
    }

    public void stopWatchingPlayer(Player p){
        logger.info(p.getPlayer() + " was removed from the response watch list!");
        this.PlayersToWatch.remove(p);
    }

}
