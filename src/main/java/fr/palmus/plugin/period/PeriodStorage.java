package fr.palmus.plugin.period;

import fr.palmus.plugin.EvoPlugin;
import fr.palmus.plugin.enumeration.Period;
import fr.palmus.plugin.period.key.StorageKey;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class PeriodStorage {

    EvoPlugin main = EvoPlugin.getInstance();

    public HashMap<Material, Integer> getBreakableMap(StorageKey key) {
        HashMap<Material, Integer> map;

        switch (key.get()) {
            case 1 -> {
                map = new HashMap<>() {{
                    put(Material.STONE, 3);
                    put(Material.ACACIA_LOG, 4);
                    put(Material.BIRCH_LOG, 4);
                    put(Material.DARK_OAK_LOG, 4);
                    put(Material.OAK_LOG, 4);
                    put(Material.JUNGLE_LOG, 4);
                    put(Material.SPRUCE_LOG, 4);
                    put(Material.OAK_LEAVES, 1);
                    put(Material.ACACIA_LEAVES, 1);
                    put(Material.BIRCH_LEAVES, 1);
                    put(Material.DARK_OAK_LEAVES, 1);
                    put(Material.JUNGLE_LEAVES, 1);
                    put(Material.SPRUCE_LEAVES, 1);
                    put(Material.GRASS_BLOCK, 1);
                    put(Material.DIRT, 1);
                }};
                return map;
            }
            case 2 -> {
                map = new HashMap<>() {{
                    put(Material.STONE, 3);
                    put(Material.ACACIA_LOG, 4);
                    put(Material.BIRCH_LOG, 4);
                    put(Material.DARK_OAK_LOG, 4);
                    put(Material.OAK_LOG, 4);
                    put(Material.JUNGLE_LOG, 4);
                    put(Material.SPRUCE_LOG, 4);
                    put(Material.OAK_LEAVES, 1);
                    put(Material.ACACIA_LEAVES, 1);
                    put(Material.BIRCH_LEAVES, 1);
                    put(Material.DARK_OAK_LEAVES, 1);
                    put(Material.JUNGLE_LEAVES, 1);
                    put(Material.SPRUCE_LEAVES, 1);
                    put(Material.GRASS_BLOCK, 1);
                    put(Material.DIRT, 1);
                }};
                return map;
            }
            default -> throw new IllegalStateException("Unexpected value: " + key);
        }
    }

    public HashMap<Material, ArrayList<String>> getKillMap(StorageKey key){
        HashMap<Material, ArrayList<String>> map = switch (key.get()) {
            case 1 -> new HashMap<>() {{
                put(Material.SKELETON_SKULL, new ArrayList<String>(Arrays.asList("75", "§eDésosseur")));
                put(Material.ZOMBIE_HEAD, new ArrayList<String>(Arrays.asList("75", "§eAffamé")));
            }};
            case 2 -> new HashMap<>() {{
                put(Material.SKELETON_SKULL, new ArrayList<String>(Arrays.asList("75", "§eDésosseur")));
                put(Material.ZOMBIE_HEAD, new ArrayList<String>(Arrays.asList("75", "§eAffamé")));
            }};
            default -> throw new IllegalStateException("Unexpected value: " + key);
        };

        return map;
    }

    public HashMap<Material, Integer> getCraftMap(StorageKey key){
        HashMap<Material, Integer> map;

        switch (key.get()){
            case 1 -> {
                map = new HashMap<>() {{
                    put(Material.STRING, 10);
                    put(Material.WOODEN_AXE, 25);
                    put(Material.WOODEN_HOE, 25);
                    put(Material.WOODEN_PICKAXE, 25);
                    put(Material.WOODEN_SWORD, 25);
                }};
                return map;
            }

            case 2 -> {
                map = new HashMap<>() {{
                    put(Material.STRING, 10);
                    put(Material.WOODEN_AXE, 25);
                    put(Material.WOODEN_HOE, 25);
                    put(Material.WOODEN_PICKAXE, 25);
                    put(Material.WOODEN_SWORD, 25);
                }};
                return map;
            }
            default->throw new IllegalStateException("Unexpected value: " + key);
        }
    }
}