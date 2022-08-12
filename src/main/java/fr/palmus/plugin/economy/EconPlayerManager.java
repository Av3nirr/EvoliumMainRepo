package fr.palmus.plugin.economy;

import org.bukkit.entity.Player;

public class EconPlayerManager {

    Player player;

    Economy econ;

    int money;

    int bank;

    public EconPlayerManager(Player pl, Economy economy) {
        this.player = pl;
        this.econ = economy;
        this.bank = econ.main.cfg.getInt(player.getDisplayName() + ".bank");;
        this.money = econ.main.cfg.getInt(player.getDisplayName() + ".money");;
    }

    public void addMoney(int money){
        this.money += money;
    }

    public void substractMoney(int money){
        this.money -= money;
    }

    public void setMoney(int money){
        this.money = money;
    }

    public void addBank(int money){
        this.money += money;
    }

    public void substractBank(int money){
        this.money -= money;
    }

    public void setBank(int bank) {
        this.bank = bank;
    }

    public void saveMoney(){
        econ.main.cfg.set(player.getDisplayName() + ".money", money);
    }

    public Player getPlayer() {
        return player;
    }

    public int getMoney() {
        return money;
    }

    public int getBank() {
        return bank;
    }
}
