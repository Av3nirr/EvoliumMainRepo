package fr.palmus.plugin.commands;

import fr.palmus.plugin.Main;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FarmzoneExecutor implements CommandExecutor {

    Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)){
            return false;
        }

        Player pl = (Player) sender;

        if(args.length > 0 && !pl.hasPermission("farmzone.set")){
            pl.sendMessage(main.getComponents().getPrefix("error") + "usage: /farmzone");
            return false;
        }

        if(!main.FarmlandsModules){
            sender.sendMessage(main.getComponents().getPrefix("error") + "Le module des farmlands est désactivé sur ce serveur");
        }

        if(args.length == 0){
            if(main.getConfig().get("farmzone." + main.plmList.get(pl).getPeriod() + ".loc") == null){
                pl.sendMessage(main.getComponents().getPrefix("error") + "La farmzone de cette période n'a pas été définie merci de prévenir un administrateur");
                return false;
            }
            pl.teleport((Location) main.getConfig().get("farmzone." + main.plmList.get(pl).getPeriod() + ".loc"));
            pl.sendTitle("§e"+ main.getComponents().getPeriod(main.plmList.get(pl).getPeriod()), "§6Farmzone", 10, 40, 10);
            return false;
        }

        if(args.length != 2){
            pl.sendMessage(main.getComponents().getPrefix("error") + "usage: /farmzone set <période>");
            return false;
        }

        main.getConfig().set("farmzone." + args[1] + ".loc", pl.getLocation());
        main.saveConfig();
        pl.sendMessage(main.getComponents().getPrefix("") + "Farmzone de là période " + main.getComponents().getPeriod(main.plmList.get(pl).getPeriod())+ " définie avec succès");

        return false;
    }
}
