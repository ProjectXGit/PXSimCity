package net.projectx.simcity.functions.berufe;

import net.projectx.simcity.functions.mysql.MySQL_User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.entity.Player;
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
        Player p = event.getPlayer();
        UUID uuid = event.getPlayer().getUniqueId();
        Random random = new Random();
        if (wood.getType().toString().endsWith("_LOG")) {
            if(!MySQL_User.getJob(uuid).equalsIgnoreCase("Foerster")){
                if (random.nextInt(5) == 0) {
                }else{
                    event.setDropItems(false);
                }
            }else {
                p.sendMessage("Förster baut Holz ab");
                ItemStack werkzeugitem = event.getPlayer().getInventory().getItemInMainHand();
                Damageable werkzeug = (Damageable) event.getPlayer().getInventory().getItemInMainHand();
                p.sendMessage(""+werkzeugitem);
                if (werkzeugitem.getType().toString().endsWith("_AXE")) {

                    p.sendMessage("Förster baut Holz mit Axt ab");

                    if(!p.isSneaking()) {

                        Location loctreedown = wood.getLocation();
                        Block lowest = loctreedown.getBlock();   //lowest block, block player destroys
                        loctreedown.setY(loctreedown.getY()+1);
                        Block treeup = loctreedown.getBlock();    //blocks of tree
                        ItemStack wooddrop = new ItemStack(wood.getType(), 1);

                        while (treeup.getType().equals(lowest.getType())) {

                            if (werkzeug.getDamage()==werkzeugitem.getType().getMaxDurability()-1) {

                                p.sendMessage("Durability null");
                                return;

                            }

                            p.sendMessage("Über dem Holz ist noch eins");
                            treeup.setType(Material.AIR);
                            Bukkit.getWorld("world").dropItem(treeup.getLocation(), wooddrop);

                            loctreedown.setY(loctreedown.getY()+1);
                            treeup = loctreedown.getBlock();

                            werkzeug.setDamage((short) (werkzeug.getDamage() + 1));




                        }

                    }
                }
            }
        }
    }
}
