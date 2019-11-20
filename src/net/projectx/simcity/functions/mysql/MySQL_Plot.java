package net.projectx.simcity.functions.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * ~Yannick on 19.11.2019 at 09:19 o´ clock
 */
public class MySQL_Plot {

    public static void createPlotTable() {
        MySQL.update("CREATE TABLE IF NOT EXISTS plot (name VARCHAR(20), owner VARCHAR(64), boolean city)");
    }

    public static void createPlotMemberTable() {
        MySQL.update("CREATE TABLE IF NOT EXISTS member (plot VARCHAR 20, member VARCHAR(64))");
    }

    public static void createPlot(String name, boolean city) {
        MySQL.update("INSERT INTO plot VALUES ('" + name + "', 'null', " + city + ")");
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


    public static void setName(String newname, String oldname) {
        MySQL.update("UPDATE plot SET name = '" + newname + "' WHERE name = '" + oldname + "'");
    }

    public static String getOwner(String plot) {
        try {
            ResultSet rs = MySQL.querry("SELECT owner FROM plot WHERE name = '" + plot + "'");
            while (rs.next()) {
                return rs.getString("owner");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
