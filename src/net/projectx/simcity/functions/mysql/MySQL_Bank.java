package net.projectx.simcity.functions.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/**
 * ~Yannick on 23.11.2019 at 08:12 o´ clock
 */
public class MySQL_Bank {

    public static void createBankTable() {
        MySQL.update("CREATE TABLE IF NOT EXISTS bank(id MEDIUMINT NOT NULL AUTO_INCREMEN, uuid VARCHAR(24), startmoney BIGINT, endmoney BIGINT, end DATETIME, PRIMARY KEY (id)");
    }

    public static void openBankAccount(UUID user, long dukaten, int hours) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = Date.from(Instant.ofEpochSecond(Instant.now().getEpochSecond() + hours * 3600));
        MySQL.update("INSERT INTO bank VALUES('" + user + "', " + dukaten + ", " + dukaten * (getZins(hours) + 1) + " '" + sdf.format(time) + "')");
    }

    public static HashMap<Integer, LocalDateTime> getBankAccounts(UUID uuid) {
        HashMap<Integer, LocalDateTime> map = new HashMap<>();
        try {
            ResultSet rs = MySQL.querry("SELECT id WHERE uuid = '" + uuid + "'");
            while (rs.next()) {
                map.put(rs.getInt("id"), getEndTime(rs.getInt("id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static long getStartmoney(int id) {
        try {
            ResultSet rs = MySQL.querry("SELECT startmoney WHERE id = " + id);
            while (rs.next()) {
                rs.getLong("startmoney");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static long getEndmoney(int id) {
        try {
            ResultSet rs = MySQL.querry("SELECT endmoney WHERE id = " + id);
            while (rs.next()) {
                rs.getLong("endmoney");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static LocalDateTime getEndTime(int id) {
        ResultSet rs = MySQL.querry("SELECT  end FROM user WHERE id = " + id + "");
        try {
            while (rs.next()) {
                return LocalDateTime.of(rs.getDate("end").toLocalDate(), rs.getTime("end").toLocalTime());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static double getZins(int hours) {
        return hours / 10 * 0.01;
    }
}
