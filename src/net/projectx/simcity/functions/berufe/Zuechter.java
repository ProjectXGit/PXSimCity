package net.projectx.simcity.functions.berufe;

import net.projectx.simcity.functions.mysql.MySQL_User;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.Random;
import java.util.UUID;

public class Zuechter implements Listener {

    @EventHandler
    public void Tierkiller(EntityDeathEvent event){
        Random random = new Random();
        Entity dead = event.getEntity();
        if(dead.getLastDamageCause() instanceof Player) {
            Player p = (Player) dead.getLastDamageCause();
            UUID uuid = p.getUniqueId();

                if (!(dead instanceof Mob)) {
                    if (!MySQL_User.getJob(uuid).equalsIgnoreCase("Zuechter")) {
                        if (random.nextInt(5) == 0) {
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
    public void LiebeMachen(PlayerInteractEntityEvent event){
        Player p = event.getPlayer();
        UUID uuid = p.getUniqueId();
        Entity tier = event.getRightClicked();

        if(tier instanceof Animals) {

            if (((Animals) tier).isLoveMode()) {
                if (!MySQL_User.getJob(uuid).equalsIgnoreCase("Zuechter")) {
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }
}
