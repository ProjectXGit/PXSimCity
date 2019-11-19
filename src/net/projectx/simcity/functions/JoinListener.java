package net.projectx.simcity.functions;

import net.projectx.simcity.functions.mysql.MySQL_User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static net.projectx.simcity.main.Data.tablist;

/**
 * ~Yannick on 13.11.2019 at 20:28 o´ clock
 */
public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (!MySQL_User.isUserExists(p.getUniqueId())) {
            MySQL_User.createUser(p);
        }
        e.setJoinMessage("§7" + p.getDisplayName() + "[§2+§7]");
        p.sendMessage("§aWillkommen auf dem Server!");
        tablist.setTablist(p.getUniqueId());
        tablist.createTablist();
        Scheduler.createScoreboard(p);
        tablist.setPlayer(p);
    }

}
