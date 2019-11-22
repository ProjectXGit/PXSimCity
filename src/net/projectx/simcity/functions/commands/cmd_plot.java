package net.projectx.simcity.functions.commands;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
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

import static net.projectx.simcity.main.Data.prefix;
import static net.projectx.simcity.main.Data.wedit;

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
        add("list", "Listet alle Grundstücke auf");
        add("buy", "Kauft ein Grundstück");
        add("sell", "Verkauft ein Grundstück");
        add("members", "Listet alle Mitglieder eines Grundstücks auf");
        add("memberadd", "Fügt ein Mitglied hinzu!");
        add("memberremove", "Entfernt ein Mitglied");
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
    }

    @PXCommand(
            name = "delete",
            usage = "/plot delete <plot>",
            minArgs = 1,
            maxArgs = 1,
            parent = "plot"
    )
    public void delete(CommandSender p, String name) {
        if (Plot.isPlotExists(name)) {
            Plot.deletePlot(name);
            p.sendMessage(prefix + "§aDas Grundstück §e" + name + "§c wurde gelöscht!");
        } else {
            p.sendMessage(prefix + "§cDas Grundstück §e" + name + "§c existiert nicht!");
        }
    }

    @PXCommand(
            name = "list",
            minArgs = 0,
            maxArgs = 0,
            usage = "plot list"
    )
    public void list(CommandSender sender) {
        final String[] plots = {""};
        MySQL_Plot.getPlots().forEach(entry -> {
            if (Plot.isPlotPurchaseable(entry)) {
                plots[0] = plots[0] + entry;
            }
        });
        if (plots.length != 0) {
            sender.sendMessage(prefix + plots);
        } else {
            sender.sendMessage(prefix + "§cEs steht kein Grundstück zu Verkauf!");
        }
    }

    @PXCommand(
            name = "all",
            minArgs = 0,
            maxArgs = 0,
            usage = "plot list all",
            parent = "list"
    )
    public void listall(CommandSender sender) {
        final String[] plots = {""};
        MySQL_Plot.getPlots().forEach(entry -> {
            plots[0] = plots[0] + entry;
        });
        if (plots.length != 0) {
            sender.sendMessage(prefix + plots);
        } else {
            sender.sendMessage(prefix + "§cEs wurde noch kein Grundstück eingetragen!");
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
                    component.setText(prefix + "§aKlicke §ehier§a um den das Grundstück §e" + name + "§a zu kaufen!");
                    component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/plot buy confirm " + name));
                    component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§eHier Klicken!!").create()));
                    p.spigot().sendMessage(component);
                } else {
                    p.sendMessage(prefix + "§cDU hast nicht genug Dukaten! Dir fehlen §e" + (Plot.getPrice(name) - MySQL_User.getDukaten(p.getUniqueId())) + "§c Dukaten!");
                }
            } else {
                p.sendMessage(prefix + "§cDas Grundstück§e " + name + "§c steht nicht zu Verkauf!");
            }
        } else {
            p.sendMessage(prefix + "§cDas Grundstück§e " + name + "§c gibt es nicht!");
        }
    }

    @PXCommand(
            name = "confirm",
            minArgs = 1,
            maxArgs = 1,
            usage = "/plot buy confirm <plot>",
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
                    p.sendMessage(prefix + "§cDU hast nicht genug Dukaten! Dir fehlen §e" + (Plot.getPrice(name) - MySQL_User.getDukaten(p.getUniqueId())) + "§c Dukaten!");
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
            if (MySQL_Plot.getOwner(name).equals(p.getName())) {
                if (price >= Plot.getStandardPrice(name)) {
                    TextComponent component = new TextComponent();
                    component.setText(prefix + "§aKlicke §ehier§a um das Grundstück §e" + name + "§a für " + price + " §aDukaten zu verkaufen!");
                    component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/plot buy confirm " + name));
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
            name = "sell",
            minArgs = 2,
            maxArgs = 2,
            usage = "/plot sell confirm <plot> <preis>",
            parent = "plot",
            noConsole = true
    )
    public void confirmSell(Player p, String name, long price) {
        if (Plot.isPlotExists(name)) {
            if (MySQL_Plot.getOwner(name).equals(p.getName())) {
                if (price >= Plot.getStandardPrice(name)) {
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
            if (MySQL_Plot.getMembers(name).size() != 0) {

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
            parent = "plot"
    )
    public void memberadd(CommandSender sender, String plot, String member) {

    }

    @PXCommand(
            name = "memberremove",
            minArgs = 2,
            maxArgs = 2,
            usage = "/plot memberadd <plot> <member>",
            parent = "plot"
    )
    public void memberremove(CommandSender sender, String plot, String member) {

    }


    private void add(String command, String usage) {
        builder.append("\n" + Data.symbol + "§e/plot " + command + "§8: §7 " + usage + ChatColor.RESET + "\n");
    }


}
