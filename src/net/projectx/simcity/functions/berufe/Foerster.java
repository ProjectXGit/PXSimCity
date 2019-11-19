package net.projectx.simcity.functions.berufe;

import net.projectx.simcity.functions.mysql.MySQL_User;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Random;
import java.util.UUID;

public class Foerster implements Listener {

    @EventHandler
    public void TreeChop(BlockBreakEvent event) {
        System.out.println("Wird ausgeführt!");
        Block wood = event.getBlock();
        UUID uuid = event.getPlayer().getUniqueId();
        Random random = new Random();
        System.out.println(wood.getBlockData());
        if (wood.getType().equals(Material.OAK_LOG)) {
            System.out.println("Legacy Log!");
            if(!MySQL_User.getJob(uuid).equalsIgnoreCase("Foerster")){
                if (random.nextInt(5) == 0) {
                    System.out.println(random.nextInt(5));
                }else{
                    System.out.println(random.nextInt(5));
                    event.setDropItems(false);
                }
            }
        }
    }
}
