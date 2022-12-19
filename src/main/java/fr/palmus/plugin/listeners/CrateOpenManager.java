package fr.palmus.plugin.listeners;

import fr.palmus.plugin.EvoPlugin;
import fr.palmus.plugin.components.Crate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;


public class CrateOpenManager implements Listener {
    EvoPlugin main = EvoPlugin.getInstance();
    @EventHandler
    public void onClick(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Location loc = e.getClickedBlock().getLocation();
            World world = e.getClickedBlock().getWorld();
            for (Crate c : main.getComponents().crates){
                Location crateLoc = e.getClickedBlock().getWorld().getBlockAt(c.getLoc()).getLocation();
                if (crateLoc.equals(loc)){
                    if (c.getWorld().equals(world)){
                        p.sendMessage("Crate trouvée, son nom est: " + c.getName());
                        break;
                    }
                }
            }


        }
    }
}
