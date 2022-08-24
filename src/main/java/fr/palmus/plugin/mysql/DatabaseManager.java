package fr.palmus.plugin.mysql;

import fr.palmus.plugin.EvoPlugin;
import org.bukkit.ChatColor;

import java.sql.SQLException;
import java.util.logging.Level;

public class DatabaseManager {

    private DBConnection periodConnection;

    EvoPlugin main = EvoPlugin.getInstance();

    public DatabaseManager(){
        this.periodConnection = new DBConnection(new DBCredentials());
    }

    public void close(){
        try {
            this.periodConnection.close();
        } catch (SQLException e) {
            main.log.log(Level.SEVERE, ChatColor.RED + "Failed to bind to mysql database, maybe the credentials are incorrect. FATAL");
            main.getPluginLoader().disablePlugin(main);
        }
    }

    public DBConnection getDatabase() {
        return periodConnection;
    }

}
