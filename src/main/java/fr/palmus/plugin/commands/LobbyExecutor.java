package fr.palmus.plugin.commands;

import fr.palmus.plugin.EvoPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class LobbyExecutor implements CommandExecutor {
    EvoPlugin inst;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;

        if(!(p instanceof Player)){
            return  false;
        }
        if (p.hasPermission("evolium.spawn.bypasscooldown")){
            p.sendMessage("§eTéléportation au spawn en cours !");
            p.teleport(inst.getComponents().getLobby());
            inst.getComponents().LobbyEffect(p);
            p.sendMessage("§aVous avez été téléporté au Spawn !");
            return false;
        }
        p.sendMessage("§eTéléportation au spawn dans 5sec !");
        new BukkitRunnable() {
            @Override
            public void run() {
                p.teleport(inst.getComponents().getLobby());
                inst.getComponents().LobbyEffect(p);
                p.sendMessage("§aVous avez été téléporté au Spawn !");
                cancel();
            }
        }.runTaskLater(inst, 100L);
        return false;
    }
}
