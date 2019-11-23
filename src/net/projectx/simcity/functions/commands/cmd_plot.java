package net.projectx.simcity.functions.commands;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector2;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.projectx.simcity.functions.Plot;
import net.projectx.simcity.functions.mysql.MySQL_Plot;
import net.projectx.simcity.functions.mysql.MySQL_User;
import net.projectx.simcity.main.Data;
import net.projectx.simcity.util.command.PXCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static net.projectx.simcity.main.Data.*;

/**
 * ~Yannick on 19.11.2019 at 09:09 o´ clock
 */
public class cmd_plot {
    StringBuilder builder;

    @PXCommand(
            name = "plot",
            aliases = "p",
            maxArgs = 0,
            minArgs = 0
    )
    public void execute(CommandSender sender) {
        builder = new StringBuilder();
        builder.append(prefix + "§7§lHilfsübersicht:§r\n");
        add("create", "Erstellt ein Grundstück");
        add("delete", "Löscht ein Grundstück");
        add("list", "Listet Grundstücke auf");
        add("buy", "Kauft ein Grundstück");
        add("sell", "Verkauft ein Grundstück");
        add("members", "Listet alle Mitglieder eines Grundstücks auf");
        add("memberadd", "Fügt ein Mitglied hinzu!");
        add("memberremove", "Entfernt ein Mitglied");
        add("edges", "Gibt die Ecken eines Grundstücks aus!");
        sender.sendMessage(builder.toString());
    }

    @PXCommand(
            name = "create",
            usage = "/plot create <name> <inCity>",
            minArgs = 2,
            maxArgs = 2,
            noConsole = true,
            parent = "plot"
    )
    public void create(Player p, String name, boolean city) {
        if (MySQL_User.getJob(p.getUniqueId()).equals("Buergermeister")) {
            if (!Plot.isPlotExists(name)) {
                try {
                    com.sk89q.worldedit.entity.Player weplayer = BukkitAdapter.adapt(p);
                    Location loc1 = new Location(p.getWorld(), wedit.getSession(p).getSelection(weplayer.getWorld()).getMaximumPoint().getBlockX(), wedit.getSession(p).getSelection(weplayer.getWorld()).getMaximumPoint().getBlockY(), wedit.getSession(p).getSelection(weplayer.getWorld()).getMaximumPoint().getBlockZ());
                    Location loc2 = new Location(p.getWorld(), wedit.getSession(p).getSelection(weplayer.getWorld()).getMinimumPoint().getBlockX(), wedit.getSession(p).getSelection(weplayer.getWorld()).getMinimumPoint().getBlockY(), wedit.getSession(p).getSelection(weplayer.getWorld()).getMinimumPoint().getBlockZ());
                    Plot.createPlot(name, loc1, loc2, city);
                    p.sendMessage(prefix + "§aPlot wurde erstellt!");
                } catch (IncompleteRegionException e) {
                    p.sendMessage(prefix + "Du musst zuerst die Ecken markieren!");
                }
            } else {
                p.sendMessage(prefix + "§cDas Grundstück §e" + name + "§c existiert bereits!");
            }
        } else {
            p.sendMessage(prefix + "§cDas kann nur der Bürgermeister!");
        }
    }

    @PXCommand(
            name = "createstate",
            usage = "/plot createstate <name>",
            minArgs = 1,
            maxArgs = 1,
            noConsole = true,
            parent = "plot"
    )
    public void createState(Player p, String name) {
        if (MySQL_User.getJob(p.getUniqueId()).equals("Buergermeister")) {
            if (!Plot.isPlotExists(name)) {
                try {
                    com.sk89q.worldedit.entity.Player weplayer = BukkitAdapter.adapt(p);
                    Location loc1 = new Location(p.getWorld(), wedit.getSession(p).getSelection(weplayer.getWorld()).getMaximumPoint().getBlockX(), wedit.getSession(p).getSelection(weplayer.getWorld()).getMaximumPoint().getBlockY(), wedit.getSession(p).getSelection(weplayer.getWorld()).getMaximumPoint().getBlockZ());
                    Location loc2 = new Location(p.getWorld(), wedit.getSession(p).getSelection(weplayer.getWorld()).getMinimumPoint().getBlockX(), wedit.getSession(p).getSelection(weplayer.getWorld()).getMinimumPoint().getBlockY(), wedit.getSession(p).getSelection(weplayer.getWorld()).getMinimumPoint().getBlockZ());
                    Plot.createPlot(name, loc1, loc2, true);
                    MySQL_Plot.setMembers(new ArrayList<>(), name);
                    MySQL_Plot.setOwnerString("Staat", name);
                    MySQL_Plot.setPurchaseable(false, name);
                    p.sendMessage(prefix + "§aPlot wurde erstellt!");
                } catch (IncompleteRegionException e) {
                    p.sendMessage(prefix + "Du musst zuerst die Ecken markieren!");
                }
            } else {
                p.sendMessage(prefix + "§cDas Grundstück §e" + name + "§c existiert bereits!");
            }
        } else {
            p.sendMessage(prefix + "§cDas kann nur der Bürgermeister!");
        }
    }

