package fr.palmus.plugin.components;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;

public class Crate {
    private String name;
    private Location loc;
    private String desc;
    private World world;
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
