package fr.palmus.plugin.components;

import fr.palmus.plugin.EvoPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;

public class Crate {
    EvoPlugin main;
    private String name;
    private Location loc;
    private String desc;
    private World world;
    public File file;
    public FileConfiguration cfg;
    public Crate(String name, Location loc, String desc, World world){
        this.name = name;
        this.loc = loc;
        this.desc = desc;
        this.world = world;
    }

    public String getName(){
        return this.name;
    }
    public Location getLoc(){
        return this.loc;
    }

    public String getDesc() {
        return this.desc;
    }

    public World getWorld() {
        return world;
    }
    /**public void setup(){
        main.log.log(Level.INFO, ChatColor.GOLD + "Starting Crate Initialisation...");
        if (file == null) {
            file = new File("plugins/EvoPlugin", "crates.yml");
        }
        if (!file.exists()) {
            main.saveResource("crate.yml", false);
        }
        cfg = YamlConfiguration.loadConfiguration(file);
        main.log.log(Level.INFO, ChatColor.GOLD + "Crate storage loaded");
    }**/
    public void setArmorStand(){
        ArmorStand armorStand = this.world.spawn(this.loc, ArmorStand.class);
        armorStand.setCustomName(this.name);
        armorStand.setVisible(false);
        armorStand.setCustomNameVisible(true);
        armorStand.setCollidable(false);
        armorStand.setInvulnerable(false);
        armorStand.setSmall(true);
    }
}
