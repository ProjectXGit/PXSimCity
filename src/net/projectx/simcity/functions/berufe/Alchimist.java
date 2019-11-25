package net.projectx.simcity.functions.berufe;

import net.projectx.simcity.functions.mysql.MySQL_User;
import org.bukkit.block.Block;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.UUID;

public class Alchimist implements Listener {
    @EventHandler
    public void Braustand(PlayerInteractEvent event){
        Player p = event.getPlayer();
        UUID uuid = p.getUniqueId();
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            Block b = (Block) event.getClickedBlock();
            if(b.getType().equals(Material.BREWING_STAND)){
                if(!MySQL_User.getJob(uuid).equalsIgnoreCase("Alchimist")){
                    event.setCancelled(true);
                    p.sendMessage("§cDu musst Alchimist sein, um diesen Braustand nutzen zu können.");
                }
            }
        }
    }
    @EventHandler
    public void Zaubertisch(PlayerInteractEvent event){
        Player p = event.getPlayer();
        UUID uuid = p.getUniqueId();
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            Block b = (Block) event.getClickedBlock();
            if(b.getType().equals(Material.ENCHANTING_TABLE)){
                if(!MySQL_User.getJob(uuid).equalsIgnoreCase("Alchimist")){
                    event.setCancelled(true);
                    p.sendMessage("§cDu musst Alchimist sein, um diesen Zaubertisch nutzen zu können.");
                }
            }
        }
    }
}
