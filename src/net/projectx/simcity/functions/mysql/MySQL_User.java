package net.projectx.simcity.functions.mysql;

import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * ~Yannick on 13.11.2019 at 18:59 o´ clock
 */
public class MySQL_User {
    public static void createUserTable() {
        MySQL.update("CREATE TABLE IF NOT EXISTS user(name VARCHAR(20), uuid VARCHAR(64), dukaten BIGINT, job VARCHAR(20), playtime BIGINT,  lastJoin DATETIME)");
    }

    public static void createUser(Player p) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = Date.from(Instant.now());
        System.out.println();
        MySQL.update("INSERT INTO `user` VALUES ('" + p.getName() + "','" + p.getUniqueId().toString() + "', 0, '', 0, '" + sdf.format(time) + "')");
    }

    public static boolean isUserExists(UUID uuid) {
        try {
            ResultSet rs = MySQL.querry("SELECT * FROM user WHERE uuid = '" + uuid + "'");
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ArrayList<UUID> getUsers() {
        ArrayList<UUID> list = new ArrayList<>();
        try {
            ResultSet rs = MySQL.querry("SELECT uuid FROM user WHERE 1");
            while (rs.next()) {
                list.add(UUID.fromString(rs.getString("uuid")));
            }
            return list;
        } catch (SQLException e) {
            return null;
        }
    }

    public static String getName(UUID uuid) {
        try {
            ResultSet rs = MySQL.querry("SELECT name FROM user WHERE uuid = '" + uuid + "'");
            while (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setName(String name, UUID uuid) {
        MySQL.update("UPDATE user SET name = '" + name + "' WHERE uuid = '" + uuid + "'");
    }

    public static int getDukaten(UUID uuid) {
        try {
            ResultSet rs = MySQL.querry("SELECT dukaten FROM user WHERE uuid = '" + uuid + "'");
            while (rs.next()) {
                return rs.getInt("dukaten");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void setDukaten(int dukaten, UUID uuid) {
        MySQL.update("UPDATE user SET dukaten = " + dukaten + " WHERE uuid = '" + uuid + "'");
    }

    public static void addDukaten(int dukaten, UUID uuid) {
        setDukaten(getDukaten(uuid) + dukaten, uuid);
    }

    public static void removeDukaten(int dukaten, UUID uuid) {
        setDukaten(getDukaten(uuid) - dukaten, uuid);
    }

    public static String getJob(UUID uuid) {
        try {
            ResultSet rs = MySQL.querry("SELECT job FROM user WHERE uuid = '" + uuid + "'");
            while (rs.next()) {
                return rs.getString("job");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setJob(String job, UUID uuid) {
        MySQL.update("UPDATE user SET job = '" + job + "' WHERE uuid = '" + uuid + "'");
    }

    public static long getPlaytime(UUID uuid) {
        try {
            ResultSet rs = MySQL.querry("SELECT playtime FROM user WHERE uuid = '" + uuid + "'");
            while (rs.next()) {
                return rs.getLong("playtime");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void setPlaytime(long time, UUID uuid) {
        MySQL.update("UPDATE user SET playtime = " + time + " WHERE uuid = '" + uuid + "'");
    }

    public static void addPlaytime(long time, UUID uuid) {
        setPlaytime(getPlaytime(uuid) + time, uuid);
    }

    public static long getLastJoin(UUID uuid) {
        try {
            ResultSet rs = MySQL.querry("SELECT lastJoin FROM `user` WHERE uuid = '" + uuid + "'");
            return rs.getLong("lastseen");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void setLastJoin(UUID uuid, long time) {
        MySQL.update("UPDATE `user` SET lastJoin = " + time + " WHERE uuid = '" + uuid + "'");
    }


}
