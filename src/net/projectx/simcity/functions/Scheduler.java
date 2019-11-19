package net.projectx.simcity.functions;

import net.projectx.simcity.functions.mysql.MySQL_User;
import net.projectx.simcity.main.Data;
import net.projectx.simcity.util.ScoreboardSign;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;


/**
 * ~Yannick on 15.11.2019 at 21:52 o´ clock
 */
public class Scheduler {
    public static BukkitTask scheduler;

    public static void startScheduler() {
        scheduler = Bukkit.getScheduler().runTaskTimer(Data.instance, new Runnable() {
            @Override
            public void run() {
                for (Player p : boards.keySet()) {
                    ScoreboardSign ss = boards.get(p);
                    ss.setLine(1, "§6" + MySQL_User.getDukaten(p.getUniqueId()));
                    if (MySQL_User.getJob(p.getUniqueId()).length() > 1) {
                        ss.setLine(4, "§6" + MySQL_User.getJob(p.getUniqueId()));
                    } else {
                        ss.setLine(4, "§4Arbeitslos");
                    }
                }
            }
        }, 0, 20);
    }

    public static void stopScheduler() {
        scheduler.cancel();
    }

    public static HashMap<Player, ScoreboardSign> boards = new HashMap<>();

    public static void createScoreboard(Player p) {
        ScoreboardSign ss = new ScoreboardSign(p, "§a" + p.getName());
        ss.create();
        ss.setLine(0, "§7Dukaten: ");
        ss.setLine(1, "§6" + MySQL_User.getDukaten(p.getUniqueId()));
        ss.setLine(2, "");
        ss.setLine(3, "§7Job: ");
        if (MySQL_User.getJob(p.getUniqueId()).length() > 1) {
            ss.setLine(4, "§6" + MySQL_User.getJob(p.getUniqueId()));
        } else {
            ss.setLine(4, "§4Arbeitslos");
        }
        ss.setLine(5, "");
        ss.setLine(6, "  §e§lSimCity1.0");
        boards.put(p, ss);
    }


}
