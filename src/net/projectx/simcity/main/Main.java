package net.projectx.simcity.main;


import net.projectx.simcity.functions.JoinListener;
import net.projectx.simcity.functions.mysql.MySQL;
import net.projectx.simcity.functions.Scheduler;
import net.projectx.simcity.functions.Tablist;
import org.bukkit.Bukkit;
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
        Data.tablist = new Tablist();
        Scheduler.startScheduler();
    }

    @Override
    public void onDisable() {
        Scheduler.stopScheduler();
    }

    public void registerCommands() {

    }

    public void registerListener() {
        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
    }

}
