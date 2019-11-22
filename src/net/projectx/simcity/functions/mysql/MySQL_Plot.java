package net.projectx.simcity.functions.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * ~Yannick on 19.11.2019 at 09:19 o´ clock
 */
public class MySQL_Plot {

    public static void createPlotTable() {
        MySQL.update("CREATE TABLE IF NOT EXISTS plot (name VARCHAR(20), owner VARCHAR(64), city BOOLEAN, price BIGINT, purchaseable BOOLEAN)");
    }

    public static void createPlotMemberTable() {
        MySQL.update("CREATE TABLE IF NOT EXISTS member (plot VARCHAR(20), member VARCHAR(64), true)");
    }

    public static void createPlot(String name, boolean city, long price) {
        MySQL.update("INSERT INTO plot VALUES ('" + name + "', 'null', " + city + ", " + price + ", true)");
    }

    public static void deletePlot(String name) {
        MySQL.update("DELETE FROM plot WHERE name = '" + name + "'");
    }


    public static ArrayList<String> getPlots() {
        ArrayList<String> list = new ArrayList<>();
        try {
            ResultSet rs = MySQL.querry("SELECT name FROM plot WHERE 1");
            while (rs.next()) {
                list.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<String> getPlotsOf(UUID uuid) {
        ArrayList<String> list = new ArrayList<>();
        try {
            ResultSet rs = MySQL.querry("SELECT name FROM plot WHERE owner = '" + uuid + "'");
            while (rs.next()) {
                list.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public static void setName(String newname, String oldname) {
        MySQL.update("UPDATE plot SET name = '" + newname + "' WHERE name = '" + oldname + "'");
    }

    public static UUID getOwner(String plot) {
        try {
            ResultSet rs = MySQL.querry("SELECT owner FROM plot WHERE name = '" + plot + "'");
            while (rs.next()) {
                String s = rs.getString("owner");
                if (!s.equals("null")) {
                    return UUID.fromString(s);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setOwner(UUID uuid, String plot) {
        String owner;
        if (uuid != null) {
            owner = uuid.toString();
        } else {
            owner = "null";
        }
        MySQL.update("UPDATE plot SET owner = '" + owner + "' WHERE name = '" + plot + "'");

    }

    public static boolean isCity(String plot) {
        try {
            ResultSet rs = MySQL.querry("SELECT city FROM plot WHERE name = '" + plot + "'");
            while (rs.next()) {
                return rs.getBoolean("city");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void setCity(boolean city, String plot) {
        MySQL.update("UPDATE plot SET city = " + city + " WHERE name = '" + plot + "'");
    }

    public static long getPrice(String plot) {
        try {
            ResultSet rs = MySQL.querry("SELECT price FROM plot WHERE name = '" + plot + "'");
            while (rs.next()) {
                return rs.getInt("price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void setPrice(long price, String plot) {
        MySQL.update("UPDATE plot SET price = " + price + " WHERE name = '" + plot + "'");
    }

    public static boolean isPurchaseable(String plot) {
        try {
            ResultSet rs = MySQL.querry("SELECT purchaseable FROM plot WHERE name = '" + plot + "'");
            while (rs.next()) {
                return rs.getBoolean("purchaseable");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void setPurchaseable(boolean purchaseable, String plot) {
        MySQL.update("UPDATE plot SET purchaseable = " + purchaseable + " WHERE name = '" + plot + "'");
    }

    public static void addMember(UUID member, String plot) {
        MySQL.update("INSERT INTO member VALUES ('" + plot + "', '" + member + "')");
    }

    public static void removeMember(UUID member, String plot) {
        MySQL.update("DELETE FROM member WHERE member = '" + member + "' AND plot = '" + plot + "'");
    }

    public static ArrayList<UUID> getMembers(String plot) {
        ArrayList<UUID> list = new ArrayList<>();
        try {
            ResultSet rs = MySQL.querry("SELECT member FROM plot WHERE plot = '" + plot + "'");
            while (rs.next()) {
                list.add(UUID.fromString(rs.getString("member")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void setMembers(ArrayList<UUID> list, String plot) {
        MySQL.update("DELETE FROM member WHERE plot = '" + plot + "'");
        if (list.size() != 0) {
            list.forEach(entry -> {
                addMember(entry, plot);
            });
        }
    }






}
