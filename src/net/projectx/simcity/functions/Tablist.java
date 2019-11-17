package net.projectx.simcity.functions;

import net.projectx.simcity.functions.mysql.MySQL_User;
import net.projectx.simcity.main.Data;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.UUID;

import static net.projectx.simcity.main.Data.instance;

/**
 * Created by Yannick who could get really angry if somebody steal his code!
 * ~Yannick on 09.06.2019 at 11:47 o´ clock
 */
public class Tablist {
    static String header;
    static String footer;
    static String port;
    static String ranks;

    private Team buergermeister;
    private Team foerster;
    private Team miner;
    private Team elektriker;
    private Team schmied;
    private Team farmer;
    private Team zuechter;
    private Team arbeitslos;


    private HashMap<Player, String> rankColor = new HashMap<>();

    public Tablist() {
        header = "§8« " + Data.prefix.replaceAll(Data.symbol, "") + "§8 »";
        footer = "§eSimCity 1.0";
        port = "\n§7Port: §e" + Bukkit.getPort();
    }


    public void createTablist(Scoreboard sb) {

        this.buergermeister = sb.getTeam("1a") == null ? sb.registerNewTeam("1a") : sb.getTeam("1a");
        this.foerster = sb.getTeam("2b") == null ? sb.registerNewTeam("2b") : sb.getTeam("2b");
        this.miner = sb.getTeam("3c") == null ? sb.registerNewTeam("3c") : sb.getTeam("3c");
        this.elektriker = sb.getTeam("4d") == null ? sb.registerNewTeam("4d") : sb.getTeam("4d");
        this.schmied = sb.getTeam("5e") == null ? sb.registerNewTeam("5e") : sb.getTeam("5e");
        this.farmer = sb.getTeam("6f") == null ? sb.registerNewTeam("6f") : sb.getTeam("6f");
        this.zuechter = sb.getTeam("7g") == null ? sb.registerNewTeam("7g") : sb.getTeam("7g");
        this.arbeitslos = sb.getTeam("8h") == null ? sb.registerNewTeam("8h") : sb.getTeam("8h");


        this.buergermeister.setPrefix("§8[§1Bürgermeister§8]§7 ");
        this.foerster.setPrefix("§8[§2Förster§8]§7 ");
        this.miner.setPrefix("§8[§3Miner§8]§7 ");
        this.elektriker.setPrefix("§8[§4Elektriker§8]§7 ");
        this.schmied.setPrefix("§8[§bSchmied§8]§7 ");
        this.farmer.setPrefix("§8[§5Farmer§8]§7 ");
        this.zuechter.setPrefix("§8[§dTierzüchter§8]§7 ");
        this.arbeitslos.setPrefix("§8[§0Arbeitslos§1]§7 ");
    }


    public void setTablist(UUID uuid) {
        Player p = Bukkit.getPlayer(uuid);

        if (Bukkit.getPlayer(uuid).hasPermission("px.tab.serversettings")) {
            p.setPlayerListHeader(header + port);
        } else p.setPlayerListHeader(header);

        p.setPlayerListFooter(footer);
    }


    public void setPlayer(Player p, Scoreboard sb) {
        String team = "";
        team = "";
        if (MySQL_User.getJob(p.getUniqueId()).equalsIgnoreCase("Buergermeister")) {
            team = "1a";
        } else if (MySQL_User.getJob(p.getUniqueId()).equalsIgnoreCase("Foerster")) {
            team = "2b";
        } else if (MySQL_User.getJob(p.getUniqueId()).equalsIgnoreCase("Miner")) {
            team = "3c";
        } else if (MySQL_User.getJob(p.getUniqueId()).equalsIgnoreCase("Elektriker")) {
            team = "4d";
        } else if (MySQL_User.getJob(p.getUniqueId()).equalsIgnoreCase("Schmied")) {
            team = "5e";
        } else if (MySQL_User.getJob(p.getUniqueId()).equalsIgnoreCase("Farmer")) {
            team = "6f";
        } else if (MySQL_User.getJob(p.getUniqueId()).equalsIgnoreCase("Tierzuechter")) {
            team = "7g";
        } else {
            team = "8h";
        }
        if (!sb.getTeam(team).hasPlayer(Bukkit.getOfflinePlayer(p.getUniqueId())))
            sb.getTeam(team).addPlayer(Bukkit.getOfflinePlayer(p.getUniqueId()));
        if (!sb.getTeam(team).hasEntry(p.getName())) sb.getTeam(team).addEntry(p.getName());
        rankColor.put(p, sb.getTeam(team).getPrefix());

        String name = "";
        name = String.valueOf(sb.getTeam(team).getPrefix()) + p.getName();
        System.out.println(name);
        ChatColor.translateAlternateColorCodes('§', name);

        p.setPlayerListName(name);
        p.setDisplayName(name);
        p.setCustomName(name);
        p.setCustomNameVisible(true);
        p.setScoreboard(sb);
        Bukkit.getScheduler().runTaskTimer(instance, () -> {
            Bukkit.getOnlinePlayers().forEach(pl -> pl.setScoreboard(sb));
        }, 1, 1);
    }

    /*
    public String getPrefix(Player p) {
        if (rankColor.containsKey(p)) {
            return rankColor.get(p);
        } else {
            String team;
            if (p.hasPermission("px.color.admin")) {
                team = "1a";
            } else if (p.hasPermission("px.color.mod")) {
                team = "2b";
            } else if (p.hasPermission("px.color.dev")) {
                team = "3c";
            } else if (p.hasPermission("px.color.sup")) {
                team = "4d";
            } else if (p.hasPermission("px.color.builder")) {
                team = "5e";
            } else if (p.hasPermission("px.color.youtuber")) {
                team = "6f";
            } else if (p.hasPermission("px.color.premium")) {
                team = "7g";
            } else {
                team = "8h";
            }
            return sb.getTeam(team).getPrefix();
        }
    }
    */

}
