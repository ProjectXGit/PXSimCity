package net.projectx.simcity.functions.commands;

import net.projectx.simcity.functions.Scheduler;
import net.projectx.simcity.functions.mysql.MySQL_User;
import net.projectx.simcity.main.Data;
import net.projectx.simcity.util.command.PXCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

/**
 * ~Yannick on 20.11.2019 at 11:34 o´ clock
 */
public class cmd_reload {
    @PXCommand(
            name = "screload",
            maxArgs = 0,
            minArgs = 0,
            requiresOp = true
    )
    public void execute(CommandSender sender) {
        Bukkit.getOnlinePlayers().forEach(entry -> {
            entry.sendTitle("§4§lACHTUNG", "§cDer Server wird reloadet!");
            Scheduler.boards.get(entry).destroy();
            MySQL_User.setPlaytime(Data.playtime.get(entry), entry.getUniqueId());
        });
        Bukkit.reload();
    }
}
