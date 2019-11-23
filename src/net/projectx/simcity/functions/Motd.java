package net.projectx.simcity.functions;

import net.projectx.simcity.functions.mysql.MySQL_User;
import net.projectx.simcity.main.Data;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import java.io.*;
import java.net.URL;

/**
 * ~Yannick on 23.11.2019 at 16:41 o´ clock
 */
public class Motd implements Listener {
    @EventHandler
    public void onPingEvent(ServerListPingEvent e) {
        if (MySQL_User.getNameByAddress(e.getAddress().getHostAddress()) != null) {
            String playername = MySQL_User.getNameByAddress(e.getAddress().getHostAddress());
            e.setMotd("§aHallo §e" + playername + ",\n§aWie geht es dir?");
            downloadPicture(playername);
            System.out.println("Wurde gedownloadet!");
            try {
                e.setServerIcon(Bukkit.loadServerIcon(new File("plugins/" + Data.instance.getDescription().getName() + "/Heads", playername + ".png")));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else {
            e.setMotd("§aHallo §eNeuling§a,\n§aWie geht es dir?");
            downloadPicture("xXLilMCXx_PvPDE");
            try {
                e.setServerIcon(Bukkit.loadServerIcon(new File("plugins/" + Data.instance.getDescription().getName() + "/Heads", "xXLilMCXx_PvPDE" + ".png")));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }


    public static void downloadPicture(String playername) {
        try {
            URL url = null;
            url = new URL("https://www.mc-heads.net/avatar/" + playername + "/64.png");
            InputStream in = null;
            in = url.openConnection().getInputStream();
            OutputStream out = null;
            out = new FileOutputStream(new File("plugins/" + Data.instance.getDescription().getName() + "/Heads", playername + ".png"));
            byte[] buffer = new byte[1024];
            for (int n; (n = in.read(buffer)) != -1; out.write(buffer, 0, n)) ;
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
