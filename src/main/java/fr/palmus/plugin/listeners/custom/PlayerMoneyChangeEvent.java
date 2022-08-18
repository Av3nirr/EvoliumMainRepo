package fr.palmus.plugin.listeners.custom;

import fr.palmus.plugin.EvoPlugin;
import fr.palmus.plugin.components.PlayerManager;
import fr.palmus.plugin.economy.EconPlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerMoneyChangeEvent extends Event implements Cancellable {

    private Player player;
    private PlayerManager plm;
    private EconPlayerManager.TransferType action;
    private int money;
    private int bank;
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;

    public PlayerMoneyChangeEvent(Player pl, int money, int bank, EconPlayerManager.TransferType action){
        System.out.println("g");
        this.player = pl;
        this.plm = EvoPlugin.getInstance().plmList.get(pl);
        this.action = action;
        this.money = money;
        this.bank = bank;
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

    public int getMoney() {
        return money;
    }

    public int getBank() {
        return bank;
    }

    public EconPlayerManager.TransferType getAction() {
        return action;
    }

    public static HandlerList getHandlersList() {
        return HANDLERS_LIST;
    }


}
