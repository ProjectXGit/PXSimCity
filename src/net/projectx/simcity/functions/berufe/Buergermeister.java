package net.projectx.simcity.functions.berufe;

import com.sk89q.worldedit.event.platform.CommandEvent;
import net.minecraft.server.v1_14_R1.ICommandListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class Buergermeister implements Listener {

    @EventHandler
    public void PlotCreater(CommandEvent event) {
        if (event.getArguments().startsWith("plot create")) {
            if (event.getActor().isPlayer()) {
                Player p = (Player) event.getActor();
                p.setHealth(0);
            }
        }
    }
}
