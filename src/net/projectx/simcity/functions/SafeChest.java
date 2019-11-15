package net.projectx.simcity.functions;

import net.projectx.simcity.functions.mysql.MySQL_SafeChest;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.UUID;

public class SafeChest implements Listener {
    @EventHandler
    public void openChest(PlayerInteractEvent ce) {
        if(ce.getClickedBlock().getType() == Material.CHEST){

           UUID uuid = ce.getPlayer().getUniqueId();
            Location loc = ce.getClickedBlock().getLocation();
            if(MySQL_SafeChest.isChestOf(uuid, loc)){
                return;
            }else{
                ce.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void activateChest(BlockPlaceEvent sign) {
        UUID uuid = sign.getPlayer().getUniqueId();
        Location loc = sign.getBlockAgainst().getLocation();
        if(sign.getBlock().getType()==Material.OAK_WALL_SIGN){


            if(sign.getBlockAgainst().getType()==Material.CHEST){
                if(!MySQL_SafeChest.isChestOf(loc)) {

                    MySQL_SafeChest.safeChest(uuid, loc);
                }
            }
        }
    }

    @EventHandler
    public void deactivateChest(BlockBreakEvent sign) {
        UUID uuid = sign.getPlayer().getUniqueId();
        Location loc = sign.getBlock().getLocation();
        if(sign.getBlock().getType()==Material.OAK_WALL_SIGN){
            if (MySQL_SafeChest.isSignNearChest(loc)) {
                MySQL_SafeChest.deleteChest(uuid, MySQL_SafeChest.isSignNearChestGetLocation(loc));
            }
        }
    }

}
