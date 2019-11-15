package net.projectx.simcity.functions;

import net.projectx.simcity.functions.mysql.MySQL;
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

        if(sign.getBlock().getType()==Material.OAK_WALL_SIGN){
            UUID uuid = sign.getPlayer().getUniqueId();
            Location loc = sign.getBlock().getLocation();
           if(MySQL_SafeChest.isSignNearChest(loc)){
               Location chest  = MySQL_SafeChest.isSignNearChestGetLocation(loc);
               if(MySQL_SafeChest.isChestOf(uuid,chest)){
                   MySQL_SafeChest.deleteChest(uuid,chest);
               }else{
                   sign.setCancelled(true);
               }
           }
        }else{
            if(sign.getBlock().getType()==Material.CHEST){
                UUID uuid = sign.getPlayer().getUniqueId();
                Location chest = sign.getBlock().getLocation();
                if(MySQL_SafeChest.isChestOf(uuid,chest)){
                    MySQL_SafeChest.deleteChest(uuid,chest);
                }else{
                    sign.setCancelled(true);
                }
            }
        }
    }

}
