package net.projectx.simcity.functions;

import net.projectx.simcity.functions.mysql.MySQL_SafeChest;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.UUID;

public class SafeChest implements Listener {
    @EventHandler
    public void openChest(PlayerInteractEvent ce) {
        Player p = ce.getPlayer();
        p.sendMessage("Event wurde ausgelöst");
        if (ce.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            p.sendMessage("Es wurde Rechtsklick gemacht");
            if (!ce.getPlayer().isSneaking()) {
                p.sendMessage("Es wurde nicht gesneakt");
                if (ce.getClickedBlock().getType().equals(Material.CHEST)) {
                    p.sendMessage("Es wurde eine Kiste geöffnet");

                    UUID uuid = ce.getPlayer().getUniqueId();
                    Location loc = ce.getClickedBlock().getLocation();
                    if (MySQL_SafeChest.isChestOf(uuid, loc)) {
                        p.sendMessage("Die Kiste gehört dem Spieler");
                        return;
                    } else {
                        p.sendMessage("Du darfst diese Kiste nicht öffen");
                        ce.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void activateChest(BlockPlaceEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        Location loc = event.getBlockAgainst().getLocation();
        if (event.getBlock().getType().equals(Material.OAK_WALL_SIGN)) {


            if (event.getBlockAgainst().getType().equals(Material.CHEST)) {
                if (!MySQL_SafeChest.isChestOf(loc)) {

                    MySQL_SafeChest.safeChest(uuid, loc);
                }
            }
        }
    }

    @EventHandler
    public void deactivateChest(BlockBreakEvent sign) {

        if (sign.getBlock().getType().equals(Material.OAK_WALL_SIGN)) {
            UUID uuid = sign.getPlayer().getUniqueId();
            Location loc = sign.getBlock().getLocation();
            if (MySQL_SafeChest.isSignNearChest(loc)) {
                Location chest = MySQL_SafeChest.isSignNearChestGetLocation(loc);
                if (MySQL_SafeChest.isChestOf(uuid, chest)) {
                    MySQL_SafeChest.deleteChest(uuid, chest);
                } else {
                    sign.setCancelled(true);
                }
            }
        } else {
            if (sign.getBlock().getType().equals(Material.CHEST)) {
                UUID uuid = sign.getPlayer().getUniqueId();
                Location chest = sign.getBlock().getLocation();
                if (MySQL_SafeChest.isChestOf(uuid, chest)) {
                    MySQL_SafeChest.deleteChest(uuid, chest);
                } else {
                    sign.setCancelled(true);
                }
            }
        }
    }

}
