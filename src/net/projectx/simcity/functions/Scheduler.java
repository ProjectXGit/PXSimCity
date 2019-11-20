package net.projectx.simcity.functions;

import net.md_5.bungee.api.ChatColor;
import net.projectx.simcity.functions.mysql.MySQL_User;
import net.projectx.simcity.main.Data;
import net.projectx.simcity.util.ScoreboardSign;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.text.DecimalFormat;
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
                    ss.setLine(0, "§7Dukaten: ");
                    ss.setLine(1, "§6" + MySQL_User.getDukaten(p.getUniqueId()));
                    ss.setLine(2, "§f§o❄❄❄❄❄❄❄❄❄❄❄❄❄" + ChatColor.AQUA);
                    ss.setLine(3, "§7Job: ");
                    if (MySQL_User.getJob(p.getUniqueId()).length() > 1) {
                        ss.setLine(4, "§6" + MySQL_User.getJob(p.getUniqueId()));
                    } else {
                        ss.setLine(4, "§4Arbeitslos");
                    }
                    ss.setLine(5, "§f§o❄❄❄❄❄❄❄❄❄❄❄❄❄" + ChatColor.BLACK);
                    ss.setLine(6, "§7Spielzeit:");
                    if (Data.playtime.containsKey(p)) {
                        ss.setLine(7, toUnits(Data.playtime.get(p)));
                        Data.playtime.put(p, Data.playtime.get(p) + 1);
                    } else {
                        ss.setLine(7, "§4Nicht vorhanden!");
                    }
                    ss.setLine(8, "§f§k❄❄❄❄❄❄❄❄❄❄❄❄❄" + ChatColor.BLUE);
                    ss.setLine(9, "  §e§lSimCity1.0");

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
        ss.setLine(2, "§f§o❄❄❄❄❄❄❄❄❄❄❄❄❄" + ChatColor.AQUA);
        ss.setLine(3, "§7Job: ");
        if (MySQL_User.getJob(p.getUniqueId()).length() > 1) {
            ss.setLine(4, "§6" + MySQL_User.getJob(p.getUniqueId()));
        } else {
            ss.setLine(4, "§4Arbeitslos");
        }
        ss.setLine(5, "§f§o❄❄❄❄❄❄❄❄❄❄❄❄❄" + ChatColor.BLACK);
        ss.setLine(6, "§7Spielzeit:");
        ss.setLine(7, toUnits(Data.playtime.get(p)));
        ss.setLine(8, "§f§k❄❄❄❄❄❄❄❄❄❄❄❄❄" + ChatColor.BLUE);
        ss.setLine(9, "  §e§lSimCity1.0");
        boards.put(p, ss);
    }

    public static String toUnits(long zeit) {
        long years = zeit / (60 * 60 * 24 * 7 * 31 * 12);
        zeit = zeit % (60 * 60 * 24 * 7 * 31 * 12);
        long months = zeit / (60 * 60 * 24 * 7 * 31);
        zeit = zeit % (60 * 60 * 24 * 7 * 31);
        long weeks = zeit / (60 * 60 * 24 * 7);
        zeit = zeit % (60 * 60 * 24 * 7);
        long days = zeit / (60 * 60 * 24);
        zeit = zeit % (60 * 60 * 24);
        long hours = zeit / (60 * 60);
        zeit = zeit % (60 * 60);
        long minutes = zeit / 60;
        zeit = zeit % 60;

        DecimalFormat format = new DecimalFormat("00");

        String message = "";
        if (years != 0) message = message + "§6" + format.format(years) + "§8:";
        if (months != 0) message = message + "§6" + format.format(months) + "§8:";
        if (weeks != 0) message = message + "§6" + format.format(weeks) + "§8:";
        if (days != 0) message = message + "§6" + format.format(days) + "§8:";
        if (hours != 0) message = message + "§6" + format.format(hours) + "§8:";
        if (minutes != 0) message = message + "§6" + format.format(minutes) + "§8:";
        message = message + "§6" + format.format(zeit);
        return message;
    }


}
