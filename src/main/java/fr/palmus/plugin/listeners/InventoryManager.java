package fr.palmus.plugin.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryManager implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getView().getTitle().equalsIgnoreCase("§eInformations")){
            if(e.getCurrentItem().getType() == Material.BARRIER){
                e.getWhoClicked().closeInventory();
            }
            e.setCancelled(true);
        }
    }
}
