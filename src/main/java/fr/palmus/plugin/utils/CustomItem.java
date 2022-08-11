package fr.palmus.plugin.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Array;
import java.util.Arrays;

public class CustomItem {

    public static ItemStack fiber = new ItemBuilder(Material.STRING).setName("§aFIBRE").setLore(Arrays.asList("§7-------------", "§eRareté: ⭐", "§eType: Utils", "§7-------------", "§aPériode: Préhistoire I", "§aUtilité: Craft Ficelle", "§7-------------")).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack();
    public static ItemStack massue = new ItemBuilder(Material.STONE_AXE).setName("§eMASSUE").setLore(Arrays.asList("§7-------------", "§eRareté: ⭐⭐", "§eType: Armes", "§eDamage: +20", "§7-------------", "§aPériode: Préhistoire I", "§aUtilité: Dégats-Boost", "§7-------------")).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack();
    public static ItemStack dirt_upgrade = new ItemBuilder(Material.STRING).setName("§aFIBRE").setLore(Arrays.asList("§7-------------", "§eRareté: ⭐", "§eType: Utils", "§7-------------", "§aPériode: Préhistoire I", "§aUtilité: Craft various", "§7-------------")).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack();

}
