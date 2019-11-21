package net.projectx.simcity.functions.berufe;


import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;

import net.projectx.simcity.functions.mysql.MySQL_User;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.entity.EntityDeathEvent;

import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.Random;
import java.util.UUID;

public class Zuechter implements Listener {

    @EventHandler
    public void Tierkiller(EntityDeathEvent event) {
        Random random = new Random();
        LivingEntity dead = event.getEntity();

        if (event.getEntity().getKiller() instanceof Player) {

            Player p = event.getEntity().getKiller();
            UUID uuid = p.getUniqueId();

            if (dead instanceof Animals) {

                if (!MySQL_User.getJob(uuid).equalsIgnoreCase("Zuechter")) {

                    int rand = random.nextInt(5);

                    if (rand == 0) {
                        return;
                    } else {

                        event.getDrops().clear();
                        return;
                    }
                }
            }
        }
    }


    @EventHandler
    public void LiebeMachen(EntityBreedEvent event) {

        if(event.getBreeder() instanceof Player) {
            Player p = (Player) event.getBreeder();
            UUID uuid = p.getUniqueId();
            Entity entity = event.getEntity();

            if(!MySQL_User.getJob(uuid).equalsIgnoreCase("Zuechter")){

                event.setCancelled(true);
                return;
            }
        }
    }
    @EventHandler
    public void SchafSchere(PlayerShearEntityEvent event){

        Player p = event.getPlayer();
        UUID uuid = p.getUniqueId();
        Entity sheep = event.getEntity();

        if(sheep.getType().equals(EntityType.SHEEP)){
            if(!MySQL_User.getJob(uuid).equalsIgnoreCase("Zuechter")){
                event.setCancelled(true);
                return;
            }
        }
    }
    @EventHandler
    public void Melken(PlayerInteractEntityEvent event){
        Player p = event.getPlayer();
        UUID uuid = p.getUniqueId();

        if(event.getRightClicked() instanceof Cow){
            Entity entity = event.getRightClicked();
            if(p.getInventory().getItemInMainHand().getType().equals(Material.BUCKET)){
                if(MySQL_User.getJob(uuid).equalsIgnoreCase("Zuechter")){
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }
}
