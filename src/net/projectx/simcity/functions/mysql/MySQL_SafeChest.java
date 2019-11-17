package net.projectx.simcity.functions.mysql;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.craftbukkit.v1_14_R1.block.CraftSign;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MySQL_SafeChest {

    public static void createUserTable() {
        MySQL.update("CREATE TABLE IF NOT EXISTS chest(uuid VARCHAR(64),xk INT," +
                "yk INT,zk INT)");
    }
    public static boolean isChestOf(UUID uuid, Location loc) {
        ResultSet rs = MySQL.querry("SELECT * FROM chest WHERE xk=" + loc.getBlockX() + " AND yk=" + loc.getBlockY() +
                " AND zk=" + loc.getBlockZ() + " AND uuid='" + uuid + "'");

        try {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isSafeChest(Location loc) {
        ResultSet rs = MySQL.querry("SELECT * FROM chest WHERE xk=" + loc.getBlockX() + " AND yk=" + loc.getBlockY() +
                " AND zk=" + loc.getBlockZ() + "");

        try {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void safeChest(UUID uuid, Location loc){
        MySQL.update("INSERT INTO chest VALUES ('" + uuid + "'," + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ() + ")");
    }

    public static void deleteChest(Location loc) {
        MySQL.update("DELETE FROM chest WHERE xk=" + loc.getBlockX() + " AND yk=" + loc.getBlockY() +
                " AND zk=" + loc.getBlockZ());
    }

    public static Block getBlockSignAttachedTo(Block block) {
        if (block != null && block.getState() instanceof CraftSign) {
            BlockData data = block.getBlockData();
            if (data instanceof Directional) {
                Directional directional = (Directional) data;
                return block.getRelative(directional.getFacing().getOppositeFace());
            }
        }
        return null;
    }

    public static boolean isChestAttached(Block sign) {
        return getBlockSignAttachedTo(sign).getType().equals(Material.CHEST);
    }

    public static UUID getOwner(Location loc) {
        try {
            ResultSet rs = MySQL.querry("SELECT uuid FROM chest WHERE xk = " + loc.getBlockX() + " AND yk = " + loc.getBlockY() + " AND zk = " + loc.getBlockZ());
            while (rs.next()) {
                String uuid = rs.getString("uuid");
                return UUID.fromString(uuid);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isNearSafeChest(Location loc) {
        for (int x = -1; x < 2; x++) {
            for (int z = -1; z < 2; z++) {
                Location loc1 = new Location(loc.getWorld(), loc.getBlockX() + x, loc.getBlockY(), loc.getBlockZ() + z);
                System.out.println("X:" + loc1.getBlockX() + " Y:" + loc1.getBlockY() + " Z:" + loc1.getBlockZ());
                System.out.println(isSafeChest(loc1));
                if (isSafeChest(loc1)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Location getSafeChestNear(Location loc) {
        for (int x = -1; x < 2; x++) {
            for (int z = -1; z < 2; z++) {
                if (isSafeChest(new Location(loc.getWorld(), loc.getBlockX() + x, loc.getBlockY(), loc.getBlockZ() + z))) {
                    return new Location(loc.getWorld(), loc.getBlockX() + x, loc.getBlockY(), loc.getBlockZ() + z);
                }
            }
        }
        return null;
    }

}
