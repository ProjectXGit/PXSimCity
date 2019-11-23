package net.projectx.simcity.functions.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.projectx.simcity.functions.mysql.MySQL_Bank;
import net.projectx.simcity.functions.mysql.MySQL_User;
import net.projectx.simcity.main.Data;
import net.projectx.simcity.util.command.PXCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.time.format.DateTimeFormatter;

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
        add("zins", "Gibt den Zinssatz für einen Betrag und einen Zeitraum");
        p.sendMessage(builder.toString());
    }

    @PXCommand(
            name = "create",
            minArgs = 2,
            maxArgs = 2,
            noConsole = true,
            parent = "bankaccount",
            usage = "/bankaccount create <Dukaten> <timeindays>"
    )
    public void create(Player p, int dukaten, int days) {
        if (MySQL_User.getDukaten(p.getUniqueId()) >= dukaten) {
            MySQL_Bank.openBankAccount(p.getUniqueId(), dukaten, days * 24);
            MySQL_User.removeDukaten(dukaten, p.getUniqueId());
            p.sendMessage(prefix + "Es wurden erfolgreich §e" + dukaten + "§a Dukaten für §e" + days + "§a Tage angelegt!");
        } else {
            p.sendMessage(prefix + "§cDu hast nicht genug Dukaten!");
        }
    }

    @PXCommand(
            name = "receive",
            minArgs = 0,
            maxArgs = 0,
            noConsole = true,
            parent = "bankaccount",
            usage = "/bankaccount receive"
    )
    public void receive(Player p) {
        if (MySQL_Bank.getBankAccounts(p.getUniqueId()).size() != 0) {
            final boolean[] received = {false};
            MySQL_Bank.getBankAccounts(p.getUniqueId()).forEach((id, end) -> {
                if (MySQL_Bank.isRecieveable(id)) {
                    received[0] = true;
                    p.sendMessage(prefix + "§e" + MySQL_Bank.getEndMoney(id) + "§a Dukaten abgeholt!");
                    MySQL_User.addDukaten(MySQL_Bank.getEndMoney(id), p.getUniqueId());
                    MySQL_Bank.closeBankAccount(id);
                }
            });
            if (!received[0]) {
                p.sendMessage(prefix + "§cDu hast kein Konto zum Ausbezahlen!");
            }
        } else {
            p.sendMessage(prefix + "Du hast kein Geld angelegt!");
        }
    }

    @PXCommand(
            name = "abort",
            parent = "bankaccount",
            usage = "/bankaccount abort <id>",
            minArgs = 1,
            maxArgs = 1,
            noConsole = true
    )
    public void abort(Player p, int id) {
        if (MySQL_Bank.getBankAccounts(p.getUniqueId()).containsKey(id)) {
            if (!MySQL_Bank.isRecieveable(id)) {
                TextComponent component = new TextComponent();
                component.setText(prefix + "§aKlicke §ehier§a um das Konto zu löschen! Du wirst nur den eingezahlten Betrag von §e" + MySQL_Bank.getStartMoney(id) + "§a Dukaten erhalten!");
                component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/bankaccount abortconfirm " + id));
                component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§eHier Klicken!!").create()));
                p.spigot().sendMessage(component);
            } else {
                p.sendMessage(prefix + "§aDas Konto hat bereits die Endzeit überschritten. Es wird automatisch abgeholt!");
                p.sendMessage(prefix + "§e" + MySQL_Bank.getEndMoney(id) + "§a Dukaten hinzugefügt!");
                MySQL_User.addDukaten(MySQL_Bank.getEndMoney(id), p.getUniqueId());
                MySQL_Bank.closeBankAccount(id);
            }
        } else {
            p.sendMessage(prefix + "§cDas Bankkonto §e" + id + "§c gibt es nicht!");
        }
    }

    @PXCommand(
            name = "abortconfirm",
            parent = "bankaccount",
            usage = "/bankaccount abortconfirm <id>",
            maxArgs = 1,
            minArgs = 1,
            noConsole = true
    )
    public void abortConfirm(Player p, int id) {
        if (MySQL_Bank.getBankAccounts(p.getUniqueId()).containsKey(id)) {
            if (!MySQL_Bank.isRecieveable(id)) {
                p.sendMessage(prefix + "§aKonto gelöscht!");
                p.sendMessage(prefix + "§e" + MySQL_Bank.getStartMoney(id) + "§a Dukaten hinzugefügt!");
                MySQL_User.addDukaten(MySQL_Bank.getStartMoney(id), p.getUniqueId());
                MySQL_Bank.closeBankAccount(id);
            } else {
                p.sendMessage(prefix + "§aDas Konto hat bereits die Endzeit überschritten. Es wird automatisch abgeholt!");
                p.sendMessage(prefix + "§e" + MySQL_Bank.getEndMoney(id) + "§a Dukaten hinzugefügt!");
                MySQL_User.addDukaten(MySQL_Bank.getEndMoney(id), p.getUniqueId());
                MySQL_Bank.closeBankAccount(id);
            }
        } else {
            p.sendMessage(prefix + "§cDas Bankkonto §e" + id + "§c gibt es nicht!");
        }
    }

    @PXCommand(
            name = "list",
            parent = "bankaccount",
            usage = "/bankaccount list",
            maxArgs = 0,
            minArgs = 0,
            noConsole = true
    )
    public void list(Player p) {
        if (MySQL_Bank.getBankAccounts(p.getUniqueId()).size() != 0) {
            final String[] s = {""};
            MySQL_Bank.getBankAccounts(p.getUniqueId()).forEach(((id, end) -> {
                s[0] = s[0] + "§e" + id + "§7,";
            }));
            p.sendMessage(prefix + s[0]);
        } else {
            p.sendMessage(prefix + "Du hast kein Geld angelegt!");
        }
    }

    @PXCommand(
            name = "info",
            parent = "bankacccount",
            usage = "/bankaccount info",
            maxArgs = 1,
            minArgs = 1,
            noConsole = true
    )
    public void info(Player p, int id) {
        p.sendMessage(prefix + "§eKonto: " + id);
        p.sendMessage(Data.symbol + "§7Startkapital: §6" + MySQL_Bank.getStartMoney(id));
        p.sendMessage(Data.symbol + "§7Endkapital: §6" + MySQL_Bank.getEndMoney(id));
        p.sendMessage(Data.symbol + "§7Endzeit: §6" + MySQL_Bank.getEndTime(id).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

    @PXCommand(
            name = "zins",
            parent = "bankaccount",
            usage = "/bankaccount zins <days>",
            minArgs = 1,
            maxArgs = 1,
            noConsole = true
    )
    public void zins(Player p, int days) {
        p.sendMessage("§aDer Zinssatz würde §e" + MySQL_Bank.getZins(days / 24) + "% §abetragen!");
    }

    private void add(String command, String usage) {
        builder.append("\n" + Data.symbol + "§e/bankaccount " + command + "§8: §7 " + usage + ChatColor.RESET + "\n");
    }
}
