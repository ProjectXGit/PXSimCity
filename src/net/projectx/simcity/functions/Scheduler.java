package net.projectx.simcity.functions;

import net.md_5.bungee.api.ChatColor;
import net.projectx.simcity.functions.mysql.MySQL_User;
import net.projectx.simcity.main.Data;
import net.projectx.simcity.util.Fireworkgenerator;
import net.projectx.simcity.util.ScoreboardSign;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;


/**
 * ~Yannick on 15.11.2019 at 21:52 o´ clock
 */
public class Scheduler {
    public static BukkitTask scheduler;
    static int animation = 0;
    static int ticks = 0;
    static String animation0 = "§f§o❄❄☃❄❄☃❄❄☃❄❄☃❄❄☃";
    static String animation1 = "§f§o❄☃❄❄☃❄❄☃❄❄☃❄❄☃❄";
    static String animation2 = "§f§o☃❄❄☃❄❄☃❄❄☃❄❄☃❄❄";


    public static void startScheduler() {
        scheduler = Bukkit.getScheduler().runTaskTimer(Data.instance, new Runnable() {
            @Override
            public void run() {
                ticks++;
                if (ticks == 21) {
                    ticks = 0;
                }
                if (ticks == 0) {
                    animation++;
                    if (animation == 3) {
                        animation = 0;
                    }
                    String a = getAnimation();
                    for (Player p : boards.keySet()) {
                        ScoreboardSign ss = boards.get(p);
                        ss.setLine(1, "     §7Dukaten: ");
                        ss.setLine(2, "     §6" + MySQL_User.getDukaten(p.getUniqueId()));
                        ss.setLine(4, "     §7Job: ");
                        if (MySQL_User.getJob(p.getUniqueId()).length() > 1) {
                            ss.setLine(5, "     §6" + MySQL_User.getJob(p.getUniqueId()));
                        } else {
                            ss.setLine(5, "     §4Arbeitslos");
                        }
                        ss.setLine(7, "     §7Spielzeit:");
                        if (Data.playtime.containsKey(p)) {
                            ss.setLine(8, "     " + toUnits(Data.playtime.get(p)));
                            Data.playtime.put(p, Data.playtime.get(p) + 1);
                        } else {
                            ss.setLine(8, "     §4Nicht vorhanden!");
                        }
                        ss.setLine(0, "§f§o" + a + ChatColor.DARK_BLUE);
                        ss.setLine(3, "§f§o" + a + ChatColor.AQUA);
                        ss.setLine(6, "§f§o" + a + ChatColor.BLACK);
                        ss.setLine(9, "§f§o" + a + ChatColor.BLUE);
                    }
                }
                if (ticks % 7 == 0) {
                    firework();
                }
            }
        }, 0, 1);
    }

    public static void stopScheduler() {
        scheduler.cancel();
    }

    public static HashMap<Player, ScoreboardSign> boards = new HashMap<>();

    public static void createScoreboard(Player p) {
        String a = getAnimation();
        ScoreboardSign ss = new ScoreboardSign(p, "§a" + p.getName());
        ss.create();
        ss.setLine(0, "§f§o" + a + ChatColor.DARK_BLUE);
        ss.setLine(1, "     §7Dukaten: ");
        ss.setLine(2, "     §6" + MySQL_User.getDukaten(p.getUniqueId()));
        ss.setLine(3, "§f§o" + a + ChatColor.AQUA);
        ss.setLine(4, "     §7Job: ");
        if (MySQL_User.getJob(p.getUniqueId()).length() > 1) {
            ss.setLine(5, "     §6" + MySQL_User.getJob(p.getUniqueId()));
        } else {
            ss.setLine(5, "     §4Arbeitslos");
        }
        ss.setLine(6, "§f§o" + a + ChatColor.BLACK);
        ss.setLine(7, "     §7Spielzeit:");
        ss.setLine(8, "     " + toUnits(Data.playtime.get(p)));
        ss.setLine(9, "§f§o" + a + ChatColor.BLUE);
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

    public static void firework() {
        Random random = new Random();

        Iterator iterator = Data.fireworks.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry me2 = (Map.Entry) iterator.next();
            Location loc = (Location) me2.getKey();
            int fireworks = (int) me2.getValue();
            Location spawn = new Location(Bukkit.getWorld("world"), 0, 0, 0);
            spawn.setX(loc.getX() + (random.nextInt(11) - 5));
            spawn.setZ(loc.getZ() + (random.nextInt(11) - 5));
            spawn.setY(loc.getWorld().getHighestBlockAt(spawn.getBlockX(), spawn.getBlockZ()).getY() + 2);


            Fireworkgenerator fireworkgenerator = new Fireworkgenerator(Data.instance);
            fireworkgenerator.setLocation(spawn);
            fireworkgenerator.setLifeTime(30);
            fireworkgenerator.setPower(60);
            FireworkEffect.Type type;
            switch (random.nextInt(5)) {
                case 0:
                    type = FireworkEffect.Type.BALL;
                    break;
                case 1:
                    type = FireworkEffect.Type.BALL_LARGE;
                    break;
                case 2:
                    type = FireworkEffect.Type.BURST;
                    break;
                case 3:
                    type = FireworkEffect.Type.CREEPER;
                    break;
                default:
                    type = FireworkEffect.Type.STAR;
                    break;
            }
            fireworkgenerator.setEffect(FireworkEffect.builder().withColor(Color.fromBGR(random.nextInt(256), random.nextInt(256), random.nextInt(256))).with(type).withFlicker().withTrail().withColor(Color.fromBGR(random.nextInt(256), random.nextInt(256), random.nextInt(256))).withColor(Color.fromBGR(random.nextInt(256), random.nextInt(256), random.nextInt(256))).build());
            fireworkgenerator.spawn();
            if (fireworks - 1 == 0) {
                Data.fireworks.remove(loc);
            } else {
                Data.fireworks.put(loc, fireworks - 1);
            }
        }
    }

    public static String getAnimation() {
        switch (animation) {
            case 0:
                return animation0;
            case 1:
                return animation1;
            case 2:
                return animation2;
        }
        return null;
    }


}
