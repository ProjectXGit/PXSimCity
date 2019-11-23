package net.projectx.simcity.functions.mysql;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MySQL_Auftrag {

    public static void createAuftragTable(){
        MySQL.update("CREATE IF NOT EXISTS auftrag (auftragname VARCHAR(64), auftragsbeschreibung VARCHAR(300), spielername VARCHAR(64), plot VARCHAR(64), belohnung BIGINT)");
    }

    public static void createAuftrag(String auftragname, String auftragsbeschreibung, String plot, BigInteger belohnung){
        MySQL.update("INSERT INTO auftrag VALUES ('" + auftragname + "', '" + auftragsbeschreibung + "', 'null', '" + plot + "', '" + belohnung + "')");
    }

    public static void deleteAuftrag(String auftragname){
        MySQL.querry("DELETE FROM auftrag WHRE auftragname = '" + auftragname + "'");
    }

    public static ArrayList<String> getAuftraege() {
        ArrayList<String> list = new ArrayList<>();
        try {
            ResultSet rs = MySQL.querry("SELECT auftragname FROM auftrag WHERE 1");
            while (rs.next()) {
                list.add(rs.getString("auftragname "));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<String> getAuftragsBeschreibung() {
        ArrayList<String> list = new ArrayList<>();
        try {
            ResultSet rs = MySQL.querry("SELECT auftragsbeschreibung FROM auftrag WHERE 1");
            while (rs.next()) {
                list.add(rs.getString("auftragsbeschreibung "));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<String> getAuftraegeFrei() {
        ArrayList<String> list = new ArrayList<>();
        try {
            ResultSet rs = MySQL.querry("SELECT auftragname FROM auftrag WHERE 1");
            while (rs.next()) {
                list.add(rs.getString("auftrag "));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void acceptAuftrag(String auftragname, String spielername){
        MySQL.update("UPDATE auftrag SET spielername = '" + spielername + "' WHERE auftragname = '" + auftragname+ "'");
    }

    public static void rejectAuftrag(String auftragname, String spielername){
        MySQL.update("UPDATE auftrag SET spielername = 'null' WHERE auftragname = '" + auftragname + "'");
    }

    public static void changeBelohnung(String auftragname, BigInteger belohnung){
        MySQL.update("UPDATE auftrag SET belohnung = '" + belohnung + "' WHERE auftragname = '" + auftragname + "'");
    }
}
