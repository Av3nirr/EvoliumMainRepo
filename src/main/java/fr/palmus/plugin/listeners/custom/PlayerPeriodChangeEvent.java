package fr.palmus.plugin.listeners.custom;

import fr.palmus.plugin.EvoPlugin;
import fr.palmus.plugin.player.PlayerPeriod;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerPeriodChangeEvent extends Event implements Cancellable {

    private Player player;
    private PlayerPeriod plm;
    private PlayerPeriod.PeriodAction action;
    private int period;
    private int limiter;
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;

    public PlayerPeriodChangeEvent(Player pl, int period, int limiter, PlayerPeriod.PeriodAction action, EvoPlugin main){
        this.player = pl;
        this.plm = main.getCustomPlayer().get(pl);
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

    public PlayerPeriod getPlayerManager() {
        return plm;
    }

    public int getPeriod() {
        return period;
    }

    public int getLimiter() {
        return limiter;
    }

    public PlayerPeriod.PeriodAction getAction() {
        return action;
    }

    public static HandlerList getHandlersList() {
        return HANDLERS_LIST;
    }


}
