package net.projectx.simcity.functions.berufe;

import net.projectx.simcity.functions.mysql.MySQL_User;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class Farmer implements Listener {
    @EventHandler
    public void DontHoe(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        UUID uuid = p.getUniqueId();

        if (event.hasBlock()) {
            Block gras = event.getClickedBlock();
            if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (gras.getType().equals(Material.GRASS_BLOCK) || gras.getType().toString().endsWith("DIRT")) {
                    if (event.hasItem()) {
                        ItemStack inHand = event.getItem();
                        if(inHand.getType().toString().endsWith("_HOE")){
                            if(!MySQL_User.getJob(uuid).equalsIgnoreCase("Farmer")){
                                event.setCancelled(true);
                                p.sendMessage("§cDu musst Farmer sein, um Hacken benutzen zu können.");

                            }
                        }
                    }
                }
            }

        }
    }

    @EventHandler
    public void DontSeed(BlockPlaceEvent event){
        Block seeds = event.getBlockPlaced();
        Player p = event.getPlayer();
        UUID uuid = p.getUniqueId();
        if(seeds.getType().toString().endsWith("_SEEDS")||seeds.getType().equals(Material.POTATO)||seeds.getType().equals(Material.CARROT)){
            if(event.getBlockAgainst().getType().equals(Material.COARSE_DIRT)){
                if(!MySQL_User.getJob(uuid).equalsIgnoreCase("Farmer")){
                    event.setCancelled(true);
                    p.sendMessage("§cDu musst Farmer sein, um Äcker bepflanzen zu können.");
                }
            }
        }

    }
}
