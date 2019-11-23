package net.projectx.simcity.functions.berufe;


import com.sk89q.worldedit.math.BlockVector2;
import net.projectx.simcity.functions.mysql.MySQL_Plot;
import net.projectx.simcity.functions.mysql.MySQL_User;
import net.projectx.simcity.main.Data;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;

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

                if (!MySQL_User.getJob(uuid).equalsIgnoreCase("Tierzuechter")) {

                    int rand = random.nextInt(5);

                    if (rand == 0) {
                        return;
                    } else {

                        event.getDrops().clear();
                        return;
                    }
                }
            }
        } else {
            final boolean[] onPlotOfZuechter = {false};
            MySQL_User.getUsers().forEach(entry -> {
                if (MySQL_User.getJob(entry).equals("Tierzuechter")) {
                    MySQL_Plot.getPlotsOf(entry).forEach(plot -> {
                        BlockVector2 bv0 = Data.regions.getRegion(plot).getPoints().get(0);
                        BlockVector2 bv1 = Data.regions.getRegion(plot).getPoints().get(1);
                        Location loc = dead.getLocation();
                        if (bv0.getBlockX() < loc.getBlockX() && loc.getBlockX() < bv1.getBlockX() || bv1.getBlockX() < loc.getBlockX() && loc.getBlockX() < bv0.getBlockX()) {
                            System.out.println();
                            if (bv0.getBlockZ() < loc.getBlockZ() && loc.getBlockZ() < bv1.getBlockX() || bv1.getBlockZ() < loc.getBlockZ() && loc.getBlockZ() < bv0.getBlockZ()) {
                                onPlotOfZuechter[0] = true;
                            }
                        }
                    });
                }
            });
            if (!onPlotOfZuechter[0]) {
                event.getDrops().clear();
                return;
            }
        }
    }


    @EventHandler
    public void LiebeMachen(EntityBreedEvent event) {

        if(event.getBreeder() instanceof Player) {
            Player p = (Player) event.getBreeder();
            UUID uuid = p.getUniqueId();
            Entity entity = event.getEntity();

            if(!MySQL_User.getJob(uuid).equalsIgnoreCase("Tierzuechter")){

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
            if(!MySQL_User.getJob(uuid).equalsIgnoreCase("Tierzuechter")){
                event.setCancelled(true);
                p.sendMessage("§cDu musst Tierzüchter sein, um Schafe scheren zu können.");
                return;
            }
        }
    }
    @EventHandler
    public void Melken(PlayerInteractEntityEvent event){
        Player p = event.getPlayer();
        UUID uuid = p.getUniqueId();

        if(event.getRightClicked() instanceof Cow){
            if(p.getInventory().getItemInMainHand().getType().equals(Material.BUCKET)){
                if(!MySQL_User.getJob(uuid).equalsIgnoreCase("Tierzuechter")){
                    p.sendMessage("§cDu musst Tierzüchter sein, um Kühe melken zu können.");
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }
}
