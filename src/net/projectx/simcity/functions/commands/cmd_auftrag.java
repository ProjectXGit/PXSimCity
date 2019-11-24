package net.projectx.simcity.functions.commands;

import com.sun.imageio.plugins.common.I18N;
import net.projectx.simcity.functions.Plot;
import net.projectx.simcity.functions.mysql.MySQL_Auftrag;
import net.projectx.simcity.functions.mysql.MySQL_Plot;
import net.projectx.simcity.functions.mysql.MySQL_User;
import net.projectx.simcity.main.Data;
import net.projectx.simcity.util.command.PXCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.datatransfer.StringSelection;
import java.math.BigInteger;
import java.util.UUID;

import static net.projectx.simcity.main.Data.playtime;
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
        add("belohnungchange", "Ändert die Belohnung");
        add("beschreibung", "Gibt die Beschreibung eines Auftrags aus");
        add("setbeschreibung", "Setzt oder ändert die Beschreibung");
        add("accept", "Nimmt einen Auftrag an");
        add("reject", "Lehnt einen Auftrag ab");
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
                    auftrag[0] = auftrag[0] + "§e" + entry + "§7, ";
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
            minArgs = 2,
            maxArgs = 3,
            usage = "/auftrag create <auftragname> <belohnung> [<plot>] ",
            parent = "auftrag",
            noConsole = true
    )
    public void create(CommandSender sender, String auftragname, Integer belohnung, String plot) {
        Player p = (Player) sender;
        UUID uuid = p.getUniqueId();
       if(plot == null){
           if(!MySQL_Auftrag.getAuftraege().contains(auftragname)){
               MySQL_Auftrag.createAuftrag(auftragname, plot, belohnung);
               p.sendMessage("§eDer Auftrag§6 " + auftragname + "§e wurde erstellt.");
           }
       }else{
           if (!MySQL_Auftrag.getAuftraege().contains(auftragname)) {
               if (MySQL_Plot.getPlots().contains(plot)) {
                   MySQL_Auftrag.createAuftrag(auftragname, plot, belohnung);
                   p.sendMessage("§eDer Auftrag§6 " + auftragname + "§e wurde erstellt.");
               } else {
                   sender.sendMessage("§cDieses Grundstück gibt es nicht.");
               }
           } else {
               sender.sendMessage("§cEs gibt bereits einen Auftrag mit diesem Namen.");
           }
       }
    }

    @PXCommand(
            name = "setbeschreibung",
            usage = "/auftrag setbeschreibung <auftragname> <auftragsbeschreibung>",
            minArgs = 2,
            maxArgs = 2,
            parent = "auftrag"
    )
    public void setbeschreibung(CommandSender sender, String auftragname, String... beschreibung){
        Player p = (Player) sender;
        UUID uuid = p.getUniqueId();
        p.sendMessage("Beschreibung wird bearbeitet");
        String description = "";
        for (int i = 0;i<beschreibung.length;i++){
            description = description + " " + beschreibung[i];
        }
        p.sendMessage(description);
        MySQL_Auftrag.setBeschreibung(auftragname, description);
        p.sendMessage("Beschreibung wurde gesetzt");
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
            if(MySQL_Auftrag.getPlot(auftragname)==null) {
                MySQL_Auftrag.deleteAuftrag(auftragname);
                sender.sendMessage("§eDer Auftrag §6" + auftragname + "§e wurde gelöscht.");
            }else{
                MySQL_Plot.removeMember(MySQL_User.getUUID(MySQL_Auftrag.getAuftragOwner(auftragname)),MySQL_Auftrag.getPlot(auftragname));
                MySQL_Auftrag.deleteAuftrag(auftragname);
                sender.sendMessage("§eDer Auftrag §6" + auftragname + "§e wurde gelöscht.");

            }
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
        Player p = (Player) sender;
        UUID uuid = p.getUniqueId();
        String plot = MySQL_Auftrag.getPlot(auftragname);
        if(plot == null){
            if (MySQL_Auftrag.getAuftraege().contains(auftragname)) {
                if (MySQL_Auftrag.getAuftraegeFrei().contains(auftragname)) {
                    MySQL_Auftrag.acceptAuftrag(auftragname, sender.getName());

                    p.sendMessage("§eDu hast den Auftrag §6" + auftragname + " §eangenommen.");
                } else {
                    sender.sendMessage("§cDieser Auftrag ist bereits an §6" + MySQL_Auftrag.getAuftragOwner(auftragname) + " §cvergeben.");
                }
            } else {
                sender.sendMessage("§cDiesen Auftrag gibt es nicht.");
            }
        }else{
            if (MySQL_Auftrag.getAuftraege().contains(auftragname)) {
                if (MySQL_Auftrag.getAuftraegeFrei().contains(auftragname)) {
                    MySQL_Auftrag.acceptAuftrag(auftragname, sender.getName());
                    MySQL_Plot.addMember(uuid, plot);
                    p.sendMessage("§eDu hast den Auftrag §6" + auftragname + " §eangenommen und bist jetzt ein Mitglied des Grundstücks §6" + plot + "§e.");
                } else {
                    sender.sendMessage("§cDieser Auftrag ist bereits an §6" + MySQL_Auftrag.getAuftragOwner(auftragname) + " §cvergeben.");
                }
            } else {
                sender.sendMessage("§cDiesen Auftrag gibt es nicht.");
            }
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
        Player p = (Player) sender;
        UUID uuid = p.getUniqueId();
        String plot = MySQL_Auftrag.getPlot(auftragname);
        if(plot == null){
            if (MySQL_Auftrag.getAuftraege().contains(auftragname)) {
                if (!MySQL_Auftrag.getAuftraegeFrei().contains(auftragname)) {
                    if (MySQL_Auftrag.getAuftragOwner(auftragname).equalsIgnoreCase(p.getName())) {
                        MySQL_Auftrag.rejectAuftrag(auftragname);

                        p.sendMessage("§eDer Auftrag §6" + auftragname + " §eist jetzt wieder frei.");
                    } else {
                        sender.sendMessage("§cDiesen Auftrag hast du nicht angenommen. Er wird von §a" + MySQL_Auftrag.getAuftragOwner(auftragname) + " §ebearbeitet.");
                    }
                } else {
                    sender.sendMessage("§cDieser Auftrag ist frei.");
                }
            } else {
                sender.sendMessage("§cDiesen Auftrag gibt es nicht.");
            }
        }else {
            if (MySQL_Auftrag.getAuftraege().contains(auftragname)) {
                if (!MySQL_Auftrag.getAuftraegeFrei().contains(auftragname)) {
                    if (MySQL_Auftrag.getAuftragOwner(auftragname).equalsIgnoreCase(p.getName())) {
                        MySQL_Auftrag.rejectAuftrag(auftragname);
                        MySQL_Plot.removeMember(uuid, plot);
                        p.sendMessage("§eDer Auftrag §6" + auftragname + " §eist jetzt wieder frei. Du bist nicht mehr Mitglied des Grundstücks §6" + plot + "§e.");
                    } else {
                        sender.sendMessage("§cDiesen Auftrag hast du nicht angenommen. Er wird von §a" + MySQL_Auftrag.getAuftragOwner(auftragname) + " §ebearbeitet.");
                    }
                } else {
                    sender.sendMessage("§cDieser Auftrag ist frei.");
                }
            } else {
                sender.sendMessage("§cDiesen Auftrag gibt es nicht.");
            }
        }
    }

    @PXCommand(
            name = "belohnungchange",
            usage = "/auftrag belohnungchange <auftragname> <belohnung>",
            minArgs = 2,
            maxArgs = 2,
            parent = "auftrag"
    )
    public void belohnungchange(CommandSender sender, String auftragname, Integer belohnung) {
        if (MySQL_Auftrag.getAuftraege().contains(auftragname)) {
            MySQL_Auftrag.changeBelohnung(auftragname, belohnung);
            sender.sendMessage("§eDie Belohnung des Auftrags §6" + auftragname + " §ewurde auf " + belohnung + " geändert.");
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
            sender.sendMessage(prefix + "§e " + auftragname + "§7:§e " + MySQL_Auftrag.getAuftragsBelohnung(auftragname) + " §eDukaten.");
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
            sender.sendMessage(prefix + "§6 " + auftragname + "§7:§e " + MySQL_Auftrag.getAuftragsBeschreibung(auftragname));
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
                    delete(sender,auftragname);
            }else{
                sender.sendMessage("§cDieser Auftrag ist frei.");
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