    @PXCommand(
            name = "delete",
            usage = "/plot delete <plot>",
            minArgs = 1,
            maxArgs = 1,
            parent = "plot",
            noConsole = true
    )
    public void delete(Player p, String name) {
        if (MySQL_User.getJob(p.getUniqueId()).equals("Buergermeister")) {
            if (Plot.isPlotExists(name)) {
                    TextComponent component = new TextComponent();
                    component.setText(prefix + "§aKlicke §ehier§a um das Grundstück §e" + name + "§a zu löschen!!");
                    component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/plot deleteconfirm " + name));
                    component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§eHier Klicken!!").create()));
                    p.spigot().sendMessage(component);
            } else {
                p.sendMessage(prefix + "§cDas Grundstück §e" + name + "§c existiert nicht!");
            }
        } else {
            p.sendMessage(prefix + "§cDas kann nur der Bürgermeister!");
        }
    }

    @PXCommand(
            name = "deleteconfirm",
            usage = "/plot deleteconfirm <plot>",
            minArgs = 1,
            maxArgs = 1,
            parent = "plot",
            noConsole = true
    )
    public void deleteconfirm(Player p, String name) {
        if (MySQL_User.getJob(p.getUniqueId()).equals("Buergermeister")) {
            if (Plot.isPlotExists(name)) {
                if (MySQL_Plot.getOwnerString(name).equals("null")) {
                    Plot.deletePlot(name);
                    p.sendMessage(prefix + "§aDas Grundstück §e" + name + "§a wurde gelöscht!");
                } else {
                    p.sendMessage(prefix + "§cDas Grundstück gehört jemandem!");
                }
            } else {
                p.sendMessage(prefix + "§cDas Grundstück §e" + name + "§c existiert nicht!");
            }
        } else {
            p.sendMessage(prefix + "§cDas kann nur der Bürgermeister!");
        }
    }

    @PXCommand(
            name = "list",
            minArgs = 1,
            maxArgs = 1,
            usage = "plot list <all/playername/purchaseable>",
            parent = "plot"
    )
    public void list(CommandSender sender, String args) {
        final String[] plots = {""};
        if (args.equals("all")) {
            MySQL_Plot.getPlots().forEach(entry -> {
                plots[0] = plots[0] + "§e" + entry + "§7, ";
            });
            if (plots[0].length() != 0) {
                sender.sendMessage(prefix + plots[0]);
            } else {
                sender.sendMessage(prefix + "§cEs wurde noch kein Grundstück eingetragen!");
            }
        } else if (args.equals("purchaseable")) {
            MySQL_Plot.getPlots().forEach(entry -> {
                if (Plot.isPlotPurchaseable(entry)) {
                    plots[0] = plots[0] + "§e" + entry + "§7, ";
                }
            });
            if (plots[0].length() != 0) {
                sender.sendMessage(prefix + plots[0]);
            } else {
                sender.sendMessage(prefix + "§cEs steht kein Grundstück zu Verkauf!");
            }
        } else {
            if (MySQL_User.isUserExists(args)) {
                if (MySQL_Plot.getPlotsOf(MySQL_User.getUUID(args)).size() != 0) {
                    MySQL_Plot.getPlotsOf(MySQL_User.getUUID(args)).forEach(entry -> {
                        plots[0] = plots[0] + "§e" + entry + "§7, ";
                    });
                    if (plots[0].length() != 0) {
                        sender.sendMessage(prefix + plots[0]);
                    } else {
                        sender.sendMessage(prefix + "§cEs steht kein Grundstück zu Verkauf!");
                    }
                } else {
                    sender.sendMessage(prefix + "§cDer User§e " + args + "§c hat keine Grundstücke!");
                }
            } else {
                sender.sendMessage(prefix + "§cDer User §e" + args + "§c steht nicht in der Datenbank!");
            }
        }
    }

