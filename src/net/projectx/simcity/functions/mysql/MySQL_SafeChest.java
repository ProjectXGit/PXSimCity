package net.projectx.simcity.functions.mysql;

import org.bukkit.Location;

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
        System.out.println("SELECT * FROM chest WHERE xk=" + loc.getBlockX() + " AND yk=" + loc.getBlockY() +
                " AND zk=" + loc.getBlockZ() + " AND uuid='" + uuid + "'");

        try {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean isChestOf(Location loc) {
        ResultSet rs = MySQL.querry("SELECT * FROM chest WHERE xk=" + loc.getBlockX() + " AND yk=" + loc.getBlockY() +
                " AND zk=" + loc.getBlockZ() + "");
        System.out.println("SELECT * FROM chest WHERE xk=" + loc.getBlockX() + " AND yk=" + loc.getBlockY() +
                " AND zk=" + loc.getBlockZ() + "");

        try {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean isChestOf(UUID uuid) {
        ResultSet rs = MySQL.querry("SELECT * FROM chest WHERE uuid='" + uuid + "'");
        System.out.println("SELECT * FROM chest WHERE uuid='" + uuid + "'");
        try {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void safeChest(UUID uuid, Location loc){
        MySQL.update("INSERT INTO chest VALUES ('" + uuid + "'," + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ() + ")");
        System.out.println("INSERT INTO chest VALUES (" + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ() + ",'" + uuid + "')");
    }
    public static void deleteChest(UUID uuid, Location loc){
        MySQL.update("DELETE FROM chest WHERE xk=" + loc.getBlockX() + " AND yk=" + loc.getBlockY() +
                " AND zk=" + loc.getBlockZ() + " AND uuid='" + uuid + "'");
        System.out.println("DELETE FROM chest WHERE xk=" + loc.getBlockX() + " AND yk=" + loc.getBlockY() +
                " AND zk=" + loc.getBlockZ() + " AND uuid='" + uuid + "'");
    }
    public static boolean isSignNearChest(Location loc) {
        Location loc1 = loc;
        loc1.setX(loc1.getBlockX()+1);
        Location loc2 = loc;
        loc1.setX(loc1.getBlockX()-1);
        Location loc3 = loc;
        loc1.setZ(loc1.getBlockZ()+1);
        Location loc4 = loc;
        loc1.setZ(loc1.getBlockZ()-1);
        if(isChestOf(loc1)||isChestOf(loc2)||isChestOf(loc3)||isChestOf(loc4)){
            System.out.println("Schild ist neben kiste");
            return true;

        }else{
            System.out.println("Schild ist nicht neben kiste");
            return false;
        }
    }
    public static Location isSignNearChestGetLocation(Location loc) {
        Location loc1 = loc;
        loc1.setX(loc1.getBlockX()+1);
        Location loc2 = loc;
        loc1.setX(loc1.getBlockX()-1);
        Location loc3 = loc;
        loc1.setZ(loc1.getBlockZ()+1);
        Location loc4 = loc;
        loc1.setZ(loc1.getBlockZ()-1);
        if(isChestOf(loc1)){
            System.out.println("Schild ist neben kiste1");
            return loc1;
        }else{
            if(isChestOf(loc2)){
                System.out.println("Schild ist neben kiste2");
                return loc2;
            }else{
                if(isChestOf(loc3)){
                    System.out.println("Schild ist neben kiste3");
                    return loc3;
                }else{
                    if(isChestOf(loc4)){
                        System.out.println("Schild ist neben kiste4");
                        return loc4;
                    }else{
                        System.out.println("Schild ist nicht neben kiste");
                        return loc;
                    }
                }
            }

        }
    }
}
