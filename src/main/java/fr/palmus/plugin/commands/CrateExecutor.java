package fr.palmus.plugin.commands;

import fr.palmus.plugin.EvoPlugin;
import fr.palmus.plugin.components.Crate;
import org.antlr.v4.runtime.misc.NotNull;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public class CrateExecutor implements CommandExecutor {
    EvoPlugin main = EvoPlugin.getInstance();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player p = (Player) sender;

        if (!(p instanceof Player)){
            return false;
        }
        if (args.length < 1){
            p.sendMessage(main.getComponents().getString("prefix.error") + "Vous devez définir un nom.");
            return false;
        }
        StringBuilder name = new StringBuilder("");
        for (String part : args) {
            if (!name.toString().equals("")) {
                name.append(" ");
            }
            name.append(part);
        }


        Location playerLoc = p.getLocation();
        Crate myCrate = new Crate(name.toString(), playerLoc, "No description yet !", p.getWorld());
        myCrate.setArmorStand();
        main.getComponents().crates.add(myCrate);
        p.getWorld().getBlockAt(playerLoc).setType(Material.CHEST);


        p.sendMessage(main.getComponents().getString("prefix.ok") + "La crate à été créé en " + playerLoc.getX() +", "+playerLoc.getY()+", "+playerLoc.getZ()+" !");
        return false;
    }
}
