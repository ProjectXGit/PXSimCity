package net.projectx.simcity.functions.berufe;

import net.projectx.simcity.functions.mysql.MySQL_User;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Random;
import java.util.UUID;

public class Miner implements Listener {
    @EventHandler
    public void Mining(BlockBreakEvent event){
        Block Ore = event.getBlock();
        Player p = event.getPlayer();
        UUID uuid = p.getUniqueId();
        Random random = new Random();

        if(!Ore.getType().equals(Material.NETHER_QUARTZ_ORE)){
            if(Ore.getType().toString().endsWith("_ORE")){
                if(!MySQL_User.getJob(uuid).equalsIgnoreCase("Miner")){
                    if(random.nextInt(5)==0){
                        return;
                    }else{
                        event.setDropItems(false);
                    }
                }
            }

        }
    }
}