    @PXCommand(
            name = "buy",
            minArgs = 1,
            maxArgs = 1,
            usage = "/plot buy <plot>",
            parent = "plot",
            noConsole = true
    )
    public void buy(Player p, String name) {
        if (Plot.isPlotExists(name)) {
            if (Plot.isPlotPurchaseable(name)) {
                if (MySQL_User.getDukaten(p.getUniqueId()) >= Plot.getPrice(name)) {
                    TextComponent component = new TextComponent();
                    component.setText(prefix + "§aKlicke §ehier§a um das Grundstück §e" + name + "§a für §e" + Plot.getPrice(name) + "§a zu kaufen!");
                    component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/plot buyconfirm " + name));
                    component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§eHier Klicken!!").create()));
                    p.spigot().sendMessage(component);
                } else {
                    p.sendMessage(prefix + "§cDu hast nicht genug Dukaten! Dir fehlen §e" + (Plot.getPrice(name) - MySQL_User.getDukaten(p.getUniqueId())) + "§c Dukaten!");
                }
            } else {
                p.sendMessage(prefix + "§cDas Grundstück§e " + name + "§c steht nicht zu Verkauf!");
            }
        } else {
            p.sendMessage(prefix + "§cDas Grundstück§e " + name + "§c gibt es nicht!");
        }
    }

    @PXCommand(
            name = "buyconfirm",
            minArgs = 1,
            maxArgs = 1,
            usage = "/plot buyconfirm <plot>",
            parent = "plot",
            noConsole = true
    )
    public void confirmBuy(Player p, String name) {
        if (Plot.isPlotExists(name)) {
            if (Plot.isPlotPurchaseable(name)) {
                if (MySQL_User.getDukaten(p.getUniqueId()) >= Plot.getPrice(name)) {
                    Plot.buyPlot(name, p.getUniqueId());
                    p.sendMessage(prefix + "§aDu hast erfolgreich das Grundstück §e" + name + "§a gekauft!");
                } else {
                    p.sendMessage(prefix + "§cDu hast nicht genug Dukaten! Dir fehlen §e" + (Plot.getPrice(name) - MySQL_User.getDukaten(p.getUniqueId())) + "§c Dukaten!");
                }
            } else {
                p.sendMessage(prefix + "§cDas Grundstück§e " + name + "§c steht nicht zu Verkauf!");
            }
        } else {
            p.sendMessage(prefix + "§cDas Grundstück§e " + name + "§c gibt es nicht!");
        }
    }

    @PXCommand(
            name = "sell",
            minArgs = 2,
            maxArgs = 2,
            usage = "/plot sell <plot> <preis>",
            parent = "plot",
            noConsole = true
    )
    public void sell(Player p, String name, long price) {
        if (Plot.isPlotExists(name)) {
            if (MySQL_Plot.getOwner(name).equals(p.getUniqueId())) {
                if (price >= Plot.getStandardPrice(name, MySQL_Plot.isCity(name))) {
                    TextComponent component = new TextComponent();
                    component.setText(prefix + "§aKlicke §ehier§a um das Grundstück §e" + name + "§a für " + price + " §aDukaten zu verkaufen!");
                    component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/plot sellconfirm " + name + " " + price));
                    component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§eHier Klicken!!").create()));
                    p.spigot().sendMessage(component);
                } else {
                    p.sendMessage(prefix + "§cDu kannst das Grundstück nicht unter Wert verkaufen!");
                }
            } else {
                p.sendMessage(prefix + "§cDas Grundstück§e " + name + "§c gehört nicht dir!");
            }
        } else {
            p.sendMessage(prefix + "§cDas Grundstück§e " + name + "§c gibt es nicht!");
        }
    }

    @PXCommand(
            name = "sellconfirm",
            minArgs = 2,
            maxArgs = 2,
            usage = "/plot sellconfirm <plot> <preis>",
            parent = "plot",
            noConsole = true
    )
    public void confirmSell(Player p, String name, long price) {
        if (Plot.isPlotExists(name)) {
            if (MySQL_Plot.getOwner(name).equals(p.getUniqueId())) {
                if (price >= Plot.getStandardPrice(name, MySQL_Plot.isCity(name))) {
                    MySQL_Plot.setPrice(price, name);
                    MySQL_Plot.setPurchaseable(true, name);
                    p.sendMessage(prefix + "Grundstück wurde zu Verkauf gestellt!");
                } else {
                    p.sendMessage(prefix + "§cDu kannst das Grundstück nicht unter Wert verkaufen!");
                }
            } else {
                p.sendMessage(prefix + "§cDas Grundstück§e " + name + "§c gehört nicht dir!");
            }
        } else {
            p.sendMessage(prefix + "§cDas Grundstück§e " + name + "§c gibt es nicht!");
        }
    }

