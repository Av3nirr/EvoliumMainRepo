package fr.ideeess.market.commands;

import fr.ideeess.market.MarketEvolium;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MarketCommand implements CommandExecutor {
    MarketEvolium main;

    public MarketCommand(MarketEvolium main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player player){

            if(args.length != 0){

                if (args[0].equalsIgnoreCase("sell")){

                    if (!args[1].equalsIgnoreCase("")){



                        return false;
                    }

                    player.sendMessage(ChatColor.RED + "Vous devez indiquer la quantité");

                    return false;
                }

                player.sendMessage(ChatColor.RED + "Argument invalide");

                return false;
            }

            if (player.hasPermission("market.use")){

                return true;
            }

            player.sendMessage(ChatColor.RED + "Vous n'avez pas la permission pour ouvrir le market");

            return false;
        }
        return false;
    }
}
