package net.projectx.simcity.functions.berufe;

import net.projectx.simcity.functions.mysql.MySQL_User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

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
            }else {
                System.out.println("Förster baut Holz ab");
                ItemStack werkzeug = event.getPlayer().getItemOnCursor();
                System.out.println(""+werkzeug);
                if (werkzeug.getType().toString().endsWith("_AXE")) {
                    System.out.println("Förster baut Holz mit Axt ab");
                    Location loc = wood.getLocation();
                    loc.setY(loc.getY() + 1);
                    Block treeup = loc.getBlock();
                    int anzahlholz = 1;
                    ItemStack wooddrop = new ItemStack(wood.getType(), 1);

                    while (treeup.getType().equals(wood.getType())) {
                        System.out.println("Über dem Holz ist noch eins");
                        if(werkzeug.getDurability()==0){
                            System.out.println("Durability null hahahahaha");
                            return;
                        }
                        treeup.setType(Material.AIR);
                        Bukkit.getWorld("world").dropItem(treeup.getLocation(), wooddrop);
                        anzahlholz++;
                        treeup.getLocation().setY(treeup.getLocation().getY()+1);
                        werkzeug.setDurability((short)(werkzeug.getDurability()-1));
                    }


                }
            }
        }
    }
}
