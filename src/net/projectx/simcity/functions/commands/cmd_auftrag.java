package net.projectx.simcity.functions.commands;

import com.sun.imageio.plugins.common.I18N;
import net.projectx.simcity.functions.Plot;
import net.projectx.simcity.functions.mysql.MySQL_Auftrag;
import net.projectx.simcity.functions.mysql.MySQL_Plot;
import net.projectx.simcity.functions.mysql.MySQL_User;
import net.projectx.simcity.main.Data;
import net.projectx.simcity.util.command.PXCommand;
import org.bukkit.ChatColor;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigInteger;
import java.util.UUID;

import static net.projectx.simcity.main.Data.prefix;

public class cmd_auftrag {
    StringBuilder builder;

    @PXCommand(
            name = "auftrag",
            minArgs = 0,
            maxArgs = 0
    )
    public void execute(CommandSender sender) {
        builder = new StringBuilder();
        builder.append(prefix + "§7§lHilfsübersicht:§r\n");
        add("create", "Erstellt einen Auftrag");
        add("delete", "Löscht einen Auftrag");
        add("list", "Gibt alle Aufträge aus");
        add("belohnung", "Gibt die Belohung eines Auftrags aus");
        add("beschreibung", "Gibt die Beschreibung eines Auftrags aus");
        add("accept", "Nimmt einen Auftrag an");
        add("reject", "Lehnt einen Auftrag ab");
        add("belohnungChange", "Ändert die Belohnung");
        add("abschluss", "Der Auftrag wird abgeschlossen und der Spieler erhält die Belohnung.");
        sender.sendMessage(builder.toString());
    }

    @PXCommand(
            name = "list",
            minArgs = 1,
            maxArgs = 1,
            usage = "/auftrag list <all/frei>",
            parent = "auftrag"
    )
    public void list(CommandSender sender, String args) {
        final String[] auftrag = {""};
        if (args.equals("all")) {
            MySQL_Auftrag.getAuftraege().forEach(entry -> {
                auftrag[0] = auftrag[0] + "§e" + entry + "§7, ";
            });
            if (auftrag[0].length() != 0) {
                sender.sendMessage(prefix + auftrag[0]);
            } else {
                sender.sendMessage(prefix + "§cEs gibt keinen Auftrag!");
            }
        } else if (args.equals("frei")) {
            MySQL_Auftrag.getAuftraegeFrei().forEach(entry -> {
                if (Plot.isPlotPurchaseable(entry)) {
                    auftrag[0] = auftrag[0] + "§e" + entry + "§7, ";
                }
            });
            if (auftrag[0].length() != 0) {
                sender.sendMessage(prefix + auftrag[0]);
            } else {
                sender.sendMessage(prefix + "§cEs gibt keinen freien Auftrag");
            }
        }
    }

    @PXCommand(
            name = "create",
            minArgs = 4,
            maxArgs = 4,
            usage = "/auftrag create <auftragname> <auftragsbeschreibung> <plot> <belohnung>",
            parent = "auftrag",
            noConsole = true
    )
    public void create(CommandSender sender, String auftragname, String auftragsbeschreibung, String plot, Integer belohnung) {
        Player p = (Player) sender;
        UUID uuid = p.getUniqueId();
        if (!MySQL_Auftrag.getAuftraege().contains(auftragname)) {
            if (MySQL_Plot.getPlots().contains(plot)) {
                MySQL_Auftrag.createAuftrag(auftragname, auftragsbeschreibung, plot, belohnung);
                MySQL_Plot.addMember(uuid, plot);
            } else {
                sender.sendMessage("§cDieses Grundstück gibt es nicht.");
            }
        } else {
            sender.sendMessage("§cEs gibt bereits einen Auftrag mit diesem Namen.");
        }
    }

    @PXCommand(
            name = "delete",
            minArgs = 1,
            maxArgs = 1,
            usage = "/auftrag delete <auftragname>",
            parent = "auftrag"
    )
    public void delete(CommandSender sender, String auftragname) {
        if (MySQL_Auftrag.getAuftraege().contains(auftragname)) {
            MySQL_Auftrag.deleteAuftrag(auftragname);
        } else {
            sender.sendMessage("§cDiesen Auftrag gibt es nicht.");
        }
    }

