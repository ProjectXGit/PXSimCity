package net.projectx.simcity.functions;

import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * ~Yannick on 19.11.2019 at 22:43 o´ clock
 */
public class ChatEvent implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        Bukkit.getOnlinePlayers().forEach(entry -> {
            if (e.getMessage().contains("@" + entry.getName())) {
                entry.sendMessage(p.getDisplayName() + "§8:§7 " + e.getMessage().replaceAll("@" + entry.getName(), "§b@" + entry.getName() + "§r§7"));
                entry.playEffect(EntityEffect.FIREWORK_EXPLODE);
                entry.playSound(p.getLocation(), Sound.ENTITY_WITHER_DEATH, 10, 1);
            } else {
                entry.sendMessage(p.getDisplayName() + "§8:§7 " + e.getMessage());
            }
        });
        e.setCancelled(true);
    }
}