    @PXCommand(
            name = "members",
            minArgs = 1,
            maxArgs = 1,
            usage = "/plot members <plot>",
            parent = "plot"
    )
    public void members(CommandSender sender, String name) {
        if (Plot.isPlotExists(name)) {
            final String[] plots = {""};
            if (MySQL_Plot.getMembers(name).size() != 0) {
                MySQL_Plot.getMembers(name).forEach(entry -> {
                    plots[0] = plots[0] + "§e" + MySQL_User.getName(entry) + "§7,";
                });
                if (plots.length != 0) {
                    sender.sendMessage(prefix + plots[0]);
                } else {
                    sender.sendMessage(prefix + "§cEs steht kein Grundstück zu Verkauf!");
                }
            } else {
                sender.sendMessage(prefix + "Das Grundstück hat keine Member");
            }
        } else {
            sender.sendMessage(prefix + "§cDas Grundstück§e " + name + "§c gibt es nicht!");
        }
    }

    @PXCommand(
            name = "memberadd",
            minArgs = 2,
            maxArgs = 2,
            usage = "/plot memberadd <plot> <member>",
            parent = "plot",
            noConsole = true
    )
    public void memberadd(Player sender, String name, String member) {
        if (Plot.isPlotExists(name)) {
            if (MySQL_Plot.getOwner(name).equals(sender.getUniqueId())) {
                if (MySQL_User.isUserExists(member)) {
                    if (!MySQL_Plot.getMembers(name).contains(member)) {
                        MySQL_Plot.addMember(MySQL_User.getUUID(member), name);
                        sender.sendMessage(prefix + "§aDer Spieler §e" + member + "§a wurde als Mitglied hinzugefügt!");
                    } else {
                        sender.sendMessage(prefix + "§cSpieler ist bereits Mitglied des Plots!");
                    }
                } else {
                    sender.sendMessage(prefix + "§cDer Spieler §e" + member + "§c existiert nicht!");
                }
            } else {
                sender.sendMessage(prefix + "§cDu bist nicht der Owner des Grundstücks!");
            }
        } else {
            sender.sendMessage(prefix + "§cDas Grundstück§e " + name + "§c gibt es nicht!");
        }
    }

    @PXCommand(
            name = "memberremove",
            minArgs = 2,
            maxArgs = 2,
            usage = "/plot memberremove <plot> <member>",
            parent = "plot",
            noConsole = true
    )
    public void memberremove(Player sender, String plot, String member) {
        if (Plot.isPlotExists(plot)) {
            if (MySQL_Plot.getOwner(plot).equals(sender.getUniqueId())) {
                if (MySQL_User.isUserExists(member)) {
                    if (MySQL_Plot.getMembers(plot).contains(MySQL_User.getUUID(member))) {
                        MySQL_Plot.removeMember(MySQL_User.getUUID(member), plot);
                        sender.sendMessage(prefix + "§aDer Spieler §e" + member + "§a wurde als Mitglied entfernt!");
                    } else {
                        sender.sendMessage(prefix + "§cSpieler ist kein Mitglied des Plots!");
                    }
                } else {
                    sender.sendMessage(prefix + "§cDer Spieler §e" + member + "§c existiert nicht!");
                }
            } else {
                sender.sendMessage(prefix + "§cDu bist nicht der Owner dieses Grundstücks!");
            }
        } else {
            sender.sendMessage(prefix + "§cDas Grundstück§e " + plot + "§c gibt es nicht!");
        }
    }

    @PXCommand(
            name = "edges",
            parent = "plot",
            minArgs = 1,
            maxArgs = 1,
            noConsole = true
    )
    public void edges(Player p, String name) {
        if (Plot.isPlotExists(name)) {
            List<BlockVector2> points = regions.getRegion(name).getPoints();
            p.sendMessage(prefix + "Ecken: (§e" + points.get(0).getBlockX() + "§7|§e" + points.get(0).getBlockZ() + "§7) und (§e" + points.get(1).getBlockX() + "§7|§e" + points.get(1).getBlockZ() + "§7)");
        } else {
            p.sendMessage(prefix + "§cDas Grundstück§e " + name + "§c gibt es nicht!");
        }
    }


    private void add(String command, String usage) {
        builder.append("\n" + Data.symbol + "§e/plot " + command + "§8: §7 " + usage + ChatColor.RESET + "\n");
    }


}
