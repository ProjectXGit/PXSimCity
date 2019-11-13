package net.projectx.simcity.main;


import org.bukkit.plugin.java.JavaPlugin;
import sun.plugin2.liveconnect.BrowserSideObject;
import sun.plugin2.main.server.Plugin;

import java.net.PasswordAuthentication;
import java.net.URL;

/**
 * ~Yannick on 12.11.2019 at 21:42 o´ clock
 */
public class Main extends JavaPlugin implements Plugin {
    
    @Override
    public void onLoad(){
        
    }
    
    @Override
    public void onEnable() {
        Data.instance = this;
        registerCommands();
        registerListener();
    }

    @Override



    public void onDisable() {

    }

    public static void registerCommands() {
        //*LOOOOOOOOOOOOOOOOL
    }

    public static void registerListener() {

    }
}
