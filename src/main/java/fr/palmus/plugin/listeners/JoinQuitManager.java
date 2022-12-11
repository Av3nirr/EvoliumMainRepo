package fr.palmus.plugin.listeners;

import fr.palmus.plugin.EvoPlugin;
import fr.palmus.plugin.utils.fastboard.FastBoard;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.sql.SQLException;

public class JoinQuitManager implements Listener {

    EvoPlugin main = EvoPlugin.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) throws IOException, SQLException {
        Player pl = e.getPlayer();
        System.out.println("[EvoPlugin] Scoreboard linked !");
        main.getCustomPlayer().initPlayer(pl);
        if(pl.isOp()){
            pl.setGameMode(GameMode.CREATIVE);
        }
        main.econ.initPlayerEcon(pl);
        FastBoard board = new FastBoard(pl);

        board.updateTitle(ChatColor.RED + "Evolium");

        main.getComponents().boards.put(pl.getUniqueId(), board);
        for (FastBoard boarde : main.getComponents().boards.values()) {
            main.getComponents().updateBoard(pl);
        }
        if (pl.hasPlayedBefore()){
            main.getComponents().NewPlayers.add(pl);
            //attendre 60 secondes ( 1200 ticks )
            new BukkitRunnable() {
                @Override
                public void run() {
                    main.getComponents().NewPlayers.remove(pl);
                    cancel();
                }
            }.runTaskLater(main, 1200L);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) throws IOException, SQLException {
        Player pl = e.getPlayer();
        FastBoard board = main.getComponents().boards.remove(e.getPlayer().getUniqueId());

        if (board != null) {
            board.delete();
        }
        main.getCustomPlayer().get(pl).saveExp();
        main.econ.getPlayerEcon(pl).saveMoney();
        main.cfg.save(main.file);
        main.getCustomPlayer().saveData(pl, main.getDatabaseManager().getDatabase().getConnection());
    }
}
