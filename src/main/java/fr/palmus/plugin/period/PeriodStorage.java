package fr.palmus.plugin.period;

import fr.palmus.plugin.EvoPlugin;
import fr.palmus.plugin.enumeration.Period;
import fr.palmus.plugin.period.key.StorageKey;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.Set;

public class PeriodStorage {

    EvoPlugin main = EvoPlugin.getInstance();

    public HashMap<Block, Integer> getBreakableMap(StorageKey key) {
        HashMap map;

        switch (key.get()) {
            case 1:
                map = new HashMap<Material, Integer>() {{
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
            case 2:
                map = new HashMap<Material, Integer>() {{
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
            default:
                throw new IllegalStateException("Unexpected value: " + key);
        }
    }

    public HashMap<Material, Set> getKillMap(StorageKey key){
        HashMap map;

        switch (key.get()){
            case 1:
                map = new HashMap<Material, Set>(){{
                    put(Material.SKELETON_SKULL, Set.of(75, "§eDésosseur"));
                    put(Material.ZOMBIE_HEAD, Set.of(75, "§eAffamé"));
                }};
                break;

            case 2:
                map = new HashMap<Material, Set>(){{
                    put(Material.SKELETON_SKULL, Set.of(75, "§eDésosseur"));
                    put(Material.ZOMBIE_HEAD, Set.of(75, "§eAffamé"));
                }};
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + key);
        }

        return map;
    }

    public HashMap<Material, Integer> getCraftMap(StorageKey key){
        HashMap map;

        switch (key.get()){
            case 1:
                map = new HashMap<Material, Integer>(){{
                    put(Material.STRING, 10);
                    put(Material.WOODEN_AXE, 25);
                    put(Material.WOODEN_HOE, 25);
                    put(Material.WOODEN_PICKAXE, 25);
                    put(Material.WOODEN_SWORD, 25);
                }};
                break;

            case 2:
                map = new HashMap<Material, Integer>(){{
                    put(Material.STRING, 10);
                    put(Material.WOODEN_AXE, 25);
                    put(Material.WOODEN_HOE, 25);
                    put(Material.WOODEN_PICKAXE, 25);
                    put(Material.WOODEN_SWORD, 25);
                }};
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + key);
        }

        return map;
    }
}
