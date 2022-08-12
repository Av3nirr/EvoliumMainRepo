package fr.palmus.plugin.listeners;

import fr.palmus.plugin.EvoPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class DamageManager implements Listener {

    EvoPlugin main = EvoPlugin.getInstance();

    @EventHandler
    public void onDeath(EntityDeathEvent e){
        if(!e.getEntity().getLocation().getWorld().getName().equalsIgnoreCase("lobby") || !(e.getEntity().getKiller() instanceof Player)){
            return;
        }

        Player pl = e.getEntity().getKiller();
        Entity entity = e.getEntity();
        System.out.println("ok");
        if(e.getEntity() instanceof Zombie || e.getEntity().getType() == EntityType.SKELETON && main.FarmlandsModules){
            e.setDroppedExp(0);
            e.getDrops().clear();
            if(main.getComponents().prehistoireKill.containsKey(entity.getName())){
                if(main.plmList.get(pl).getPeriod() == 0){
                    main.plmList.get(pl).addExp(main.getComponents().prehistoireKill.get(entity.getName()));
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if(e.getEntity() instanceof Player || !(e.getEntity().getWorld() == Bukkit.getWorld("lobby"))){
            return;
        }

        Entity entity = e.getEntity();

        if(e.getEntity() instanceof Zombie || e.getEntity() instanceof Skeleton){
            if(e.getCause() == EntityDamageEvent.DamageCause.FIRE){
                e.setCancelled(true);
            }
        }
    }
}
