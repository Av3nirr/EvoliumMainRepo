package fr.palmus.plugin.mysql;

import fr.palmus.plugin.EvoPlugin;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.logging.Level;

public class DBCredentials {

    public File file;
    public FileConfiguration cfg;

    private String host;

    private String user;

    private String pass;

    private String dbName;

    private String dbType;

    private int port;

    EvoPlugin main = EvoPlugin.getInstance();

    public DBCredentials(){
        main.log.log(Level.INFO, ChatColor.GOLD + "Starting MYSQL module...");
        if (file == null) {
            file = new File("plugins/EvoPlugin", "mysql.yml");
        }
        if (!file.exists()) {
            main.saveResource("mysql.yml", false);
        }
        cfg = YamlConfiguration.loadConfiguration(file);
        main.log.log(Level.INFO, ChatColor.GOLD + "MYSQL config files generated");

        host = cfg.getString("host");
        user = cfg.getString("user");
        pass = cfg.getString("pass");
        dbName = cfg.getString("name");
        dbType = cfg.getString("dbType");
        port = cfg.getInt("port");
    }

    public String toURI(){
        final StringBuilder sb = new StringBuilder();
        sb.append("jdbc:" + dbType + "://")
                .append(host)
                .append(":")
                .append(port)
                .append("/")
                .append(dbName);

        return sb.toString();
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }
}
