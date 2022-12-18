package fr.palmus.plugin.listeners;

import fr.palmus.plugin.EvoPlugin;
import fr.palmus.plugin.components.Crate;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;


public class CrateOpenManager implements Listener {
    EvoPlugin main = EvoPlugin.getInstance();
    public void onClick(PlayerInteractEvent e){
        Player p = e.getPlayer();

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Location loc = e.getClickedBlock().getLocation();
            World world = e.getClickedBlock().getWorld();
            for (Crate c : main.getComponents().crates){
                if (c.getLoc().equals(loc)){
                    if (c.getWorld().equals(world)){
                        p.sendMessage("Crate trouvée, son nom est: " + c.getName());
                        break;
                    }
                }
            }


        }
    }
}
