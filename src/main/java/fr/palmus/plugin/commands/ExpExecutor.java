package fr.palmus.plugin.commands;


import fr.palmus.plugin.EvoPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class ExpExecutor implements CommandExecutor {

    EvoPlugin main = EvoPlugin.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){
            return false;
        }

        Player pl = (Player) sender;

        if(!pl.hasPermission("evoplugin.exp_mod")){
            pl.sendMessage(main.getComponents().getPrefix("") + main.getComponents().getString("commands.no_perm") + ", si vous souhaitez obtenir des informations à propos des périodes vous pouvez utiliser le §6/periode");
            return false;
        }

        if(args.length == 0){
            pl.sendMessage(main.getComponents().getPrefix("error") + main.getComponents().getString("commands.wrong_exp_usage"));
            ;return false;
        }

        if(args[0].equalsIgnoreCase("add")){
            if(args.length == 1 || args.length == 2){
                pl.sendMessage(main.getComponents().getPrefix("error") + main.getComponents().getString("commands.less_args").replace("{label}", args[0]) + "exp");
                return false;
            }

            if(Bukkit.getPlayer(args[1]) == null){
                pl.sendMessage(main.getComponents().getPrefix("error") + main.getComponents().getString("commands.unknow_player"));
                return false;
            }

            int exp = Integer.parseInt(args[2]);

            main.plmList.get(Bukkit.getPlayer(args[1])).addExp(exp);
            pl.sendMessage(main.getComponents().getPrefix("") + exp + "exp §5ajouté avec succès à §d" + Bukkit.getPlayer(args[1]).getDisplayName());
        }

        if(args[0].equalsIgnoreCase("set")){
            if(args.length == 1 || args.length == 2){
                pl.sendMessage(main.getComponents().getPrefix("error") + main.getComponents().getString("commands.less_args").replace("{label}", args[0]) + "exp");
                return false;
            }

            if(Bukkit.getPlayer(args[1]) == null){
                pl.sendMessage(main.getComponents().getPrefix("error") + main.getComponents().getString("commands.unknow_player"));
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
                pl.sendMessage(main.getComponents().getPrefix("error") + main.getComponents().getString("commands.less_args").replace("{label}", args[0]));
                return false;
            }

            if(Bukkit.getPlayer(args[1]) == null){
                pl.sendMessage(main.getComponents().getPrefix("error") + main.getComponents().getString("commands.unknow_player"));
                return false;
            }

            if(main.plmList.get(Bukkit.getPlayer(args[1])).getPeriod() == 0 && main.plmList.get(Bukkit.getPlayer(args[1])).getLimiter() == 1){
                pl.sendMessage(main.getComponents().getPrefix("error") + "§5Ce joueur est déjà §d" + main.plmList.get(Bukkit.getPlayer(args[1])).getEntirePeriodStyle());
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
                pl.sendMessage(main.getComponents().getPrefix("error") + main.getComponents().getString("commands.less_args").replace("{label}", args[0]));
                return false;
            }

            if(Bukkit.getPlayer(args[1]) == null){
                pl.sendMessage(main.getComponents().getPrefix("error") + main.getComponents().getString("commands.unknow_player"));
                return false;
            }

            if(main.plmList.get(Bukkit.getPlayer(args[1])).getPeriod() == 6 && main.plmList.get(Bukkit.getPlayer(args[1])).getLimiter() == 3){
                pl.sendMessage(main.getComponents().getPrefix("error") + "§5Ce joueur est déjà §d" + main.plmList.get(Bukkit.getPlayer(args[1])).getEntirePeriodStyle());
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
                pl.sendMessage(main.getComponents().getPrefix("error") + "");
                return false;
            }
            pl.sendMessage(main.getComponents().getPrefix("") + "" + Bukkit.getPlayer(args[1]).getDisplayName() + "§5 à actuellement §d" + main.plmList.get(Bukkit.getPlayer(args[1])).getStringExp(main.plmList.get(Bukkit.getPlayer(args[1])).getExp()) + " EXP §5et est à la période §d" +main.plmList.get(Bukkit.getPlayer(args[1])).getEntirePeriodStyle());
        }

        if(args[0].equalsIgnoreCase("reset")){
            if(args.length == 1){
                pl.sendMessage(main.getComponents().getPrefix("error") + main.getComponents().getString("commands.less_args").replace("{label}", args[0]));
                return false;
            }

            if(Bukkit.getPlayer(args[1]) == null){
                pl.sendMessage(main.getComponents().getPrefix("error") + main.getComponents().getString("commands.unknow_player"));
                return false;
            }

            try {
                main.plmList.get(Bukkit.getPlayer(args[1])).resetPeriod();
            } catch (IOException e) {
                pl.sendMessage(main.getComponents().getPrefix("error") + "Une erreur c'est produite veuillez reportez ça à un développeur");
                e.printStackTrace();
                return false;
            }
            pl.sendMessage(main.getComponents().getPrefix("") + "" + Bukkit.getPlayer(args[1]).getDisplayName() + "§5 c'est vu réinitialiser ses périodes et est maintenant à la période §d" +  main.getComponents().getPeriod(0) + " I");
        }
        return false;
    }
}
