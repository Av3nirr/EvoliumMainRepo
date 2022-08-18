package fr.palmus.plugin.listeners;

import fr.palmus.plugin.EvoPlugin;
import fr.palmus.plugin.listeners.custom.PlayerExpChangeEvent;
import fr.palmus.plugin.listeners.custom.PlayerPeriodChangeEvent;
import fr.palmus.plugin.utils.fastboard.FastBoard;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PeriodManager implements Listener {

    EvoPlugin main = EvoPlugin.getInstance();

    @EventHandler
    public void onPeriodChange(PlayerPeriodChangeEvent e){
        for (FastBoard board : main.getComponents().boards.values()) {
            main.getComponents().updateBoard(board, e.getPlayer());
        }
    }

    @EventHandler
    public void onExpChange(PlayerExpChangeEvent e){
        for (FastBoard board : main.getComponents().boards.values()) {
            main.getComponents().updateBoard(board, e.getPlayer());
        }
    }
}
