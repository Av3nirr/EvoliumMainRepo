package fr.palmus.plugin.listeners;

import fr.palmus.plugin.EvoPlugin;
import fr.palmus.plugin.enumeration.Period;
import fr.palmus.plugin.period.PeriodStorage;
import fr.palmus.plugin.period.key.StorageKey;
import fr.palmus.plugin.player.PlayerPeriod;
import fr.palmus.plugin.item.CustomItem;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Random;

public class BlockManager implements Listener {

    EvoPlugin main = EvoPlugin.getInstance();

    PeriodStorage storage = new PeriodStorage();

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Player pl = e.getPlayer();
        PlayerPeriod customPlayer = main.getCustomPlayer().get(pl);
        Material type = e.getBlock().getType();
        StorageKey key = customPlayer.getStorageKey();
        if(!(pl.getGameMode() == GameMode.SURVIVAL)){
            return;
        }

        if(main.getCustomPlayer().get(pl).getPeriod() == Period.PREHISTOIRE){

            if(storage.getBreakableMap(key).containsKey(type)){
                if(main.api.blockLookup(e.getBlock(),
                        (int)(System.currentTimeMillis() / 1000L)).size() > 0) return;
                customPlayer.addExp(storage.getBreakableMap(key).get(type));
            }
        }

        if(type == Material.TALL_GRASS || type == Material.GRASS){
            if(e.isCancelled()){
                return;
            }
            int rdm = new Random().nextInt(100);
            if(rdm <= 5){
                pl.getWorld().dropItemNaturally(e.getBlock().getLocation(), CustomItem.fiber);
                customPlayer.addExp(10);
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        Player pl = e.getPlayer();
        Block block = e.getBlock();
        main.api.logPlacement(pl.getName(), block.getLocation(), block.getType(), block.getData());

    }
}
