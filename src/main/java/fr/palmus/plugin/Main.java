package fr.palmus.plugin;

import com.sk89q.worldedit.math.BlockVector2;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import fr.palmus.plugin.commands.ExpExecutor;
import fr.palmus.plugin.commands.FarmzoneExecutor;
import fr.palmus.plugin.components.EvoComponent;
import fr.palmus.plugin.components.MobManager;
import fr.palmus.plugin.components.PlayerManager;
import fr.palmus.plugin.listeners.BlockManager;
import fr.palmus.plugin.listeners.CraftManager;
import fr.palmus.plugin.listeners.DamageManager;
import fr.palmus.plugin.listeners.JoinQuitManager;
import fr.palmus.plugin.utils.CustomItem;
import net.coreprotect.CoreProtectAPI;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends JavaPlugin {

    private static Main INSTANCE;

    private EvoComponent storage;

    public Logger log;

    public ArrayList<Player> playerExp = new ArrayList<>();

    public HashMap<Player, PlayerManager> plmList = new HashMap<>();

    public CoreProtectAPI api;

    public LuckPerms LPapi;

    public boolean FarmlandsModules;

    public BlockVector2 preRegion1;
    public BlockVector2 preRegion2;
    RegionContainer container;

    private ScheduledExecutorService executorMonoThread;
    private ScheduledExecutorService scheduledExecutorService;

    public File file = new File("plugins/EvoPlugin", "period.yml");
    public FileConfiguration cfg;

    Plugin plugin = Bukkit.getPluginManager().getPlugin("Craftconomy3");
    //public static Common craftconomy;

    RegisteredServiceProvider<LuckPerms> provider;
    int entities;



    @Override
    public void onEnable() {

        log = getLogger();
        saveDefaultConfig();
        INSTANCE = this;
        storage = new EvoComponent();
        setCommands();
        setListeners();
        log.log(Level.INFO,ChatColor.GREEN + "Initialised !");

        if (plugin != null) {
            //craftconomy = (Common) ((Loader) plugin).getCommon();
            System.out.println("[EvoPlugin] Craftconomy Hooked !");
        }

        cfg = YamlConfiguration.loadConfiguration(file);
        System.out.println();
        log.log(Level.INFO,ChatColor.GREEN + "Configuration files loaded !");

        api = this.getComponents().getCoreProtect();
        if (api != null) {
            log.log(Level.INFO,ChatColor.GREEN + "CoreAPI Hooked !");
        }

        Plugin WE = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        if (WE != null) {
            log.log(Level.INFO,ChatColor.GREEN + "WorldEdit Hooked !");
        }

        Plugin WG = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        if (WG != null) {
            container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            log.log(Level.INFO,ChatColor.GREEN + "WorldGuard Hooked !");
        }

        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (cfg.get(pl.getDisplayName() + ".period") == null) {
                cfg.set(pl.getDisplayName() + ".period", 0);
                cfg.set(pl.getDisplayName() + ".exp", 0);
                cfg.set(pl.getDisplayName() + ".doubler", 0);
                cfg.set(pl.getDisplayName() + ".rank", 1);
                try {
                    cfg.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            plmList.put(pl, new PlayerManager(pl, cfg.getInt(pl.getDisplayName() + ".exp")));
        }

        provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            LPapi = provider.getProvider();
            log.log(Level.INFO,ChatColor.GREEN + "LuckPerms Hooked !");
        }
        Iterator<Recipe> it = getServer().recipeIterator();
        Recipe recipe;
        while (it.hasNext()) {
            recipe = it.next();
            if (recipe != null && recipe.getResult().getType() == null) {
                it.remove();
            }
        }

        NamespacedKey key = new NamespacedKey(this, "emerald_sword");

        ShapedRecipe recipie = new ShapedRecipe(key, new ItemStack(Material.STRING));

        recipie.shape("***");

        recipie.setIngredient('*', CustomItem.fiber.getData());

        Bukkit.addRecipe(recipie);

        try{
            RegionManager regions = container.get((World) Bukkit.getWorld(getConfig().getString("farmlands.world")));
            preRegion1 = regions.getRegion(getConfig().getString("farmlands.name.prehistoire")).getPoints().get(0);
            preRegion2 = regions.getRegion(getConfig().getString("farmlands.name.prehistoire")).getPoints().get(1);
            double xMiddle = (double) ((preRegion1.getBlockX() + preRegion2.getBlockX()) / 2);
            double zMiddle = (double) (preRegion1.getBlockZ() + preRegion2.getBlockZ()) / 2;
            Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                @Override
                public void run() {
                    entities = 0;
                    for(Entity ent : Bukkit.getWorld(getConfig().getString("farmlands.world")).getEntities()){
                        if(ent.getType() == EntityType.ZOMBIE || ent.getType() == EntityType.SKELETON){
                            entities = entities + 1;
                        }
                    }
                    if(entities >= 15){

                    }else{
                        System.out.println(entities);
                        double xPos = ThreadLocalRandom.current().nextDouble(xMiddle, xMiddle + 1);
                        double zPos = ThreadLocalRandom.current().nextDouble(zMiddle, zMiddle + 1);

                        MobManager.spawnMob(xPos, zPos, Bukkit.getWorld(getConfig().getString("farmlands.world")));
                    }
                }
            }, 0L, 300L);
        }catch (NullPointerException e){
            FarmlandsModules = false;
            log.warning(ChatColor.YELLOW + "Disabling Farmlands module, Farmlands undetected.");
        }
    }

    @Override
    public void onDisable() {
        saveDefaultConfig();
        for(Player pl : playerExp){
            plmList.get(pl).saveExp();
        }
        try {
            cfg.save(file);
        } catch (IOException | NullPointerException e) {
            log.severe(ChatColor.RED + "FAILED TO SAVE CONFIG, ALL THE EXP WON THIS SESSION SHOULD BE LOST, FATAL.");
            getPluginLoader().disablePlugin(this);
            return;
        }
        log.log(Level.INFO, ChatColor.GREEN + "Disabling EvoPlugin tranquillou bidou...");
    }

    public void setCommands(){
        getCommand("exp").setExecutor(new ExpExecutor());
        getCommand("farmzone").setExecutor(new FarmzoneExecutor());
        log.log(Level.INFO,ChatColor.GREEN + "Commands modules Enabled");
    }

    public void setListeners(){
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new JoinQuitManager(), this);
        pm.registerEvents(new BlockManager(), this);
        pm.registerEvents(new CraftManager(), this);
        pm.registerEvents(new DamageManager(), this);
        log.log(Level.INFO,ChatColor.GREEN + "Listeners modules Enabled");
    }

    public EvoComponent getComponents(){
        return storage;
    }

    public static Main getInstance(){
        return INSTANCE;
    }

}
