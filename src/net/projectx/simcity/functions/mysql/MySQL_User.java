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
        MySQL.update("CREATE TABLE IF NOT EXISTS user(name VARCHAR(20), uuid VARCHAR(64), address VARCHAR(64), dukaten BIGINT, job VARCHAR(20), playtime BIGINT,  lastJoin DATETIME)");
    }

    public static void createUser(Player p) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = Date.from(Instant.now());
        String ip = "" + p.getAddress().getAddress().getHostAddress();
        MySQL.update("INSERT INTO `user` VALUES ('" + p.getName() + "','" + p.getUniqueId().toString() + "', '" + ip + "', 5000, '', 0, '" + sdf.format(time) + "')");
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

    public static boolean isUserExists(String name) {
        try {
            ResultSet rs = MySQL.querry("SELECT * FROM user WHERE name = '" + name + "'");
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

    public static UUID getUUID(String name) {
        try {
            ResultSet rs = MySQL.querry("SELECT uuid FROM user WHERE name = '" + name + "'");
            while (rs.next()) {
                return UUID.fromString(rs.getString("uuid"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

    public static String getNameByAddress(String address) {
        try {
            ResultSet rs = MySQL.querry("SELECT name FROM user WHERE address = '" + address + "'");
            while (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<String> getName(String job) {
        ArrayList<String> list = new ArrayList<>();
        try {
            ResultSet rs = MySQL.querry("SELECT name FROM user WHERE job = '" + job + "'");
            while (rs.next()) {
                list.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void setName(String name, UUID uuid) {
        MySQL.update("UPDATE user SET name = '" + name + "' WHERE uuid = '" + uuid + "'");
    }

    public static String getAddress(UUID uuid) {
        try {
            ResultSet rs = MySQL.querry("SELECT address FROM user WHERE uuid = '" + uuid + "'");
            while (rs.next()) {
                return rs.getString("address");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setAddress(String address, UUID uuid) {
        MySQL.update("UPDATE user SET address = '" + address + "' WHERE uuid = '" + uuid + "'");
    }

    public static long getDukaten(UUID uuid) {
        try {
            ResultSet rs = MySQL.querry("SELECT dukaten FROM user WHERE uuid = '" + uuid + "'");
            while (rs.next()) {
                return rs.getLong("dukaten");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void setDukaten(long dukaten, UUID uuid) {
        MySQL.update("UPDATE user SET dukaten = " + dukaten + " WHERE uuid = '" + uuid + "'");
    }

    public static void addDukaten(long dukaten, UUID uuid) {
        setDukaten(getDukaten(uuid) + dukaten, uuid);
    }

    public static void removeDukaten(long dukaten, UUID uuid) {
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
