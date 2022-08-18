package fr.palmus.plugin.listeners.custom;

import fr.palmus.plugin.EvoPlugin;
import fr.palmus.plugin.components.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerExpChangeEvent extends Event implements Cancellable {

    private Player player;
    private PlayerManager plm;
    private PlayerManager.ExpAction action;
    private int exp;
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;

    public PlayerExpChangeEvent(Player pl, int exp,PlayerManager.ExpAction action){
        this.player = pl;
        this.plm = EvoPlugin.getInstance().plmList.get(pl);
        this.action = action;
        this.exp = exp;
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

    public int getExp() {
        return exp;
    }

    public PlayerManager.ExpAction getAction() {
        return action;
    }

    public static HandlerList getHandlersList() {
        return HANDLERS_LIST;
    }


}
