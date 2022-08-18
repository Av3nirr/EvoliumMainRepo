package fr.palmus.plugin.commands;

import fr.palmus.plugin.EvoPlugin;
import fr.palmus.plugin.components.EvoComponent;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RTPExecutor implements CommandExecutor {

    EvoPlugin main = EvoPlugin.getInstance();

    private static final int MIN = 500;
    private static final int MAX = 5000;

    private static List<String> NOT_STANDABLE_BLOCK_TYPES = new ArrayList<>(List.of("WATER", "LAVA"));


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(commandSender instanceof Player player){
            Location locPlayer = player.getLocation();
            World world = locPlayer.getWorld();

            player.sendMessage(main.getComponents().getPrefix("") + "Téléportation en cours...");

            //si j'atteri dans l'eau ou lave je restart
            //while(world.getBlockAt(getLocUnder(loc)).getType().toString().equals("WATER") || world.getBlockAt(getLocUnder(loc)).getType().toString().equals("LAVA")){
            //    loc = getTeleportLoc(world);
            //}


            Location loc = getTeleportLoc(world);
            //load chunk
            world.getChunkAt(world.getBlockAt(loc)).load();
            player.teleport(loc);
            return true;
        }

        return false;
    }

    private Location getTeleportLoc(World world){

        double x = EvoComponent.getRandomNumber(MIN,MAX);
        double z = EvoComponent.getRandomNumber(MIN,MAX);

        Location loc = new Location(world,x,0,z);
        Block block = world.getHighestBlockAt(loc);
        double y = block.getY() + 1.5;

        return new Location(world, x,y,z);


    }

    private Location getLocUnder(Location loc){
        return new Location(loc.getWorld(), loc.getX(),loc.getY()-1,loc.getZ());
    }
}