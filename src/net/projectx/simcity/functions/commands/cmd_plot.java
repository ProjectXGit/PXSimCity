package net.projectx.simcity.functions.commands;

import net.projectx.simcity.main.Data;
import net.projectx.simcity.util.command.PXCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import static net.projectx.simcity.main.Data.prefix;

/**
 * ~Yannick on 19.11.2019 at 09:09 o´ clock
 */
public class cmd_plot {
    StringBuilder builder;

    @PXCommand(
            name = "plot",
            aliases = "p",
            noConsole = true
    )
    public void execute(CommandSender sender) {
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
            maxArgs = 2
    )
    public void create(CommandSender sender, String name, boolean city) {

    }

    @PXCommand(
            name = "delete",
            usage = "/plot delete <plot>",
            minArgs = 1,
            maxArgs = 1
    )
    public void delete(CommandSender sender, String name) {

    }

    @PXCommand(
            name = "list",
            minArgs = 0,
            maxArgs = 0,
            usage = "plot list"
    )
    public void list(CommandSender sender) {

    }

    @PXCommand(
            name = "buy",
            minArgs = 1,
            maxArgs = 1,
            usage = "/plot buy <plot>"
    )
    public void buy(CommandSender sender, String name) {

    }

    @PXCommand(
            name = "confirm",
            minArgs = 1,
            maxArgs = 1,
            usage = "/plot buy confirm <plot>"
    )
    public void confirmBuy(CommandSender sender, String name) {

    }

    @PXCommand(
            name = "sell",
            minArgs = 2,
            maxArgs = 2,
            usage = "/plot sell <plot> <preis>"
    )
    public void sell(CommandSender sender, String name, long price) {

    }

    @PXCommand(
            name = "sell",
            minArgs = 2,
            maxArgs = 2,
            usage = "/plot sell confirm <plot> <preis>"
    )
    public void confirmSell(CommandSender sender, String name, long price) {

    }

    @PXCommand(
            name = "members",
            minArgs = 1,
            maxArgs = 1,
            usage = "/plot members <plot>"
    )
    public void members(CommandSender sender) {

    }

    @PXCommand(
            name = "memberadd",
            minArgs = 2,
            maxArgs = 2,
            usage = "/plot memberadd <plot> <member>"
    )
    public void memberadd(CommandSender sender, String plot, String member) {

    }

    @PXCommand(
            name = "memberremove",
            minArgs = 2,
            maxArgs = 2,
            usage = "/plot memberadd <plot> <member>"
    )
    public void memberremove(CommandSender sender, String plot, String member) {

    }


    private void add(String command, String usage) {
        builder.append("\n" + Data.symbol + "§e/plot " + command + "§8: §7 " + usage + ChatColor.RESET + "\n");
    }


}
