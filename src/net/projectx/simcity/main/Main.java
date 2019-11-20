package net.projectx.simcity.main;


import com.google.common.base.Joiner;
import net.projectx.simcity.functions.*;
import net.projectx.simcity.functions.berufe.Foerster;
import net.projectx.simcity.functions.berufe.Miner;
import net.projectx.simcity.functions.berufe.Schmied;
import net.projectx.simcity.functions.berufe.Zuechter;
import net.projectx.simcity.functions.commands.cmd_firework;
import net.projectx.simcity.functions.commands.cmd_plot;
import net.projectx.simcity.functions.commands.cmd_reload;
import net.projectx.simcity.functions.mysql.MySQL;
import net.projectx.simcity.functions.mysql.MySQL_SafeChest;
import net.projectx.simcity.functions.mysql.MySQL_User;
import net.projectx.simcity.util.command.BukkitCommand;
import net.projectx.simcity.util.command.PXCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.projectx.simcity.main.Data.tablist;


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
        createTables();
        Bukkit.getOnlinePlayers().forEach(p -> {
            tablist.setTablist(p.getUniqueId());
            tablist.createTablist();
            Data.playtime.put(p, MySQL_User.getPlaytime(p.getUniqueId()));
            Scheduler.createScoreboard(p);
            tablist.setPlayer(p);
        });
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(entry -> {
            Scheduler.boards.get(entry).destroy();
            MySQL_User.setPlaytime(Data.playtime.get(entry), entry.getUniqueId());
            entry.sendMessage("Scoreboard gelöscht, Playtime gesaved!");
        });
    }



    public void registerCommands() {
        register(new cmd_plot(), this);
        register(new cmd_reload(), this);
        register(new cmd_firework(), this);
    }

    public void registerListener() {
        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new SafeChest(), this);
        Bukkit.getPluginManager().registerEvents(new Foerster(), this);
        Bukkit.getPluginManager().registerEvents(new Miner(), this);
        Bukkit.getPluginManager().registerEvents(new Schmied(), this);
        Bukkit.getPluginManager().registerEvents(new Zuechter(), this);
        Bukkit.getPluginManager().registerEvents(new ChatEvent(), this);

    }

    public void createTables() {
        MySQL_User.createUserTable();
        MySQL_SafeChest.createUserTable();
    }


    /**
     * Registriert Listener und Commands aus einer Klasse für ein Plugin
     *
     * @param function Object der Klasse welche registriert werden soll
     * @param plugin   Plugin für welches die Klasse registriert wird
     */
    public static void register(Object function, JavaPlugin plugin) {
        register(function.getClass(), function, plugin);
    }

    /**
     * Registriert Listener und Commands aus einer Klasse für ein Plugin
     *
     * @param functionClass Klasse welche registriert werden soll
     * @param function      Object der Klasse welche registriert werden soll
     * @param plugin        Plugin für welches die Klasse registriert wird
     */
    public static void register(Class functionClass, Object function, JavaPlugin plugin) {
        Method[] methods = functionClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(PXCommand.class))
                registerCommand(function, method, plugin);
        }

        if (function instanceof Listener) {
            Bukkit.getPluginManager().registerEvents((Listener) function, plugin);
        }
    }

    private static Map<String, Command> commandMap = new HashMap<>();
    private static List<Object[]> unavailableSubcommands = new ArrayList<>();

    private static void registerCommand(Object function, Method method, JavaPlugin plugin) {
        PXCommand cmd = method.getAnnotation(PXCommand.class);

        if (cmd.parent().length == 0) {
            BukkitCommand tBukkitCommand = new BukkitCommand(plugin, function, method, cmd);
            tBukkitCommand.register();

            commandMap.put(tBukkitCommand.getName(), tBukkitCommand);

            for (Object[] unavailableSubcommand : unavailableSubcommands) {
                Method oldMethod = (Method) unavailableSubcommand[1];
                PXCommand old = oldMethod.getAnnotation(PXCommand.class);
                if (old.parent()[0].equalsIgnoreCase(cmd.name()))
                    registerCommand(unavailableSubcommand[0], oldMethod, plugin);
            }
        } else {
            Command pluginCommand = commandMap.get(cmd.parent()[0]);
            if (pluginCommand == null) {
                unavailableSubcommands.add(new Object[]{function, method});
                Joiner.on(" ").join(cmd.parent() + " " + cmd.name(), cmd.parent()[0]);
            } else {
                if (pluginCommand instanceof BukkitCommand) {
                    ((BukkitCommand) pluginCommand).getProcessor().addSubCommand(cmd, function, method);
                } else {
                    Joiner.on(" ").join(cmd.parent() + " " + cmd.name(), cmd.parent()[0]);
                }
            }
        }
    }

}
