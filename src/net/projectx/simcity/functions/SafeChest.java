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
        p.sendMessage("Interact Event");
        if (ce.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            p.sendMessage("Es wurde Rechtsklick gemacht");
            if (!ce.getPlayer().isSneaking()) {
                p.sendMessage("Es wurde nicht gesneakt");
                if (ce.getClickedBlock().getType().equals(Material.CHEST)) {
                    p.sendMessage("Es wurde eine Kiste geöffnet");

                    UUID uuid = ce.getPlayer().getUniqueId();
                    Location loc = ce.getClickedBlock().getLocation();
                    if(MySQL_SafeChest.isChestOf(loc)) {
                        p.sendMessage("Es ist eine SafeChest");
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
    }

    @EventHandler
    public void activateChest(BlockPlaceEvent event) {
        Player p = event.getPlayer();
        p.sendMessage("BlockplaceEvent");
        UUID uuid = event.getPlayer().getUniqueId();
        Location loc = event.getBlockAgainst().getLocation();
        if (event.getBlock().getType().equals(Material.OAK_WALL_SIGN)) {
            p.sendMessage("Es wurde ein Schild plaziert");

            if (event.getBlockAgainst().getType().equals(Material.CHEST)) {
                p.sendMessage("Das Schild wurde an eine Kiste plaziert");
                if (!MySQL_SafeChest.isChestOf(loc)) {
                    p.sendMessage("Die Kiste gehört noch keinem Spieler");
                    MySQL_SafeChest.safeChest(uuid, loc);
                }else{
                    event.setCancelled(true);
                    p.sendMessage("Die Kiste gehört schon jemandem");
                }
            }
        }
    }

    @EventHandler
    public void deactivateChest(BlockBreakEvent sign) {
        Player p = sign.getPlayer();
        p.sendMessage("BlockBreakEvent");

        if (sign.getBlock().getType().equals(Material.OAK_WALL_SIGN)) {
            p.sendMessage("Ein schild wurde zerstört");
            UUID uuid = sign.getPlayer().getUniqueId();
            Location loc = sign.getBlock().getLocation();
            if (MySQL_SafeChest.isSignNearChest(loc)) {
                p.sendMessage("Das Schild war an einer Kiste");
                Location chest = MySQL_SafeChest.isSignNearChestGetLocation(loc);
                if (MySQL_SafeChest.isChestOf(uuid, chest)) {
                    p.sendMessage("Die Kiste gehört diesem Spieler und wird gelöscht");
                    MySQL_SafeChest.deleteChest(uuid, chest);
                } else {
                    p.sendMessage("Mache nicht andere Schilde kaputt");
                    sign.setCancelled(true);
                }
            }
        } else {
            if (sign.getBlock().getType().equals(Material.CHEST)) {
                p.sendMessage("Eine Kiste wurde zerstört");
                UUID uuid = sign.getPlayer().getUniqueId();
                Location chest = sign.getBlock().getLocation();
                if(MySQL_SafeChest.isChestOf(chest)) {
                    if (MySQL_SafeChest.isChestOf(uuid, chest)) {
                        p.sendMessage("Die Kiste gehört diesem Spieler und wird gelöscht");
                        MySQL_SafeChest.deleteChest(uuid, chest);
                    } else {
                        p.sendMessage("Mache nicht andere Kisten kaputt");
                        sign.setCancelled(true);
                    }
                }
            }
        }
    }

}
