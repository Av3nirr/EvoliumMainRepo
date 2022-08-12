package fr.palmus.plugin.listeners;

import fr.palmus.plugin.EvoPlugin;
import fr.palmus.plugin.components.PlayerManager;
import fr.palmus.plugin.economy.Economy;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;

public class JoinQuitManager implements Listener {

    EvoPlugin main = EvoPlugin.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) throws IOException {
        Player pl = e.getPlayer();
        System.out.println("[EvoPlugin] Scoreboard linked !");
        if(main.cfg.get(pl.getDisplayName() + ".period") == null){
            main.cfg.set(pl.getDisplayName() + ".period", 0);
            main.cfg.set(pl.getDisplayName() + ".exp", 0);
            main.cfg.set(pl.getDisplayName() + ".doubler", 0);
            main.cfg.set(pl.getDisplayName() + ".limiter", 0);
            main.cfg.set(pl.getDisplayName() + ".rank", 1);
            main.cfg.save(main.file);
        }
        main.plmList.put(pl, new PlayerManager(pl, main.cfg.getInt(pl.getDisplayName() + ".exp")));
        if(pl.isOp()){
            pl.setGameMode(GameMode.CREATIVE);
        }
        main.getComponents().createScoreboard(pl);
        main.econ.initPlayerEcon(pl);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player pl = e.getPlayer();
    }
}
