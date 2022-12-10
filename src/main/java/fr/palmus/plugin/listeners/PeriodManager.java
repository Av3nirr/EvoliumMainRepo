package fr.palmus.plugin.listeners;

import fr.palmus.plugin.listeners.custom.PlayerPeriodChangeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PeriodManager implements Listener {
    @EventHandler
    public void onPeriodChange(PlayerPeriodChangeEvent e){
        Player p = e.getPlayer();
        //faire le système de récompenses
        //https://github.com/Palmuss/EvoliumRepo/wiki/

    }
}
