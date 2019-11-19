package net.projectx.simcity.functions.mysql;

/**
 * ~Yannick on 19.11.2019 at 09:19 o´ clock
 */
public class MySQL_Plot {

    public static void createPlotTable() {
        MySQL.update("CREATE TABLE IF NOT EXISTS plot ");
    }
}
