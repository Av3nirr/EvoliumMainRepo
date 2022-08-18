package fr.palmus.plugin.listeners;

import fr.palmus.plugin.EvoPlugin;
import fr.palmus.plugin.listeners.custom.PlayerExpChangeEvent;
import fr.palmus.plugin.listeners.custom.PlayerMoneyChangeEvent;
import fr.palmus.plugin.listeners.custom.PlayerPeriodChangeEvent;
import fr.palmus.plugin.utils.fastboard.FastBoard;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.LuckPermsEvent;
import net.luckperms.api.event.log.LogPublishEvent;
import net.luckperms.api.event.node.NodeAddEvent;
import net.luckperms.api.event.node.NodeRemoveEvent;
import net.luckperms.api.event.user.UserLoadEvent;
import net.luckperms.api.event.user.track.UserPromoteEvent;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ScoreboardUpdater implements Listener {

    EvoPlugin main;
    LuckPerms luckPerms;

    public ScoreboardUpdater(EvoPlugin plugin, LuckPerms luckPerms) {
        this.main = plugin;
        this.luckPerms = luckPerms;
        register();
    }

    public void register() {
        EventBus eventBus = this.luckPerms.getEventBus();
        eventBus.subscribe(this.main, NodeAddEvent.class, this::onNodeAdd);
        eventBus.subscribe(this.main, NodeRemoveEvent.class, this::onNodeRemove);
    }

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

    @EventHandler
    public void onMoneyChange(PlayerMoneyChangeEvent e){
        for (FastBoard board : main.getComponents().boards.values()) {
            main.getComponents().updateBoard(board, e.getPlayer());
        }
    }

    private void onNodeAdd(NodeAddEvent e) {
        if (!e.isUser()) {
            return;
        }
        User target = (User) e.getTarget();
        for (FastBoard board : main.getComponents().boards.values()) {
            main.getComponents().updateBoard(board, Bukkit.getPlayer(target.getUniqueId()));
        }
    }

    private void onNodeRemove(NodeRemoveEvent e) {
        if (!e.isUser()) {
            return;
        }
        User target = (User) e.getTarget();
        for (FastBoard board : main.getComponents().boards.values()) {
            main.getComponents().updateBoard(board, Bukkit.getPlayer(target.getUniqueId()));
        }
    }
}
