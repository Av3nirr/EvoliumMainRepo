package fr.palmus.plugin.listeners;

import fr.palmus.plugin.EvoPlugin;
import fr.palmus.plugin.components.PlayerManager;
import fr.palmus.plugin.utils.CustomItem;
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

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Player pl = e.getPlayer();
        PlayerManager plm = main.plmList.get(pl);
        Material type = e.getBlock().getType();
        if(!(pl.getGameMode() == GameMode.SURVIVAL)){
            return;
        }

        if(plm.getPeriod() == 0){

            if(main.getComponents().prehistoire.containsKey(type)){
                if(main.api.blockLookup(e.getBlock(),
                        (int)(System.currentTimeMillis() / 1000L)).size() > 0) return;
                plm.addExp(main.getComponents().prehistoire.get(type));
            }
        }

        if(type == Material.LEGACY_LONG_GRASS){
            if(e.isCancelled()){
                return;
            }
            int rdm = new Random().nextInt(100);
            if(rdm <= 5){
                pl.getWorld().dropItemNaturally(e.getBlock().getLocation(), CustomItem.fiber);
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
