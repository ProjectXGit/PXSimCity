package net.projectx.simcity.functions.mysql;

/**
 * ~Yannick on 19.11.2019 at 09:19 o´ clock
 */
public class MySQL_Plot {

    public static void createPlotTable() {
        MySQL.update("CREATE TABLE IF NOT EXISTS plot (name VARCHAR(20), owner VARCHAR(64), )");
    }

    public static void createPlotMemberTable() {
        MySQL.update("CREATE TABLE IF NOT EXISTS member (plot VARCHAR 20, member VARCHAR(64))");
    }


}
