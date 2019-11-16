package net.projectx.simcity.functions;

import net.projectx.simcity.functions.mysql.MySQL_User;
import net.projectx.simcity.main.Data;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

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
                    Scoreboard scoreboard = boards.get(p);
                    scoreboard.getTeam("dukatenanz").setPrefix("§6" + MySQL_User.getDukaten(p.getUniqueId()));
                    if (MySQL_User.getJob(p.getUniqueId()).length() > 1) {
                        scoreboard.getTeam("jobwert").setPrefix("§6" + MySQL_User.getJob(p.getUniqueId()));
                    } else {
                        scoreboard.getTeam("jobwert").setPrefix("§4Arbeitslos");
                    }
                }
            }
        }, 0, 20);
    }

    public static void stopScheduler() {
        scheduler.cancel();
    }

    public static HashMap<Player, Scoreboard> boards = new HashMap<>();


    public static void createScoreboard(Player p) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective sidebar = scoreboard.registerNewObjective("sidebar", "sidebar");
        sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);
        sidebar.setDisplayName("§a" + p.getName());
        Team dukaten = scoreboard.registerNewTeam("dukaten");
        dukaten.setPrefix("§7Dukaten");
        dukaten.addEntry(ChatColor.AQUA.toString());
        sidebar.getScore(ChatColor.AQUA.toString()).setScore(6);


        Team dukatenanz = scoreboard.registerNewTeam("dukatenanz");
        dukatenanz.setPrefix("§6" + MySQL_User.getDukaten(p.getUniqueId()));
        dukatenanz.addEntry(ChatColor.BLUE.toString());
        sidebar.getScore(ChatColor.BLUE.toString()).setScore(5);

        Team empty = scoreboard.registerNewTeam("empty");
        empty.addEntry(ChatColor.DARK_AQUA.toString());
        sidebar.getScore(ChatColor.DARK_AQUA.toString()).setScore(4);

        Team job = scoreboard.registerNewTeam("job");
        job.setPrefix("§7Job:");
        job.addEntry(ChatColor.BLACK.toString());
        sidebar.getScore(ChatColor.BLACK.toString()).setScore(3);

        Team jobwert = scoreboard.registerNewTeam("jobwert");
        System.out.println("|" + MySQL_User.getJob(p.getUniqueId()) + "|");
        if (MySQL_User.getJob(p.getUniqueId()).length() > 1) {
            jobwert.setPrefix("§6" + MySQL_User.getJob(p.getUniqueId()));
        } else {
            jobwert.setPrefix("§4Arbeitslos");
        }
        jobwert.addEntry(ChatColor.BOLD.toString());
        sidebar.getScore(ChatColor.BOLD.toString()).setScore(2);

        boards.put(p, scoreboard);
        p.setScoreboard(scoreboard);


    }
}
