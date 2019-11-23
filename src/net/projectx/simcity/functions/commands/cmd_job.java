package net.projectx.simcity.functions.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.projectx.simcity.functions.Plot;
import net.projectx.simcity.functions.mysql.MySQL_User;
import net.projectx.simcity.main.Data;
import net.projectx.simcity.util.command.PXCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import java.util.ArrayList;
import java.util.UUID;

import static net.projectx.simcity.main.Data.prefix;


public class cmd_job {
    StringBuilder builder;

    @PXCommand(
            name = "job",
            aliases = "j",
            minArgs = 0,
            maxArgs = 0
    )
    public void execute(CommandSender sender){
        builder = new StringBuilder();
        builder.append(prefix +  "§7§lHilfsübersicht:§r\n");
        add("list","Listet alle Jobs auf");
        add("get","Wählt einen Job aus");
        add("delete","Macht dich arbeitslos");
        add("player","Zeigt alle Spieler mit diesem Job an.");
        sender.sendMessage(builder.toString());

    }

    @PXCommand(
            name = "list",
            usage = "job list",
            minArgs = 0,
            maxArgs = 0,
            parent = "job"

    )
    public void list(CommandSender sender){
        builder = new StringBuilder();
        builder.append(prefix + "§7§lJobübersicht:§r\n");
        add2("§1Buergermeister§7: Hat Macht über Grundstücke und Spieler");
        add2("§4Elektriker§7: Redstone-Schaltungen sind seine Spezialität");
        add2("§5Farmer§7: Meister der Pflanzen");
        add2("§2Foerster§7: Die Waldrodung in Person");
        add2("§3Miner§7: Er weiß, wie man Erze richtig abbaut");
        add2("§bSchmied§7: Niemand schmiedet bessere Rüstungen");
        add2("§dTierzuechter§7: Für die Massentierhaltung zuständig");
        sender.sendMessage(builder.toString());
    }

    @PXCommand(
            name = "get",
            usage = "job get <job>",
            minArgs = 1,
            maxArgs = 1,
            parent = "job",
            noConsole = true
    )
    public void get(CommandSender sender, String jobs){
        String job = jobs;
        Player p = (Player) sender;
        UUID uuid = p.getUniqueId();
        if(jobs.equalsIgnoreCase("Foerster")||jobs.equalsIgnoreCase("Farmer")||jobs.equalsIgnoreCase("Buergermeister")||jobs.equalsIgnoreCase("Elektriker")||jobs.equalsIgnoreCase("Miner")||jobs.equalsIgnoreCase("Schmied")||jobs.equalsIgnoreCase("Tierzuechter")) {
            TextComponent component = new TextComponent();
            component.setText(prefix + "§aKlicke §ehier§a um den Job §e" + jobs + "§a auszuwählen!");
            component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/job confirm " + jobs));
            component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§eHier Klicken!!").create()));
            p.spigot().sendMessage(component);
        }else{
            p.sendMessage("§cDen Job gibt es nicht.");
        }
    }

    @PXCommand(
            name = "confirm",
            minArgs = 1,
            maxArgs = 1,
            usage = "/job confirm <job>",
            parent = "job",
            noConsole = true
    )
    public void confirm(CommandSender sender, String jobs) {
        String job = jobs;
        Player p = (Player) sender;
        UUID uuid = p.getUniqueId();
        if (jobs.equalsIgnoreCase("Foerster")||jobs.equalsIgnoreCase("Farmer")||jobs.equalsIgnoreCase("Buergermeister")||jobs.equalsIgnoreCase("Elektriker")||jobs.equalsIgnoreCase("Miner")||jobs.equalsIgnoreCase("Schmied")||jobs.equalsIgnoreCase("Tierzuechter")) {
            MySQL_User.setJob(job, uuid);
            p.sendMessage("§eDu arbeitetest jetzt als "+ MySQL_User.getJob(uuid));
        }else{
            p.sendMessage("§cDen Job gibt es nicht.");
        }
    }

    @PXCommand(
            name = "delete",
            usage = "job delete",
            minArgs = 0,
            maxArgs = 0,
            parent = "job",
            noConsole = true
    )
    public void delete(CommandSender sender){
        Player p = (Player) sender;
        UUID uuid = p.getUniqueId();
        MySQL_User.setJob("Arbeitslos",uuid);
        p.sendMessage("§cDu ist jetzt ein arbeitsloser Schlucker");
    }

    @PXCommand(
            name = "player",
            usage = "job player <job>",
            minArgs = 1,
            maxArgs = 1,
            parent = "job"
    )
    public void player(CommandSender sender, String jobs){
        Player p = (Player) sender;
        UUID uuid = p.getUniqueId();
        ArrayList<String> names = MySQL_User.getName(jobs);
        for(int i=0; i<names.size();i++) {
            p.sendMessage(names.get(i));
        }
    }

    private void add(String command, String usage) {
        builder.append("\n" + Data.symbol + "§e/job " + command + "§8: §7 " + usage + ChatColor.RESET + "\n");
    }

    private void add2(String beschreibung) {
        builder.append("\n" + Data.symbol + " " + beschreibung + ChatColor.RESET + "\n");
    }


}

