package net.projectx.simcity.functions.commands;

import net.projectx.simcity.functions.mysql.MySQL_User;
import net.projectx.simcity.main.Data;
import net.projectx.simcity.util.command.PXCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import static net.projectx.simcity.main.Data.prefix;

/**
 * ~Yannick on 23.11.2019 at 00:13 o´ clock
 */
public class cmd_dukaten {
    StringBuilder builder;

    @PXCommand(
            name = "dukaten",
            usage = "/dukaten",
            description = "Verwaltet die Dukaten der Spieler"
    )
    public void execute(CommandSender sender) {
        builder = new StringBuilder();
        builder.append(prefix + "§7§lHilfsübersicht:§r\n");
        add("add", "Fürgt Dukaten hinzu");
        add("remove", "Entfernt Dukaten");
        add("set", "Stetzt Dukaten");
        add("get", "Gibt die Dukaten aus");
        sender.sendMessage(builder.toString());
    }

    @PXCommand(
            name = "add",
            usage = "/dukaten add <Dukaten> <Player>",
            maxArgs = 2,
            minArgs = 2,
            parent = "dukaten"
    )
    public void add(CommandSender sender, long dukaten, String name) {
        if (MySQL_User.isUserExists(name)) {
            MySQL_User.addDukaten(dukaten, MySQL_User.getUUID(name));
            sender.sendMessage("§e" + dukaten + "§a Dukaten wurden hinzugefügt!");
        } else {
            sender.sendMessage(prefix + "Der Spieler existiert nicht!");
        }
    }

    @PXCommand(
            name = "remove",
            usage = "/dukaten remove <Dukaten> <Player>",
            maxArgs = 2,
            minArgs = 2,
            parent = "dukaten"
    )
    public void remove(CommandSender sender, long dukaten, String name) {
        if (MySQL_User.isUserExists(name)) {
            MySQL_User.removeDukaten(dukaten, MySQL_User.getUUID(name));
            sender.sendMessage("§e" + dukaten + "§a Dukaten wurden abgezogen!");
        } else {
            sender.sendMessage(prefix + "Der Spieler existiert nicht!");
        }
    }

    @PXCommand(
            name = "set",
            usage = "/dukaten set <Dukaten> <Player>",
            maxArgs = 2,
            minArgs = 2,
            parent = "dukaten"
    )
    public void set(CommandSender sender, long dukaten, String name) {
        if (MySQL_User.isUserExists(name)) {
            MySQL_User.setDukaten(dukaten, MySQL_User.getUUID(name));
            sender.sendMessage("§aDer Kontostand des Users wurde auf §e " + dukaten + "§a Dukaten gesetzt!");
        } else {
            sender.sendMessage(prefix + "Der Spieler existiert nicht!");
        }
    }

    @PXCommand(
            name = "get",
            usage = "/dukaten get <Player>",
            maxArgs = 1,
            minArgs = 1,
            parent = "dukaten"
    )
    public void get(CommandSender sender, String name) {
        if (MySQL_User.isUserExists(name)) {
            sender.sendMessage("§aDer Kontostand des Users beträgt:§e " + MySQL_User.getDukaten(MySQL_User.getUUID(name)) + "§a Dukaten");
        } else {
            sender.sendMessage(prefix + "Der Spieler existiert nicht!");
        }
    }


    private void add(String command, String usage) {
        builder.append("\n" + Data.symbol + "§e/dukaten " + command + "§8: §7 " + usage + ChatColor.RESET + "\n");
    }
}
