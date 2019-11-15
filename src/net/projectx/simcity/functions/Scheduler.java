package net.projectx.simcity.functions;

import net.projectx.simcity.functions.mysql.MySQL_User;
import net.projectx.simcity.main.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Score;

import static net.projectx.simcity.main.Data.sidebar;

/**
 * ~Yannick on 15.11.2019 at 21:52 o´ clock
 */
public class Scheduler {
    public static BukkitTask scheduler;

    public static void startScheduler() {
        scheduler = Bukkit.getScheduler().runTaskTimer(Data.instance, new Runnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(entry -> {
                    updateScoreboard(entry);
                });
            }
        }, 20, 0);
    }

    public static void stopScheduler() {
        scheduler.cancel();
    }


    public static void updateScoreboard(Player p) {
        sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);
        sidebar.setDisplayName("§a" + p.getName());
        Score score6 = sidebar.getScore("§7Dukaten");
        score6.setScore(6);
        Score score5 = sidebar.getScore("§6" + MySQL_User.getDukaten(p.getUniqueId()));
        score5.setScore(5);
        Score score4 = sidebar.getScore("");
        score4.setScore(4);
        Score score3 = sidebar.getScore("§7Beruf:");
        score3.setScore(3);
        Score score2 = sidebar.getScore("§6" + MySQL_User.getJob(p.getUniqueId()));
        score2.setScore(2);
        Score score1 = sidebar.getScore("");
        score1.setScore(1);

    }
}
