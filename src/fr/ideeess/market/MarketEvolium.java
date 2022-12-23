package fr.ideeess.market;

import fr.ideeess.market.commands.MarketCommand;
import fr.palmus.plugin.EvoPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class MarketEvolium extends JavaPlugin {
    EvoPlugin inst;

    @Override
    public void onEnable() {

        this.getCommand("market").setExecutor(new MarketCommand(this));

        //Import EvoPlugin API
        if (Bukkit.getPluginManager().getPlugin("EvoPlugin") != null) {
            inst = EvoPlugin.getInstance();
        } else {
            getLogger().warning("Could not find EvoPlugin ! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        this.saveDefaultConfig();

        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public int getPlayerMoney(Player player){
        return inst.econ.getPlayerEcon(player).getMoney();
    }

    public void addPlayerMoney(Player player,int money){
        inst.econ.getPlayerEcon(player).addMoney(money);
    }

    public void deletePlayerMoney(Player player,int moneyDeleted){

        int restantMoney = getPlayerMoney(player) - moneyDeleted;

        inst.econ.getPlayerEcon(player).setMoney(restantMoney);
    }

    @Override
    public FileConfiguration getConfig() {
        return super.getConfig();
    }
}
