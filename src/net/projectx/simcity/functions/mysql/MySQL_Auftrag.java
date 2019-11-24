package net.projectx.simcity.functions.mysql;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class MySQL_Auftrag {

    public static void createAuftragTable(){
        MySQL.update("CREATE TABLE IF NOT EXISTS auftrag (auftragname VARCHAR(64), auftragsbeschreibung VARCHAR(200), spielername VARCHAR(64), plot VARCHAR(64), belohnung INTEGER)");
    }

    public static void createAuftrag(String auftragname, String plot, Integer belohnung){
        MySQL.update("INSERT INTO auftrag VALUES ('" + auftragname + "', 'null', 'null', '" + plot + "', '" + belohnung + "')");
    }
    public static void createAuftrag(String auftragname, Integer belohnung){
        MySQL.update("INSERT INTO auftrag VALUES ('" + auftragname + "', 'null', 'null', 'null', '" + belohnung + "')");
    }
    public static void setBeschreibung(String auftragname, String beschreibung){
        MySQL.update("UPDATE auftrag SET auftragsbeschreibung = '" + beschreibung + "' WHERE auftragname = '" + auftragname + "'");
    }

    public static void deleteAuftrag(String auftragname){
        MySQL.update("DELETE FROM auftrag WHERE auftragname = '" + auftragname + "'");
    }

    public static ArrayList<String> getAuftraege() {
        ArrayList<String> list = new ArrayList<>();
        try {
            ResultSet rs = MySQL.querry("SELECT auftragname FROM auftrag WHERE 1");
            while (rs.next()) {
                list.add(rs.getString("auftragname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static String getAuftragOwner(String auftragname){
        try {
            ResultSet rs = MySQL.querry("SELECT spielername FROM auftrag WHERE auftragname = '" + auftragname + "'");
            while (rs.next()) {
                String s = rs.getString("spielername");
                if (!s.equals("null")) {
                    return s;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getAuftragsBeschreibung(String auftragname) {
        try {
            ResultSet rs = MySQL.querry("SELECT auftragsbeschreibung FROM auftrag WHERE auftragname = '" + auftragname + "'");
            while (rs.next()) {
                String s = rs.getString("auftragsbeschreibung");
                if (!s.equals("null")) {
                    return s;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Integer getAuftragsBelohnung(String auftragname) {
        try {
            ResultSet rs = MySQL.querry("SELECT belohnung FROM auftrag WHERE auftragname = '" + auftragname + "'");
            while (rs.next()) {
                Integer s = rs.getInt("belohnung");
               return s;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<String> getAuftraegeFrei() {
        ArrayList<String> list = new ArrayList<>();
        try {
            ResultSet rs = MySQL.querry("SELECT auftragname FROM auftrag WHERE spielername = 'null'");
            while (rs.next()) {
                list.add(rs.getString("auftragname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static String getPlot(String auftragname){
        try {
            ResultSet rs = MySQL.querry("SELECT plot FROM auftrag WHERE auftragname = '" + auftragname + "'");
            while (rs.next()) {
                String s = rs.getString("plot");
                if (!s.equals("null")) {
                    return s;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void acceptAuftrag(String auftragname, String spielername){
        MySQL.update("UPDATE auftrag SET spielername = '" + spielername + "' WHERE auftragname = '" + auftragname+ "'");
    }

    public static void rejectAuftrag(String auftragname){
        MySQL.update("UPDATE auftrag SET spielername = 'null' WHERE auftragname = '" + auftragname + "'");
    }

    public static void changeBelohnung(String auftragname, Integer belohnung){
        MySQL.update("UPDATE auftrag SET belohnung = '" + belohnung + "' WHERE auftragname = '" + auftragname + "'");
    }
}
