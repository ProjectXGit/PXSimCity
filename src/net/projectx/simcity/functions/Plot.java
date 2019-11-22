package net.projectx.simcity.functions;


import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector2;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedPolygonalRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import net.projectx.simcity.functions.mysql.MySQL_Plot;
import net.projectx.simcity.functions.mysql.MySQL_User;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * ~Yannick on 19.11.2019 at 16:09 o´ clock
 */
public class Plot {
    public static RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
    public static RegionManager regions = container.get(BukkitAdapter.adapt(Bukkit.getWorld("world")));

    public static void createPlot(String name, Location loc1, Location loc2, boolean city) {
        List<BlockVector2> list = new ArrayList<>();
        list.add(BlockVector2.at(loc1.getBlockX(), loc1.getBlockZ()));
        list.add(BlockVector2.at(loc2.getBlockX(), loc2.getBlockZ()));
        ProtectedRegion region = new ProtectedPolygonalRegion(name, list, 0, 255);
        regions.addRegion(region);
        MySQL_Plot.createPlot(name, city, getStandardPrice(name));
    }

    public static void deletePlot(String name) {
        regions.removeRegion(name);
        MySQL_Plot.deletePlot(name);
    }

    public static boolean isPlotExists(String name) {
        return MySQL_Plot.getPlots().contains(name);
    }

    public static boolean isPlotPurchaseable(String name) {
        return (MySQL_Plot.isPurchaseable(name));
    }

    public static long getStandardPrice(String name) {
        int blocks;
        BlockVector2 bv0 = regions.getRegion(name).getPoints().get(0);
        BlockVector2 bv1 = regions.getRegion(name).getPoints().get(1);
        blocks = bv0.distanceSq(bv1);
        if (!MySQL_Plot.isCity(name)) {
            return blocks * 10;
        } else {
            return blocks * 30;
        }
    }

    public static long getPrice(String name) {
        return MySQL_Plot.getPrice(name);
    }

    public static void buyPlot(String name, UUID uuid) {
        MySQL_User.setDukaten(MySQL_User.getDukaten(uuid) - getPrice(name), uuid);
        if (!MySQL_Plot.getOwner(name).equals("null")) {
            MySQL_User.addDukaten(getPrice(name), MySQL_Plot.getOwner(name));
        }
        MySQL_Plot.setOwner(uuid, name);
    }


}
