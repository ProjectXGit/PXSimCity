package net.projectx.simcity.functions.commands;

import net.projectx.simcity.main.Data;
import net.projectx.simcity.util.command.PXCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static net.projectx.simcity.main.Data.prefix;

/**
 * ~Yannick on 23.11.2019 at 08:57 o´ clock
 */
public class cmd_bankaccount {
    StringBuilder builder;

    @PXCommand(
            name = "bankaccount",
            minArgs = 0,
            maxArgs = 0,
            usage = "/bankaccount",
            noConsole = true
    )
    public void bank(Player p) {
        builder = new StringBuilder();
        builder.append(prefix + "§7§lHilfsübersicht:§r\n");
        add("create", "Legt Geld auf der Bank an!");
        add("receive", "Hebt Geld von der Bank ab");
        add("abort", "Bricht die Anlage ab!");
        add("list", "Gibt alle aktiven Bankkonten aus");
        add("info", "Gibt die Infos zu einem Bankkonto aus!");
        p.sendMessage(builder.toString());
    }

    @PXCommand(
            name = "create",
            minArgs = 2,
            maxArgs = 2,
            noConsole = true,
            parent = "bankaccount",
            usage = "/bankaccount create <Dukaten> <time>"
    )

    private void add(String command, String usage) {
        builder.append("\n" + Data.symbol + "§e/bankaccount " + command + "§8: §7 " + usage + ChatColor.RESET + "\n");
    }
}
