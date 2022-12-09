package fr.palmus.plugin.commands;

import fr.palmus.plugin.EvoPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class PlayerWelcomeExecutor implements CommandExecutor {
    EvoPlugin main = EvoPlugin.getInstance();
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        Player p = (Player) commandSender;
        if (!(p instanceof Player)){
            System.out.println("Erreur, vous n'êtes pas un joueur !");
        }
        if(args.length != 1) {
            p.sendMessage(main.getComponents().getPrefix("error") + "usage: /b <player>");
            return false;
        }else {
            Player target = p.getServer().getPlayer(args[1]);
            if (main.getInstance().NewPlayers.contains(target)) {
                //envoie du message au joueur
                p.sendMessage("§aTu as bien reçu XX€ car tu as souhaité la bienvenue au joueur: §e" + target.getName());
                //Broadcast du message de bienvenue
                Random r = new Random();
                for (Player bcPLayers : p.getServer().getOnlinePlayers()) {
                    bcPLayers.sendMessage(main.getInstance().welcomeList[r.nextInt(main.getInstance().welcomeList.length)].replace("%player%", p.getName()).replace("%target%", target.getName()));
                }
                //ajout de l'argent ! (faire le Multiplicateur)
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/eco add " + p.getName() + 500);
            } else {
                p.sendMessage("§cDommage... Il n'y a pas de joueurs nouveaux. Tu pourrait faire de la pub !");
            }
        }
        return false;
    }
}
