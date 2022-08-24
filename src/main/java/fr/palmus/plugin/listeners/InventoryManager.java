package fr.palmus.plugin.listeners;

import fr.palmus.plugin.EvoPlugin;
import fr.palmus.plugin.period.PeriodInventory;
import fr.palmus.plugin.period.PeriodStorage;
import fr.palmus.plugin.player.CustomPlayer;
import fr.palmus.plugin.player.PlayerPeriod;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class InventoryManager implements Listener {

    EvoPlugin main = EvoPlugin.getInstance();

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getView().getTitle().equalsIgnoreCase("§eInformations")){
            Player pl = (Player) e.getWhoClicked();
            PlayerPeriod customPlayer = main.getCustomPlayer().get(pl);
            if(e.getCurrentItem().getType() == Material.BARRIER){
                e.getWhoClicked().closeInventory();
            }

            if(e.getCurrentItem().getType() == Material.EXPERIENCE_BOTTLE){
                Inventory inv = new PeriodInventory().getExpInventory(pl, main.getPeriodCaster().getStorageKey(customPlayer.getPeriod(), customPlayer.getLimiter()));
                pl.openInventory(inv);
            }

            if(e.getCurrentItem().getType() == Material.IRON_PICKAXE){
                Inventory inv = new PeriodInventory().getCraftInventory(pl, main.getPeriodCaster().getStorageKey(customPlayer.getPeriod(), customPlayer.getLimiter()));
                pl.openInventory(inv);
                pl.playSound(pl.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
            }

            if(e.getCurrentItem().getType() == Material.CRAFTING_TABLE){
                Inventory inv = new PeriodInventory().getKillInventory(pl, main.getPeriodCaster().getStorageKey(customPlayer.getPeriod(), customPlayer.getLimiter()));
                pl.openInventory(inv);
                pl.playSound(pl.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
            }

            if(e.getCurrentItem().getType() == Material.WOODEN_SWORD){
                Inventory inv = new PeriodInventory().getExpInventory(pl, main.getPeriodCaster().getStorageKey(customPlayer.getPeriod(), customPlayer.getLimiter()));
                pl.openInventory(inv);
                pl.playSound(pl.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
            }
            e.setCancelled(true);
        }
    }
}
