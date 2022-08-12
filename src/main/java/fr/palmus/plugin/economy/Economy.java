package fr.palmus.plugin.economy;

import fr.palmus.plugin.EvoPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;

public class Economy {

    EvoPlugin main;

    public File file;
    public FileConfiguration cfg;

    private HashMap<Player, EconPlayerManager> econManagement;

    public Economy(EvoPlugin main){
        this.main = main;
        setup();
    }

    public void setup(){
        main.log.log(Level.INFO, ChatColor.GOLD + "Starting Economy module...");
        file = new File("plugins/EvoPlugin", "economy.yml");
        cfg = YamlConfiguration.loadConfiguration(file);
        main.log.log(Level.INFO, ChatColor.GOLD + "Economy config files loaded");
        econManagement = new HashMap<>();
        main.log.log(Level.INFO, ChatColor.GOLD + "Economy storage loaded");
        main.log.log(Level.INFO, ChatColor.GREEN + "Economy module successfully load !");
    }

    public EconPlayerManager getPlayerEcon(Player pl){
        return econManagement.get(pl);
    }

    public void initPlayerEcon(Player pl){

        if(!cfg.contains(pl.getDisplayName())){
            cfg.set(pl.getDisplayName() + ".money", 0);
            cfg.set(pl.getDisplayName() + ".bank", 0);
        }

        econManagement.put(pl, new EconPlayerManager(pl, this));
    }
}
