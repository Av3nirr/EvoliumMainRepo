package fr.palmus.plugin.commands;

import fr.palmus.plugin.utils.Utils;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RTPExecutor implements CommandExecutor {

    private static final int MIN = 500;
    private static final int MAX = 5000;

    private static List<String> NOT_STANDABLE_BLOCK_TYPES = new ArrayList<>(List.of("WATER", "LAVA"));


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(commandSender instanceof Player player){
            Location locPlayer = player.getLocation();
            World world = locPlayer.getWorld();

            player.sendMessage("Téléportation en cours...");
            //si j'atteri dans l'eau ou lave je restart
            Location loc = getTeleportLoc(world);
            while(world.getBlockAt(getLocUnder(loc)).getType().toString().equals("WATER") || world.getBlockAt(getLocUnder(loc)).getType().toString().equals("LAVA")){
                loc = getTeleportLoc(world);
            }

            //load chunk
            world.getChunkAt(world.getBlockAt(loc)).load();
            player.teleport(loc);

        }

        return false;
    }

    private Location getTeleportLoc(World world){

        double x = Utils.getRandomNumber(MIN,MAX) + 0.5;
        double y = 45;
        double z = Utils.getRandomNumber(MIN,MAX)+ 0.5;
        Location footLoc = new Location(world, x,y,z);
        Location headLoc = new Location(world, x,y+1,z);

        //je y++ jusqua ce que footLoc et headloc soit de l'air -> pas de tp dans les murs
        while(world.getBlockAt(footLoc).getType().toString().compareTo("AIR") != 0 && world.getBlockAt(headLoc).getType().toString().compareTo("AIR") != 0){
            y++;
            footLoc = new Location(world, x,y,z);
            headLoc = new Location(world, x,y+1,z);
        }
        return new Location(world,x,y+1,z);
    }

    private Location getLocUnder(Location loc){
        return new Location(loc.getWorld(), loc.getX(),loc.getY()-1,loc.getZ());
    }
}
