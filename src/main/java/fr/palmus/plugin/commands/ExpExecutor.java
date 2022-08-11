package fr.palmus.plugin.commands;


import fr.palmus.plugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class ExpExecutor implements CommandExecutor {

    Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){
            return false;
        }

        Player pl = (Player) sender;

        if(!pl.isOp()){
            return false;
        }

        if(args.length == 0){
            pl.sendMessage(main.getComponents().getPrefix("error") + "usage: /exp add|set|downgrade|upgrade|infos");
            ;return false;
        }

        if(args[0].equalsIgnoreCase("add")){
            if(args.length == 1 || args.length == 2){
                pl.sendMessage(main.getComponents().getPrefix("error") + "usage: /exp add joueur exp");
                return false;
            }

            if(Bukkit.getPlayer(args[1]) == null){
                pl.sendMessage(main.getComponents().getPrefix("error") + "Joueur inconnu :/");
                return false;
            }

            int exp = Integer.parseInt(args[2]);

            main.plmList.get(Bukkit.getPlayer(args[1])).addExp(exp);
            pl.sendMessage(main.getComponents().getPrefix("") + exp + "exp §5ajouté avec succès à §d" + Bukkit.getPlayer(args[1]).getDisplayName());
        }

        if(args[0].equalsIgnoreCase("set")){
            if(args.length == 1 || args.length == 2){
                pl.sendMessage(main.getComponents().getPrefix("error") + "usage: /exp set joueur exp");
                return false;
            }

            if(Bukkit.getPlayer(args[1]) == null){
                pl.sendMessage(main.getComponents().getPrefix("error") + "Joueur inconnu :/");
                return false;
            }

            int exp = Integer.parseInt(args[2]);

            try {
                main.plmList.get(Bukkit.getPlayer(args[1])).setExp(exp);
            } catch (IOException e) {
                e.printStackTrace();
            }
            pl.sendMessage(main.getComponents().getPrefix("") + "§5EXP de §d" + Bukkit.getPlayer(args[1]).getDisplayName() + "§5 mis à jour avec succès");
        }

        if(args[0].equalsIgnoreCase("downgrade")){
            if(args.length == 1){
                pl.sendMessage(main.getComponents().getPrefix("error") + "usage: /exp downgrade joueur");
                return false;
            }

            if(Bukkit.getPlayer(args[1]) == null){
                pl.sendMessage(main.getComponents().getPrefix("error") + "Joueur inconnu :/");
                return false;
            }

            try {
                main.plmList.get(Bukkit.getPlayer(args[1])).periodDowngrade();
            } catch (IOException e) {
                e.printStackTrace();
            }
            pl.sendMessage(main.getComponents().getPrefix("") + "" + Bukkit.getPlayer(args[1]).getDisplayName() + "§5 est descendu à la période §d" + main.plmList.get(Bukkit.getPlayer(args[1])).getEntirePeriodStyle());
        }

        if(args[0].equalsIgnoreCase("upgrade")){
            if(args.length == 1){
                pl.sendMessage(main.getComponents().getPrefix("error") + "usage: /exp upgrade joueur");
                return false;
            }

            if(Bukkit.getPlayer(args[1]) == null){
                pl.sendMessage(main.getComponents().getPrefix("error") + "Joueur inconnu :/");
                return false;
            }

            try {
                main.plmList.get(Bukkit.getPlayer(args[1])).periodUpgrade();
            } catch (IOException e) {
                e.printStackTrace();
            }
            pl.sendMessage(main.getComponents().getPrefix("") + "" + Bukkit.getPlayer(args[1]).getDisplayName() + "§5 est monter à la période §d" + main.plmList.get(Bukkit.getPlayer(args[1])).getEntirePeriodStyle());
        }

        if(args[0].equalsIgnoreCase("info")){
            if(args.length == 1){
                pl.sendMessage(main.getComponents().getPrefix("") + "§5Vous avez actuellement §d" + main.plmList.get(pl).getExp() + "EXP §5et vous êtes à la période §d" +main.plmList.get(pl).getEntirePeriodStyle());
                return false;
            }

            if(Bukkit.getPlayer(args[1]) == null){
                pl.sendMessage(main.getComponents().getPrefix("error") + "Joueur inconnu :/");
                return false;
            }

            pl.sendMessage(main.getComponents().getPrefix("") + "" + Bukkit.getPlayer(args[1]).getDisplayName() + "§5 à actuellement §d" + main.plmList.get(Bukkit.getPlayer(args[1])).getExp() + "EXP §5et est à la période §d" +main.plmList.get(Bukkit.getPlayer(args[1])).getEntirePeriodStyle());
        }
        return false;
    }
}
