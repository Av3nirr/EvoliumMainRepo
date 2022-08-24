package fr.palmus.plugin.mysql;

import fr.palmus.plugin.EvoPlugin;
import org.bukkit.ChatColor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class DBConnection {

    private DBCredentials dbCredentials;

    private Connection connection;

    private boolean connected;

    EvoPlugin main = EvoPlugin.getInstance();

    public DBConnection(DBCredentials dbCredentials){
        this.dbCredentials = dbCredentials;
        this.connect();
    }

    public void connect(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(this.dbCredentials.toURI(), this.dbCredentials.getUser(), this.dbCredentials.getPass());
            connected = true;
            main.log.log(Level.INFO, ChatColor.GREEN + "MYSQL connected to " + this.dbCredentials.toURI());
            main.log.log(Level.INFO, ChatColor.GREEN + "MYSQL module successfully loaded");
        }catch (SQLException | ClassNotFoundException e){
            connected = false;
            main.log.log(Level.SEVERE, ChatColor.RED + "Failed to bind to mysql database (" + this.dbCredentials.toURI() + "), maybe the credentials are incorrect. FATAL");
            e.printStackTrace();
            main.getPluginLoader().disablePlugin(main);
        }
    }

    public void close() throws SQLException {
        if(!this.connection.isClosed()){
            this.connection.close();
        }
    }

    public Connection getConnection() throws SQLException {
        if(this.connection != null){
            if(!this.connection.isClosed()){
                return this.connection;
            }
        }
        connect();
        return this.connection;
    }

    public boolean isConnected(){
        return connected;
    }
}
