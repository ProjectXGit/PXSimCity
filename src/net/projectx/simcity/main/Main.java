package net.projectx.simcity.main;


import net.projectx.simcity.functions.MySQL.MySQL;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


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
        MySQL.connect();
    }

    @Override
    public void onDisable() {

    }

    public static void registerCommands() {
        //*LOOOOOOOOOOOOOOOOL MIT MEHR OOOOOOOOOO
    }

    public static void registerListener() {

    }
}
