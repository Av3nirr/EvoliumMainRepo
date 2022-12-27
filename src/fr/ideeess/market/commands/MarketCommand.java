package fr.ideeess.market.commands;

import fr.ideeess.market.MarketEvolium;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

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

                        if (!args[2].equalsIgnoreCase("")){

                            if (player.hasPermission("market.sellitem")) {

                                ItemStack it = player.getInventory().getItemInMainHand();
                                int itNumber = Integer.parseInt(args[1]);
                                int itPrice = Integer.parseInt(args[2]);
                                if (it.getType() != Material.AIR) {
                                    String name = "";
                                    if (it.getItemMeta().hasDisplayName()){
                                        name = it.getItemMeta().getDisplayName();
                                    }else {
                                        name = it.getType().name();
                                    }
                                    if (player.getInventory().contains(it,itNumber)) {

                                        int seconds = Math.toIntExact(System.currentTimeMillis() / 1000);

                                        main.getConfig().set(seconds + ".owner", player.getDisplayName());
                                        main.getConfig().set(seconds + ".item.name", name);
                                        main.getConfig().set(seconds + ".item.lore", it.getItemMeta().getLore());
                                        main.getConfig().set(seconds + ".item.type", it.getType());
                                        main.getConfig().set(seconds + ".price", itPrice);
                                        main.getConfig().set(seconds + ".quantity", itNumber);

                                        player.sendMessage(ChatColor.GREEN + "Vous vendez à présent " + itNumber + " " + it.getItemMeta().getDisplayName() + " à " + itPrice + " EvoCoins");
                                        main.saveConfig();
                                        return false;
                                    }
                                    player.sendMessage(ChatColor.RED + "Vous n'avez pas " + itNumber + " " + Objects.requireNonNull(it.getItemMeta()).getDisplayName());
                                    return false;
                                }
                                player.sendMessage(ChatColor.RED + "Vous ne pouvez pas vendre de l'air");
                                return false;
                            }

                            return false;
                        }

                        player.sendMessage(ChatColor.RED + "Vous devez indiquer le prix");

                        return false;
                    }

                    player.sendMessage(ChatColor.RED + "Vous devez indiquer la quantité");

                    return false;
                }

                player.sendMessage(ChatColor.RED + "Argument invalide");

                return false;
            }

            if (player.hasPermission("market.use")){

                ItemStack it = new ItemStack(Material.LEATHER_HELMET);
                ItemMeta itM = it.getItemMeta();
                itM.setDisplayName(ChatColor.RED + "752ddsqsd");
                it.setItemMeta(itM);

                player.getInventory().addItem(it);

                return true;
            }

            player.sendMessage(ChatColor.RED + "Vous n'avez pas la permission pour ouvrir le market");

            return false;
        }
        return false;
    }
}
