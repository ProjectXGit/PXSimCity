package net.projectx.simcity.functions;

import net.projectx.simcity.functions.mysql.MySQL_SafeChest;
import net.projectx.simcity.util.UUIDFetcher;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.UUID;

import static net.projectx.simcity.main.Data.prefix;

public class SafeChest implements Listener {
    @EventHandler
    public void openChest(PlayerInteractEvent ce) {
        Player p = ce.getPlayer();
        if (ce.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

            if (ce.getClickedBlock().getType().equals(Material.CHEST)) {

                UUID uuid = ce.getPlayer().getUniqueId();
                Location loc = ce.getClickedBlock().getLocation();
                if (MySQL_SafeChest.isSafeChest(loc)) {
                    if (MySQL_SafeChest.isChestOf(uuid, loc)) {
                        return;
                    } else {
                        p.sendMessage(prefix + "§cDiese Kiste gehört: §e" + UUIDFetcher.getName(MySQL_SafeChest.getOwner(loc)));
                        ce.setCancelled(true);
                    }
                }
            }

        }
    }

    @EventHandler
    public void activateChest(BlockPlaceEvent event) {
        Player p = event.getPlayer();
        UUID uuid = event.getPlayer().getUniqueId();
        Location loc = event.getBlock().getLocation();
        Location against = event.getBlockAgainst().getLocation();
        if (event.getBlock().getType().equals(Material.OAK_WALL_SIGN)) {

            if (event.getBlockAgainst().getType().equals(Material.CHEST)) {
                if (!MySQL_SafeChest.isSafeChest(against)) {
                    MySQL_SafeChest.safeChest(uuid, against);
                    p.sendMessage(prefix + "§aSafeChest erstellt!");
                } else {
                    event.setCancelled(true);
                    p.sendMessage("§cDie Kiste gehört schon jemandem");
                }
            }
        }

        if (event.getBlock().getType().equals(Material.CHEST)) {
            System.out.println("Chest geplaced!");
            if (MySQL_SafeChest.isNearSafeChest(loc)) {
                System.out.println("Chest near SafeChest!");
                if (MySQL_SafeChest.isChestOf(uuid, MySQL_SafeChest.getSafeChestNear(loc))) {
                    System.out.println("Chest des Users!");
                    p.sendMessage(prefix + "§4§lAchtung!§7 Auf deine SafeChest kann jetzt zugegriffen werden!");
                } else {
                    System.out.println("Chest eines anderen Users!");
                    event.setCancelled(true);
                    p.sendMessage(prefix + "Du kannst keine Doppelkiste aus der SafeChest eines anderen machen");
                }
            }
        }
    }

    @EventHandler
    public void deactivateChest(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block block = e.getBlock();
        if (block.getType().equals(Material.OAK_WALL_SIGN)) {
            Block attached = MySQL_SafeChest.getBlockSignAttachedTo(block);
            if (attached.getType().equals(Material.CHEST)) {
                if (MySQL_SafeChest.isChestOf(p.getUniqueId(), attached.getLocation())) {
                    MySQL_SafeChest.deleteChest(attached.getLocation());
                    p.sendMessage(prefix + "SafeChest gelöscht!");
                } else {
                    p.sendMessage(prefix + "§cDiese Kiste gehört: §e" + UUIDFetcher.getName(MySQL_SafeChest.getOwner(attached.getLocation())));
                    e.setCancelled(true);
                }
            }
        }


        if (block.getType().equals(Material.CHEST)) {
            UUID uuid = e.getPlayer().getUniqueId();
            Location chest = e.getBlock().getLocation();
            if (MySQL_SafeChest.isSafeChest(chest)) {
                if (MySQL_SafeChest.isChestOf(uuid, chest)) {
                    p.sendMessage(prefix + "SafeChest gelöscht!");
                    MySQL_SafeChest.deleteChest(chest);
                } else {
                    p.sendMessage(prefix + "§cDiese Kiste gehört: §e" + UUIDFetcher.getName(MySQL_SafeChest.getOwner(chest)));
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onHop(InventoryMoveItemEvent e) {
        if (MySQL_SafeChest.isSafeChest(e.getSource().getLocation())) {
            if (e.getDestination().getType().equals(InventoryType.HOPPER)) {
                e.setCancelled(true);
            }
        }
    }


}

