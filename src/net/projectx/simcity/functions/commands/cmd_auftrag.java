package net.projectx.simcity.functions.commands;

import net.projectx.simcity.functions.Plot;
import net.projectx.simcity.functions.mysql.MySQL_Auftrag;
import net.projectx.simcity.functions.mysql.MySQL_Plot;
import net.projectx.simcity.functions.mysql.MySQL_User;
import net.projectx.simcity.main.Data;
import net.projectx.simcity.util.command.PXCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.math.BigInteger;

import static net.projectx.simcity.main.Data.prefix;

public class cmd_auftrag {
    StringBuilder builder;

    @PXCommand(
            name = "auftrag",
            minArgs = 0,
            maxArgs = 0
    )
    public void execute(CommandSender sender){
        builder = new StringBuilder();
        builder.append(prefix +  "§7§lHilfsübersicht:§r\n");
        add("create", "Erstellt einen Auftrag");
        add("delete", "Löscht einen Auftrag");
        add("list", "Gibt alle Aufträge aus");
        add("accept", "Nimmt einen Auftrag an");
        add("reject", "Lehnt einen Auftrag ab");
        add("belohnungChange", "Ändert die Belohnung");
        sender.sendMessage(builder.toString());
    }

    @PXCommand(
            name = "list",
            minArgs = 1,
            maxArgs = 1,
            usage = "auftrag list <all/frei/beschreibung>",
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
                sender.sendMessage(prefix + "§cEs wurde noch kein Auftrag eingetragen!");
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
            minArgs = 3,
            maxArgs = 3,
            usage = "auftrag create <auftragname> <auftragsbeschreibung> <belohnung>",
            parent = "auftrag"
    )
    public void create(CommandSender sender, String auftragname, String auftragsbeschreibung, String plot, BigInteger belohnung){
        MySQL_Auftrag.createAuftrag(auftragname, auftragsbeschreibung, plot, belohnung);
    }

    @PXCommand(
            name = "delete",
            minArgs = 1,
            maxArgs = 1,
            usage = "auftrag delete <auftragname>",
            parent = "auftrag"
    )
    public void delete(CommandSender sender, String auftragname){
        MySQL_Auftrag.deleteAuftrag(auftragname);
    }

    @PXCommand(
            name = "accept",
            usage = "auftrag accept <auftragname>",
            minArgs = 1,
            maxArgs = 1,
            parent = "auftrag"
    )
    public void accept(CommandSender sender){

    }

    private void add(String command, String usage) {
        builder.append("\n" + Data.symbol + "§e/auftrag " + command + "§8: §7 " + usage + ChatColor.RESET + "\n");
    }

}
