package net.projectx.simcity.functions.berufe;

import net.projectx.simcity.functions.mysql.MySQL_User;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import java.util.Random;
import java.util.UUID;

public class Zuechter implements Listener {

    @EventHandler
    public void Tierkiller(EntityDeathEvent event) {
        Random random = new Random();
        LivingEntity dead = event.getEntity();
        System.out.println("Tier gestorben!");
        if (event.getEntity().getKiller() instanceof Player) {
            System.out.println("Tier von Spieler getötet!");
            Player p = event.getEntity().getKiller();
            UUID uuid = p.getUniqueId();

            if (dead instanceof Animals) {
                System.out.println("War kein Mob!");
                if (!MySQL_User.getJob(uuid).equalsIgnoreCase("Zuechter")) {
                    System.out.println("War kein Züchter");
                    int rand = random.nextInt(5);
                    System.out.println("Zufallszahl: " + rand);
                    if (rand == 0) {
                        return;
                    } else {
                        System.out.println("Drop soll gecleared werden!");
                        event.getDrops().clear();
                        return;
                    }
                }
            } else {
                System.out.println("War ein Mob!");
            }
        }
    }


    @EventHandler
    public void LiebeMachen(PlayerInteractAtEntityEvent event) {
        Player p = event.getPlayer();
        UUID uuid = p.getUniqueId();
        Entity tier = event.getRightClicked();

        if (tier instanceof Animals) {

            if (((Animals) tier).isLoveMode()) {
                if (!MySQL_User.getJob(uuid).equalsIgnoreCase("Zuechter")) {
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }
}
