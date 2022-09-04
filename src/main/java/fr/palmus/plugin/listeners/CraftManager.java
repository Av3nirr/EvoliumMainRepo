package fr.palmus.plugin.listeners;

import fr.palmus.plugin.EvoPlugin;
import fr.palmus.plugin.period.PeriodCaster;
import fr.palmus.plugin.period.PeriodStorage;
import fr.palmus.plugin.player.PlayerPeriod;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class CraftManager implements Listener {

    EvoPlugin main = EvoPlugin.getInstance();

    PeriodStorage storage = new PeriodStorage();

    @EventHandler
    public void onCraft(CraftItemEvent e){
        Player pl = (Player) e.getWhoClicked();
        PlayerPeriod customPlayer = main.getCustomPlayer().get(pl);

        if(e.getRecipe().getResult().getType() != Material.STRING){
            for(int i = 1; i < 9; i++){
                if(e.getClickedInventory().getItem(i) != null) {
                    if (e.getClickedInventory().getItem(i).hasItemMeta()) {
                        if (e.getClickedInventory().getItem(i).getItemMeta().getDisplayName().equalsIgnoreCase("§aFIBRE")) {
                            e.setCancelled(true);
                            return;
                        }
                    }
                }
            }
        }

        if(e.getRecipe().getResult().getType() == Material.STRING){
            for(int i = 1; i < 9; i++){
                if(e.getClickedInventory().getItem(i) != null) {
                    if (!e.getClickedInventory().getItem(i).hasItemMeta()) {
                        e.setCancelled(true);
                        return;
                    }
                }
            }
        }
        if(storage.getCraftMap(customPlayer.getStorageKey()).containsKey(e.getRecipe().getResult().getType())){
            int[] amount = new int[10];
            Material type = e.getRecipe().getResult().getType();
            int minAmount = 64;
            int slotmin = 1;
            if(e.getClick().isShiftClick()){
                if(e.getSlot() == 0){
                    for(int i = 1; i < 9; i++){
                        if(e.getClickedInventory().getItem(i) != null) {
                            amount[i] = e.getClickedInventory().getItem(i).getAmount();
                        }
                    }
                    for(int i = 1; i < 9; i++){

                        if(amount[i] != 0){
                            if(amount[i] < minAmount) {
                                minAmount = amount[i];
                                slotmin = i;
                            }
                        }
                    }
                    pl.playSound(pl.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    int exp = main.getComponents().prehistoireCraft.get(e.getRecipe().getResult().getType()) * minAmount;
                    main.getCustomPlayer().get(pl).addExp(exp);
                    return;
                }
            }
            main.getCustomPlayer().get(pl).addExp(storage.getCraftMap(customPlayer.getStorageKey()).get(e.getRecipe().getResult().getType()));
            pl.playSound(pl.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        }

    }
}
