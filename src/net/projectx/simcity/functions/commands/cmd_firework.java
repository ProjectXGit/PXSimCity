package net.projectx.simcity.functions.commands;

import net.projectx.simcity.main.Data;
import net.projectx.simcity.util.command.PXCommand;
import org.bukkit.entity.Player;

/**
 * Created by Yannick on 29.10.2019 with IntelliJ for PXCode.
 */
public class cmd_firework {
    @PXCommand(
            name = "firework",
            aliases = {"fw"},
            maxArgs = 0,
            minArgs = 0,
            noConsole = true,
            permissions = "px.challenge.firework"
    )
    public void execute(Player sender, String[] args) {
        sender.sendTitle("§4§lAchtung!", "Feuerwerk wird gespawnt!");
        Data.fireworks.put(sender.getLocation(), 50);
    }
}
