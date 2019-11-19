package net.projectx.simcity.functions.berufe;

import net.projectx.simcity.functions.mysql.MySQL_User;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Random;
import java.util.UUID;

public class Foerster implements Listener {

    @EventHandler
    public void TreeChop(BlockBreakEvent event) {
        Block wood = event.getBlock();
        UUID uuid = event.getPlayer().getUniqueId();
        Random random = new Random();
        if (wood.getType().toString().endsWith("_LOG")) {
            if(!MySQL_User.getJob(uuid).equalsIgnoreCase("Foerster")){
                if (random.nextInt(5) == 0) {
                }else{
                    event.setDropItems(false);
                }
            }
        }
    }
}