    @PXCommand(
            name = "accept",
            usage = "/auftrag accept <auftragname>",
            minArgs = 1,
            maxArgs = 1,
            parent = "auftrag",
            noConsole = true
    )
    public void accept(CommandSender sender, String auftragname) {
        if (MySQL_Auftrag.getAuftraege().contains(auftragname)) {
            if (MySQL_Auftrag.getAuftraegeFrei().contains(auftragname)) {
                MySQL_Auftrag.acceptAuftrag(auftragname, sender.getName());
            } else {
                sender.sendMessage("§cDieser Auftrag ist bereits an " + MySQL_Auftrag.getAuftragOwner(auftragname) + "vergeben.");
            }
        } else {
            sender.sendMessage("§cDiesen Auftrag gibt es nicht.");
        }
    }

    @PXCommand(
            name = "reject",
            usage = "/auftrag reject <auftragname>",
            minArgs = 1,
            maxArgs = 1,
            parent = "auftrag",
            noConsole = true
    )
    public void reject(CommandSender sender, String auftragname) {
        if (MySQL_Auftrag.getAuftraege().contains(auftragname)) {
            if (!MySQL_Auftrag.getAuftraegeFrei().contains(auftragname)) {
                if (!MySQL_Auftrag.getAuftragOwner(auftragname).equals(sender.getName())) {
                    MySQL_Auftrag.rejectAuftrag(auftragname);
                } else {
                    sender.sendMessage("§cDiesen Auftrag hast du nicht angenommen. Er gehört " + MySQL_Auftrag.getAuftragOwner(auftragname));
                }
            } else {
                sender.sendMessage("§cDieser Auftrag wurde nicht angenommen.");
            }
        } else {
            sender.sendMessage("§cDiesen Auftrag gibt es nicht.");
        }
    }

    @PXCommand(
            name = "belohnungChange",
            usage = "/auftrag belohnungChange <auftragname> <belohnung>",
            minArgs = 2,
            maxArgs = 2,
            parent = "auftrag"
    )
    public void belohnungChange(CommandSender sender, String auftragname, BigInteger belohnung) {
        if (MySQL_Auftrag.getAuftraege().contains(auftragname)) {
            MySQL_Auftrag.changeBelohnung(auftragname, belohnung);
        } else {
            sender.sendMessage("§cDiesen Auftrag gibt es nicht.");
        }
    }

    @PXCommand(
            name = "belohnung",
            usage = "/auftrag belohnung <auftragname>",
            minArgs = 1,
            maxArgs = 1,
            parent = "auftrag"
    )
    public void belohnung(CommandSender sender, String auftragname) {
        if (MySQL_Auftrag.getAuftraege().contains(auftragname)) {
            sender.sendMessage(prefix + "§e " + auftragname + "§7:§e " + MySQL_Auftrag.getAuftragsBelohnung(auftragname));
        } else {
            sender.sendMessage("§cDiesen Auftrag gibt es nicht.");
        }
    }

    @PXCommand(
            name = "beschreibung",
            usage = "/auftrag beschreibung <auftragname>",
            minArgs = 1,
            maxArgs = 1,
            parent = "auftrag"
    )
    public void beschreibung(CommandSender sender, String auftragname) {
        if (MySQL_Auftrag.getAuftraege().contains(auftragname)) {
            sender.sendMessage(prefix + "§e " + auftragname + "§7,§e " + MySQL_Auftrag.getAuftragsBeschreibung(auftragname));
        } else {
            sender.sendMessage("§cDiesen Auftrag gibt es nicht.");
        }
    }

    @PXCommand(
            name = "abschluss",
            usage = "/auftrag abschluss <auftragname>",
            minArgs = 1,
            maxArgs = 1,
            parent = "auftrag"
    )
    public void abschluss(CommandSender sender, String auftragname) {

        if (MySQL_Auftrag.getAuftraege().contains(auftragname)) {
            if (!MySQL_Auftrag.getAuftraegeFrei().contains(auftragname)) {
                String Playername = MySQL_Auftrag.getAuftragOwner(auftragname);
                add(sender, MySQL_Auftrag.getAuftragsBelohnung(auftragname), Playername);
            }else{
                sender.sendMessage("Dieser Auftrag ist frei.");
            }
        }else{
            sender.sendMessage("§cDiesen Auftrag gibt es nicht.");
        }
    }

    private void add(String command, String usage) {
        builder.append("\n" + Data.symbol + "§e/auftrag " + command + "§8: §7 " + usage + ChatColor.RESET + "\n");
    }


    public void add(CommandSender sender, long dukaten, String name) {
        if (MySQL_User.isUserExists(name)) {
            MySQL_User.addDukaten(dukaten, MySQL_User.getUUID(name));
            sender.sendMessage("§e" + dukaten + "§a Dukaten wurden hinzugefügt!");
        } else {
            sender.sendMessage(prefix + "Der Spieler existiert nicht!");
        }
    }
}