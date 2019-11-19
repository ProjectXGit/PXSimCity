package net.projectx.simcity.functions.berufe;

import net.projectx.simcity.functions.mysql.MySQL_User;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;
import java.util.UUID;

public class Foerster {
    @EventHandler
    public void TreeChop(BlockBreakEvent event) {
        Block wood = event.getBlock();
        UUID uuid = event.getPlayer().getUniqueId();
        Random random = new Random(5);
        if(wood.equals(Material.LEGACY_LOG)){
            if(!MySQL_User.getJob(uuid).equalsIgnoreCase("Foerster")){
                if(random.nextInt()==5) {
                    event.setDropItems(true);
                }else{
                    event.setDropItems(false);
                }
            }else{
                event.setDropItems(true);
                ItemStack drop = new ItemStack(Material.DIAMOND);
                wood.getDrops().add(drop);
            }
        }
    }
}
