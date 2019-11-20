package net.projectx.simcity.functions.berufe;

import net.projectx.simcity.functions.mysql.MySQL_User;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class Schmied implements Listener {
    @EventHandler
    public void Schmieden(CraftItemEvent event) {
        ItemStack item = event.getCursor();
        Player p = (Player) event.getWhoClicked();
        UUID uuid = p.getUniqueId();

        if (!item.getType().toString().startsWith("WOODEN_")) {
            if (!item.getType().toString().startsWith("LEATHER_")) {
                if (!item.getType().toString().startsWith("TURTLE_")) {
                    if (!item.getType().toString().startsWith("CHAINMAIL_")) {
                        if (!item.getType().toString().startsWith("STONE_")) {
                            if (item.getType().toString().endsWith("_SWORD")) {
                                if (item.getType().toString().endsWith("_PICKAXE")) {
                                    if (item.getType().toString().endsWith("_AXE")) {
                                        if (item.getType().toString().endsWith("_SHOVEL")) {
                                            if (item.getType().toString().endsWith("_HOE")) {
                                                if (item.getType().toString().endsWith("_HELMET")) {
                                                    if (item.getType().toString().endsWith("_CHESTPLATE")) {
                                                        if (item.getType().toString().endsWith("_LEGGINGS")) {
                                                            if (item.getType().toString().endsWith("_HELMET")) {
                                                                if (item.getType().toString().endsWith("_HORSE_ARMOR")) {
                                                                    if (!MySQL_User.getJob(uuid).equalsIgnoreCase("Schmied")) {
                                                                        event.setCancelled(true);
                                                                        p.sendMessage("§cDu musst ein Schmied sein, um dieses Item craften zu können.");
                                                                        return;
                                                                    } else {
                                                                        return;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }

    }

    @EventHandler
    public void Amboss(PlayerInteractEvent event){
        Player p = event.getPlayer();
        Block Amboss = event.getClickedBlock();
        UUID uuid = p.getUniqueId();

        if(Amboss.getType().toString().endsWith("ANVIL")){
            if(!MySQL_User.getJob(uuid).equalsIgnoreCase("Schmied")){
                event.setCancelled(true);
                p.sendMessage("§cDu musst ein Schmied sein, um Ambosse nutzen kann.");
                return;
            }
        }
    }
}
