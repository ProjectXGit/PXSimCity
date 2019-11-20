package net.projectx.simcity.functions;

import net.projectx.simcity.functions.mysql.MySQL_User;
import net.projectx.simcity.main.Data;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
        if (!MySQL_User.getName(p.getUniqueId()).equals(p.getName())) {
            MySQL_User.setName(p.getName(), p.getUniqueId());
        }
        p.sendMessage("§aWillkommen auf dem Server!");
        tablist.setTablist(p.getUniqueId());
        tablist.createTablist();
        Data.playtime.put(p, MySQL_User.getPlaytime(p.getUniqueId()));
        Scheduler.createScoreboard(p);
        tablist.setPlayer(p);
        e.setJoinMessage("§7" + p.getDisplayName() + "[§2+§7]");
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        e.setQuitMessage("§7" + p.getDisplayName() + "[§4-§7]");
        MySQL_User.setPlaytime(Data.playtime.get(p), p.getUniqueId());
        Data.playtime.remove(p);
    }

}
