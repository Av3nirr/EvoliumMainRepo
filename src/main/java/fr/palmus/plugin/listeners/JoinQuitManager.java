package fr.palmus.plugin.listeners;

import fr.palmus.plugin.EvoPlugin;
import fr.palmus.plugin.components.PlayerManager;
import fr.palmus.plugin.economy.Economy;
import fr.palmus.plugin.utils.fastboard.FastBoard;
import net.md_5.bungee.api.ChatColor;
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
        main.econ.initPlayerEcon(pl);
        FastBoard board = new FastBoard(pl);

        board.updateTitle(ChatColor.RED + "Evolium");


        main.getComponents().boards.put(pl.getUniqueId(), board);
        for (FastBoard boarde : main.getComponents().boards.values()) {
            main.getComponents().updateBoard(boarde, pl);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        FastBoard board = main.getComponents().boards.remove(e.getPlayer().getUniqueId());

        if (board != null) {
            board.delete();
        }
    }
}
