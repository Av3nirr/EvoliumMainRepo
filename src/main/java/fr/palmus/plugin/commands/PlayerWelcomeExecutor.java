package fr.palmus.plugin.commands;

import fr.palmus.plugin.EvoPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Random;

public class PlayerWelcomeExecutor implements CommandExecutor {
    EvoPlugin main = EvoPlugin.getInstance();
    int cooldownTime = 30;
    Map<String, Long> cooldowns;
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        Player p = (Player) commandSender;
        if (!(p instanceof Player)){
            System.out.println("Erreur, vous n'êtes pas un joueur !");
            return false;
        }
        if(args.length != 1) {
            p.sendMessage(main.getComponents().getPrefix("error") + "usage: /b <player>");
            return false;
        }
        if(cooldowns.get(p.getName()) - System.currentTimeMillis() * 1000 < cooldownTime) {
            p.sendMessage(main.getComponents().getPrefix("error") + "Vous devez attendre avant de refaire la commande");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (!main.getComponents().NewPlayers.contains(target)) {
            p.sendMessage(main.getComponents().getPrefix("error") + "Le joueur n'existe pas, ou n'est pas nouveau !");
            return false;
        }
        //envoie du message au joueur
        int money = new Random().nextInt(500, 1000);
        //ajout de l'argent !
        main.econ.getPlayerEcon(p).addMoney(money);

        p.sendMessage("§aTu as bien reçu "+ money +"€ car tu as souhaité la bienvenue au joueur: §e" + target.getName());

        //Broadcast du message de bienvenue
        Random r = new Random();
        for (Player bcPLayers : p.getServer().getOnlinePlayers()) {
            bcPLayers.sendMessage(main.getComponents().welcomeList[r.nextInt(main.getComponents().welcomeList.length)].replace("%player%", p.getName()).replace("%target%", target.getName()));
        }
        return false;
    }
}
