package fr.palmus.plugin;

import com.sk89q.worldguard.WorldGuard;
import fr.palmus.plugin.commands.*;
import fr.palmus.plugin.components.EvoComponent;
import fr.palmus.plugin.period.PeriodCaster;
import fr.palmus.plugin.mysql.DatabaseManager;
import fr.palmus.plugin.player.CustomPlayer;
import fr.palmus.plugin.economy.Economy;
import fr.palmus.plugin.listeners.*;
import fr.palmus.plugin.utils.fastboard.FastBoard;
import fr.palmus.plugin.websockets.Client;
import net.coreprotect.CoreProtectAPI;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EvoPlugin extends JavaPlugin {

    private static EvoPlugin INSTANCE;

    private EvoComponent storage;

    public Logger log;

    public ArrayList<Player> playerExp = new ArrayList<>();

    private CustomPlayer customPlayer;

    public CoreProtectAPI api;

    public LuckPerms LPapi;

    public boolean FarmlandsModules;

    public Economy econ;

    private PeriodCaster periodCaster;

    private ScheduledExecutorService executorMonoThread;
    private ScheduledExecutorService scheduledExecutorService;

    public File file = new File("plugins/EvoPlugin", "period.yml");
    public FileConfiguration cfg;

    Plugin plugin = Bukkit.getPluginManager().getPlugin("Craftconomy3");

    RegisteredServiceProvider<LuckPerms> provider;

    private DatabaseManager databaseManager;

    @Override
    public void onEnable() {

        log = getLogger();
        saveDefaultConfig();
        INSTANCE = this;
        databaseManager = new DatabaseManager();
        log.log(Level.INFO,ChatColor.DARK_GREEN + "-------------------------------------------------------------------");
        customPlayer = new CustomPlayer(this);
        storage = new EvoComponent();
        periodCaster = new PeriodCaster();
        setCommands();

        log.log(Level.INFO,ChatColor.GREEN + "Initialised !");

        getComponents().initHashmap();
        log.log(Level.INFO,ChatColor.GREEN + "Components storage module loaded !");

        if (plugin != null) {
            log.log(Level.INFO,ChatColor.GREEN + "Craftconomy Hooked !");
        }

        cfg = YamlConfiguration.loadConfiguration(file);
        try {
            getComponents().load();
        } catch (IOException e) {
            log.log(Level.SEVERE,ChatColor.RED + "Error while enabling configurations files, FATAL");
            getPluginLoader().disablePlugin(this);
            return;
        }
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
            getComponents().container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            log.log(Level.INFO,ChatColor.GREEN + "WorldGuard Hooked !");
        }

        provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            LPapi = provider.getProvider();
            log.log(Level.INFO,ChatColor.GREEN + "LuckPerms Hooked !");
        }
        setListeners();
        log.log(Level.INFO,ChatColor.DARK_GREEN + "-------------------------------------------------------------------");

        log.log(Level.WARNING,ChatColor.YELLOW + "Initializing recipies, this may take a few minutes because of Legacy intitialization");
        getComponents().initRecipies();
        log.log(Level.INFO,ChatColor.YELLOW + "Recipies initialized successfully !");

        log.log(Level.INFO,ChatColor.DARK_GREEN + "-------------------------------------------------------------------");

        try{
            getComponents().initFarmlands();
        }catch (NullPointerException e){
            FarmlandsModules = false;
            log.log(Level.WARNING, ChatColor.YELLOW + "Disabling Farmlands module, Farmlands undetected.");
        }

        log.log(Level.INFO,ChatColor.DARK_GREEN + "-------------------------------------------------------------------");

        try{
            econ = new Economy(this);
        }catch(Exception e){
            log.log(Level.SEVERE, ChatColor.RED + "Failed Load economy module FATAL Disabling not tranquillou bidou...");
            getPluginLoader().disablePlugin(this);
        }

        log.log(Level.INFO,ChatColor.DARK_GREEN + "-------------------------------------------------------------------");

        try{
            log.log(Level.INFO, ChatColor.YELLOW + "trying to connect websocket to evolium.fr");
            Client.LaunchSocket();
        }catch (UnknownHostException | ConnectException | NoClassDefFoundError | RuntimeException e ){
            log.log(Level.SEVERE, ChatColor.RED + "Failed to start websockets, EvoPlugin will not take care of web infos cause: " + e.getCause());
        }

        for (Player pl : Bukkit.getOnlinePlayers()) {
            try {
                getCustomPlayer().initPlayer(pl);
                this.econ.initPlayerEcon(pl);
            } catch (IOException | SQLException e) {
                log.log(Level.SEVERE, ChatColor.RED + "Failed Load economy module FATAL Disabling not tranquillou bidou...");
                getPluginLoader().disablePlugin(this);
            }
            FastBoard boards = new FastBoard(pl);
            getComponents().boards.put(pl.getUniqueId(), boards);
            for (FastBoard board : getComponents().boards.values()) {
                getComponents().updateBoard(pl);
            }
        }
    }

    @Override
    public void onDisable() {
        boolean safeDisabled = true;
        saveDefaultConfig();
        for(Player pl : playerExp){
            getCustomPlayer().get(pl).saveExp();
            econ.getPlayerEcon(pl).saveMoney();
        }
        try {
            cfg.save(file);
        } catch (IOException | NullPointerException e) {
            if(getDatabaseManager() == null){
                log.severe(ChatColor.RED + "FAILED TO SAVE CONFIG, ALL THE EXP WON THIS SESSION SHOULD BE LOST, FATAL.");
            }
            safeDisabled = false;
        }
        for(Player pl : playerExp){
            try {
                getCustomPlayer().saveData(pl, getDatabaseManager().getDatabase().getConnection());
            } catch (SQLException e) {
                log.severe(ChatColor.RED + "FAILED TO SAVE DATA ON DATABASE");
                safeDisabled = false;
            }
            getDatabaseManager().close();
        }
        if(safeDisabled) log.log(Level.INFO, ChatColor.GREEN + "Disabling EvoPlugin tranquillou bidou...");
    }

    public void setCommands(){
        getCommand("exp").setExecutor(new ExpExecutor());
        getCommand("periode").setExecutor(new PeriodExecutor());
        getCommand("money").setExecutor(new EconExecutor());
        getCommand("farmzone").setExecutor(new FarmzoneExecutor());
        getCommand("rtp").setExecutor(new RTPExecutor());
        log.log(Level.INFO,ChatColor.GREEN + "Commands modules Enabled");
    }

    public void setListeners(){
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new JoinQuitManager(), this);
        pm.registerEvents(new BlockManager(), this);
        pm.registerEvents(new CraftManager(), this);
        pm.registerEvents(new DamageManager(), this);
        pm.registerEvents(new InventoryManager(), this);
        pm.registerEvents(new ScoreboardUpdater(this, LPapi), this);
        log.log(Level.INFO,ChatColor.GREEN + "Listeners modules Enabled");
    }

    public EvoComponent getComponents(){
        return storage;
    }

    public static EvoPlugin getInstance(){
        return INSTANCE;
    }

    public CustomPlayer getCustomPlayer(){
        return customPlayer;
    }

    public PeriodCaster getPeriodCaster() {
        return periodCaster;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
}
