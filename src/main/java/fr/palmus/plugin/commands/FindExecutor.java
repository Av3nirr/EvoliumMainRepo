package fr.palmus.plugin.commands;

import fr.palmus.plugin.EvoPlugin;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class FindExecutor implements CommandExecutor {
    EvoPlugin main = EvoPlugin.getInstance();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        Player p = (Player) commandSender;

        if (!(p instanceof Player)){
            return false;
        }

        if (!p.hasPermission("evoplugin.find")){
            p.sendMessage(main.getComponents().getPrefix("error") + "Vous n'avez pas la permission !");
            return false;
        }
        if(args.length != 1) {
            p.sendMessage(main.getComponents().getPrefix("error") + "usage: /b <player>");
            return false;
        }
        Player target = (Player) Bukkit.getPlayer(args[0]);
        p.sendMessage("§aInformations à propos de " + args[0]);
        p.sendMessage("§fPseudo: §e" + target.getName());
        p.sendMessage("§fArgent: §e" + main.econ.getPlayerEcon(target).getMoney() + "€");
        p.sendMessage("§fPériode actuelle: §e" + main.getCustomPlayer().get(target).getPeriod());
        p.sendMessage("§fExp: §e" + main.getCustomPlayer().get(target).getExp());
        p.sendMessage("§fUUID: §e" + target.getUniqueId());
        if(!Bukkit.getOnlinePlayers().contains(target)) {
            p.sendMessage("§fStatus: §cDéconnécté");
        }else{
            p.sendMessage("§fStatus: §aConnécté");
        }

        return false;
    }
}
