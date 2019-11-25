package net.projectx.simcity.functions.berufe;

import net.projectx.simcity.functions.mysql.MySQL_User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
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

                ItemStack werkzeugitem = event.getPlayer().getInventory().getItemInMainHand();

                if (werkzeugitem.getType().toString().endsWith("_AXE")) {

                    if(!p.isSneaking()) {

                        Location loctreedown = wood.getLocation();
                        Block lowest = loctreedown.getBlock();   //lowest block, block player destroys
                        loctreedown.setY(loctreedown.getY()+1);
                        Block treeup = loctreedown.getBlock();    //blocks of tree
                        ItemStack wooddrop = new ItemStack(wood.getType(), 1);
                        int durabilitylevel;
                        if(werkzeugitem.getItemMeta().hasEnchant(Enchantment.DURABILITY)){
                            durabilitylevel = werkzeugitem.getEnchantmentLevel(Enchantment.DURABILITY)+1;
                        }else{
                            durabilitylevel = 1;
                        }
                        while (treeup.getType().equals(lowest.getType())) {

                            if (werkzeugitem.getDurability()==werkzeugitem.getType().getMaxDurability()-1) {


                                return;
                            }


                            treeup.setType(Material.AIR);
                            Bukkit.getWorld("world").dropItem(treeup.getLocation(), wooddrop);

                            loctreedown.setY(loctreedown.getY()+1);
                            treeup = loctreedown.getBlock();
                            int durabilityrandom = random.nextInt(100);
                            if(durabilityrandom<=100/durabilitylevel) {
                                werkzeugitem.setDurability((short) (werkzeugitem.getDurability() + 1));
                            }



                        }

                    }
                }
            }
        }
    }
}
