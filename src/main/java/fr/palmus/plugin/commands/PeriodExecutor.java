package fr.palmus.plugin.commands;

import fr.palmus.plugin.EvoPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class PeriodExecutor implements CommandExecutor {

    EvoPlugin main = EvoPlugin.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){
            return false;
        }

        Player pl = (Player) sender;

        Inventory inv = Bukkit.createInventory(pl, 45, "§eInformations");

        main.getComponents().initInfoInventory(inv, pl);

        pl.openInventory(inv);

        return false;
    }
}
