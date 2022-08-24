package fr.palmus.plugin.period;

import fr.palmus.plugin.EvoPlugin;
import fr.palmus.plugin.period.key.StorageKey;
import fr.palmus.plugin.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Predicate;

public class PeriodInventory {

    EvoPlugin main = EvoPlugin.getInstance();

    PeriodStorage storage = new PeriodStorage();

    public Inventory getExpInventory(Player pl, StorageKey key){
        Inventory inv = Bukkit.createInventory(pl, 45, main.getComponents().getString("inventory.info_name").replace('&', '§'));

        main.getComponents().setDefaultMaterial(inv, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack());

        for(int i = 0; i < 9; i++){
            inv.setItem(i, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).toItemStack());
        }

        for(int i = inv.getSize() - 9; i < inv.getSize(); i++){
            inv.setItem(i, new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).toItemStack());
        }

        inv.setItem(4, new ItemBuilder(Material.IRON_PICKAXE, 1).addEnchant(Enchantment.DURABILITY, 1).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).addItemFlag(ItemFlag.HIDE_ENCHANTS).setName("§6Comment exp ?").setLore(new ArrayList<>(Arrays.asList("", "§eDe l'exp peut être gagner", "§een §6cassant §e les blocks ci-dessous", "§ecliquez pour voir les autres moyen d'exp", ""))).toItemStack());
        inv.setItem(inv.getSize() - 5, new ItemBuilder(Material.BARRIER).setName("§cFermer").addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack());

        int i = 9;
        for(Block block : storage.getBreakableMap(key).keySet()){
            ItemStack blc = new ItemBuilder(Material.STRING).setName("§a" + main.getComponents().firstLetterUp(block.getType().getData().toString().toLowerCase().replace("_", " "))).setLore(Arrays.asList("§7-------------", "§eExp: " + storage.getBreakableMap(key).get(block), "§eType: Blocks", "§7-------------", "§aPériode: " + main.getCustomPlayer().get(pl).getEntirePeriodStyle(), "§7-------------")).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack();
            inv.setItem(i, blc);
        }

        return inv;
    }

    public Inventory getKillInventory(Player pl, StorageKey key){
        Inventory inv = Bukkit.createInventory(pl, 45, main.getComponents().getString("inventory.info_name").replace('&', '§'));

        main.getComponents().setDefaultMaterial(inv, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack());

        for(int i = 0; i < 9; i++){
            inv.setItem(i, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).toItemStack());
        }

        for(int i = inv.getSize() - 9; i < inv.getSize(); i++){
            inv.setItem(i, new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).toItemStack());
        }

        inv.setItem(4, new ItemBuilder(Material.WOODEN_SWORD, 1).addEnchant(Enchantment.DURABILITY, 1).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).addItemFlag(ItemFlag.HIDE_ENCHANTS).setName("§6Comment exp ?").setLore(new ArrayList<>(Arrays.asList("", "§eDe l'exp peut être gagner", "§een §6Tuant §e les mobs ci-dessous", "§evous pouvez tuer ces mobs dans la farmlands", "§ecliquez pour voir les autres moyen d'exp", ""))).toItemStack());
        inv.setItem(inv.getSize() - 5, new ItemBuilder(Material.BARRIER).setName("§cFermer").addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack());

        int i = 9;
        for(Material mat : storage.getKillMap(key).keySet()){
            boolean hasRun = false;
            String name = null;
            int exp = 0;
            for(Iterator it = storage.getKillMap(key).get(mat).iterator(); it.hasNext();){
                if(hasRun){
                    exp = (int) it.next();
                    break;
                }else {
                    name = (String) it.next();
                    hasRun = true;
                }
            }
            ItemStack blc = new ItemBuilder(Material.STRING).setName("§a" + main.getComponents().firstLetterUp(name)).setLore(Arrays.asList("§7-------------", "§eExp: " + exp, "§eType: Mobs", "§7-------------", "§aPériode: " + main.getCustomPlayer().get(pl).getEntirePeriodStyle(), "§7-------------")).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack();
            inv.setItem(i, blc);
        }
        return inv;
    }

    public Inventory getCraftInventory(Player pl, StorageKey key){
        Inventory inv = Bukkit.createInventory(pl, 45, main.getComponents().getString("inventory.info_name").replace('&', '§'));

        main.getComponents().setDefaultMaterial(inv, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack());

        for(int i = 0; i < 9; i++){
            inv.setItem(i, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).toItemStack());
        }

        for(int i = inv.getSize() - 9; i < inv.getSize(); i++){
            inv.setItem(i, new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).toItemStack());
        }

        inv.setItem(4, new ItemBuilder(Material.CRAFTING_TABLE, 1).addEnchant(Enchantment.DURABILITY, 1).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).addItemFlag(ItemFlag.HIDE_ENCHANTS).setName("§6Comment exp ?").setLore(new ArrayList<>(Arrays.asList("", "§eDe l'exp peut être gagner", "§een §6craftant §e les items ci-dessous", "§ecliquez pour voir les autres moyen d'exp", ""))).toItemStack());
        inv.setItem(inv.getSize() - 5, new ItemBuilder(Material.BARRIER).setName("§cFermer").addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack());

        int i = 9;
        for(Material item : storage.getCraftMap(key).keySet()){
            ItemStack blc = new ItemBuilder(Material.STRING).setName("§a" + main.getComponents().firstLetterUp(item.getData().toString().toLowerCase().replace("_", " "))).setLore(Arrays.asList("§7-------------", "§eExp: " + storage.getBreakableMap(key).get(item), "§eType: Items", "§7-------------", "§aPériode: " + main.getCustomPlayer().get(pl).getEntirePeriodStyle(), "§7-------------")).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack();
            inv.setItem(i, blc);
        }

        return inv;
    }
}
