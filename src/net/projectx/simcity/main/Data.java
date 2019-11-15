package net.projectx.simcity.main;


import net.projectx.simcity.functions.Tablist;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

/**
 * ~Yannick on 12.11.2019 at 21:43 o´ clock
 */
public class Data {
    public static String symbol = "§8 » §7";
    public static String prefix = "§5Project§6X " + symbol;
    public static String noperm = prefix + "§4Dazu hast du keine Berechtigung!";
    public static String consoleonly = "§4Dieser Befehl kann nur von der Knsole ausgeführt werden!";

    public static Plugin instance;
    public static Scoreboard scoreboard;
    public static Tablist tablist;
    public static Objective sidebar = scoreboard.registerNewObjective("sidebar", "sidebar");

}
