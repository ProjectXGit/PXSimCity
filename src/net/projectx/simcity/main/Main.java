package net.projectx.simcity.main;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * ~Yannick on 12.11.2019 at 21:42 o´ clock
 */
public class Main extends JavaPlugin {
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

    }

    public static void registerListener() {

    }
}
