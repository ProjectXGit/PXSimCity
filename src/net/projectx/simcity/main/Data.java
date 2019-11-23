package net.projectx.simcity.main;


import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import net.projectx.simcity.functions.Tablist;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;

/**
 * ~Yannick on 12.11.2019 at 21:43 o´ clock
 */
public class Data {
    public static String symbol = "§8 » §7";
    public static String prefix = "§5Project§6X " + symbol;
    public static String noperm = prefix + "§4Dazu hast du keine Berechtigung!";
    public static String consoleonly = "§4Dieser Befehl kann nur von der Konsole ausgeführt werden!";

    public static Plugin instance;
    public static Tablist tablist;
    public static Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
    public static HashMap<Player, Long> playtime = new HashMap<>();
    public static HashMap<Location, Integer> fireworks = new HashMap<>();
    public static WorldEditPlugin wedit;
    public static WorldGuardPlugin wguard;
    public static RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
    public static RegionManager regions = container.get(BukkitAdapter.adapt(Bukkit.getWorld("world")));
}
