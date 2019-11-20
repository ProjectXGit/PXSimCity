package net.projectx.simcity.functions.commands;

import net.projectx.simcity.util.command.PXCommand;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import static net.projectx.simcity.main.Data.prefix;

/**
 * ~Yannick on 20.11.2019 at 14:57 o´ clock
 */
public class cmd_ping {

    @PXCommand(
            name = "ping",
            maxArgs = 0,
            minArgs = 0,
            noConsole = true
    )
    public void execute(Player p) {
        p.sendMessage(prefix + "Du hast einen Ping von §e" + ((CraftPlayer) p).getHandle().ping + "ms§7!");
    }
}
