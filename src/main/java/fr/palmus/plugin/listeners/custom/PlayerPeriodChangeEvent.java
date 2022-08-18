package fr.palmus.plugin.listeners.custom;

import fr.palmus.plugin.EvoPlugin;
import fr.palmus.plugin.components.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerPeriodChangeEvent extends Event implements Cancellable {

    private Player player;
    private PlayerManager plm;
    private PlayerManager.PeriodAction action;
    private int period;
    private int limiter;
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;

    public PlayerPeriodChangeEvent(Player pl, int period, int limiter, PlayerManager.PeriodAction action){
        this.player = pl;
        this.plm = EvoPlugin.getInstance().plmList.get(pl);
        this.action = action;
        this.period = period;
        this.limiter = limiter;
        this.isCancelled = false;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerManager getPlayerManager() {
        return plm;
    }

    public int getPeriod() {
        return period;
    }

    public int getLimiter() {
        return limiter;
    }

    public PlayerManager.PeriodAction getAction() {
        return action;
    }

    public static HandlerList getHandlersList() {
        return HANDLERS_LIST;
    }


}
