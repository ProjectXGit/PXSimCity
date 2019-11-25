package net.projectx.simcity.functions.commands;

import net.projectx.simcity.functions.mysql.MySQL_User;
import net.projectx.simcity.main.Data;
import net.projectx.simcity.util.command.PXCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
        add("give", "Gibt Dukaten an einen anderen Spieler!");
        add("add", "Fürgt Dukaten hinzu");
        add("remove", "Entfernt Dukaten");
        add("set", "Stetzt Dukaten");
        add("get", "Gibt die Dukaten aus");
        sender.sendMessage(builder.toString());
    }

    @PXCommand(
            name = "give",
            usage = "/dukaten give <Dukaten> <Player>",
            minArgs = 2,
            maxArgs = 2,
            parent = "dukaten",
            noConsole = true
    )
    public void give(Player p, int dukaten, String name) {
        if (MySQL_User.isUserExists(name)) {
            if (MySQL_User.getDukaten(p.getUniqueId()) >= dukaten) {
                MySQL_User.removeDukaten(dukaten, p.getUniqueId());
                MySQL_User.addDukaten(dukaten, MySQL_User.getUUID(name));
                Bukkit.getOnlinePlayers().forEach(entry -> {
                    if (entry.getName().equals(name)) {
                        entry.sendMessage(prefix + "§aDir wurden §e" + dukaten + "§a von §e" + p.getName() + "§a gegeben!");
                    }
                });
                p.sendMessage(prefix + "§aDu hast §e" + dukaten + "§a Dukaten an §e" + name + "§a abgegeben!");
            } else {
                p.sendMessage(prefix + "§cDu hast nicht genug Dukaten!");
            }
        } else {
            p.sendMessage(prefix + "Der Spieler existiert nicht!");
        }
    }

    @PXCommand(
            name = "add",
            usage = "/dukaten add <Dukaten> <Player>",
            maxArgs = 2,
            minArgs = 2,
            parent = "dukaten",
            requiresOp = true
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
            parent = "dukaten",
            requiresOp = true
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
            parent = "dukaten",
            requiresOp = true
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
            parent = "dukaten",
            requiresOp = true
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
