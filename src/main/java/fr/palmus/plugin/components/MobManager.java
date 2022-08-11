package fr.palmus.plugin.components;

import fr.palmus.plugin.Main;
import fr.palmus.plugin.utils.ItemBuilder;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;

import java.util.concurrent.ThreadLocalRandom;

public class MobManager {

    EntityType type;

    public MobManager(EntityType type){
        this.type = type;

    }

    public static void spawnMob(double x, double z, World world){
        if(!Main.getInstance().FarmlandsModules){
            return;
        }
        double xCo = x;
        double zCo = z;
        int choose = ThreadLocalRandom.current().nextInt(1, 3);
        int yCo = world.getHighestBlockYAt((int)x, (int)z);
        Location location = new Location(world, xCo, yCo, zCo);
        System.out.println("2");
        if(choose == 1){
            int number = ThreadLocalRandom.current().nextInt(1, 6);
            System.out.println(number);
            for(int i = 1; i < number; i++){
                int variation = ThreadLocalRandom.current().nextInt(1, 6);

                Zombie zombie = (Zombie) world.spawnEntity(location.add(variation, 0 ,variation), EntityType.ZOMBIE);
                zombie.getEquipment().setHelmet(new ItemBuilder(Material.LEATHER_HELMET).setLeatherArmorColor(Color.YELLOW).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack());
                zombie.getEquipment().setChestplate(new ItemBuilder(Material.LEATHER_CHESTPLATE).setLeatherArmorColor(Color.YELLOW).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack());
                zombie.getEquipment().setBoots(new ItemBuilder(Material.LEATHER_BOOTS).setLeatherArmorColor(Color.YELLOW).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack());
                zombie.getEquipment().setLeggings(new ItemBuilder(Material.LEATHER_LEGGINGS).setLeatherArmorColor(Color.YELLOW).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack());
                zombie.getEquipment().setItemInHand(new ItemBuilder(Material.BONE).toItemStack());
                zombie.setMaxHealth(5);
                zombie.setBaby(false);
                zombie.setVillager(false);
                zombie.setCustomName("§eAffamé");
                zombie.setCustomNameVisible(true);
            }
        }

        if(choose == 2){
            int number = ThreadLocalRandom.current().nextInt(1, 6);

            for(int i = 1; i < number; i++){
                int variation = ThreadLocalRandom.current().nextInt(1, 6);
                Skeleton zombie = (Skeleton) world.spawnEntity(location.add(variation, 0 ,variation), EntityType.SKELETON);

                zombie.getEquipment().setHelmet(new ItemBuilder(Material.LEATHER_HELMET).setLeatherArmorColor(Color.YELLOW).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack());
                zombie.getEquipment().setChestplate(new ItemBuilder(Material.LEATHER_CHESTPLATE).setLeatherArmorColor(Color.WHITE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack());
                zombie.getEquipment().setBoots(new ItemBuilder(Material.LEATHER_BOOTS).setLeatherArmorColor(Color.YELLOW).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack());
                zombie.getEquipment().setLeggings(new ItemBuilder(Material.LEATHER_LEGGINGS).setLeatherArmorColor(Color.YELLOW).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack());
                zombie.getEquipment().setItemInHand(new ItemBuilder(Material.BONE).toItemStack());
                zombie.setCanPickupItems(false);
                zombie.setMaxHealth(5);
                zombie.setCustomName("§eDésosseur");
                zombie.setCustomNameVisible(true);
            }
        }
    }
}
