package fr.palmus.plugin.components;

import com.sk89q.worldedit.math.BlockVector2;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import fr.palmus.plugin.EvoPlugin;
import fr.palmus.plugin.utils.CustomItem;
import fr.palmus.plugin.utils.ItemBuilder;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Cette classe rescense toutes les fonctions utiles pour éviter
 * de surchargé les autres classes
 */
public class EvoComponent {

    EvoPlugin main = EvoPlugin.getInstance();

    public HashMap<Material, Integer> prehistoire;

    public HashMap<Material, Integer> prehistoireCraft;

    int entities;

    public HashMap<String, Integer> prehistoireKill;

    public BlockVector2 preRegion1;
    public BlockVector2 preRegion2;
    public RegionContainer container;

    public void InitHashmap(){
        if(main.FarmlandsModules){
            prehistoireKill = new HashMap<String, Integer>(){{
                put("§eDésosseur", 75);
                put("§eAffamé", 75);
            }};
        }

        prehistoire = new HashMap<Material, Integer>(){{
            put(Material.STONE, 3);
            put(Material.LEGACY_WOOD, 2);
            put(Material.LEGACY_LEAVES, 1);
            put(Material.LEGACY_LEAVES_2, 1);
            put(Material.GRASS, 1);
            put(Material.DIRT, 1);
            put(Material.LEGACY_LOG, 2);
            put(Material.LEGACY_LOG_2, 2);
        }};

        prehistoireCraft = new HashMap<Material, Integer>(){{
            put(Material.STRING, 10);
            put(Material.WOODEN_AXE, 25);
            put(Material.WOODEN_HOE, 25);
            put(Material.WOODEN_PICKAXE, 25);
            put(Material.WOODEN_SWORD, 25);
        }};
    }

    /**
     * Renvoi le préfixe devant être devant "tous" les messages du plugin
     */
    public String getPrefix(String type){

        if(type.equalsIgnoreCase("error")){
            return "§4[§cEvolium§4]§r §l§7| §c";
        }

        if(type.equalsIgnoreCase("ok")){
            return "§2[§aEvolium§2]§r §l§7| §a";
        }

        return "§5[§dEvolium§5]§r §l§7| §d";
    }

    /**
     * Permet de récupérer la position défini par le /setlobby
     * + d'infos -> LobbyExecutor
     */
    public Location getLobby(){
        if(main.getConfig().get("Location.Lobby.x") == null){
            return null;
        }
        double x = main.getConfig().getDouble("Location.Lobby.x");
        double y = main.getConfig().getDouble("Location.Lobby.y");
        double z = main.getConfig().getDouble("Location.Lobby.z");
        World world = Bukkit.getWorld((String) main.getConfig().get("Location.Lobby.world"));
        float pitch = (float) main.getConfig().getDouble("Location.Lobby.pitch");
        float yaw = (float) main.getConfig().getDouble("Location.Lobby.yaw");

        return new Location(world, x, y, z, pitch, yaw);
    }

    /**
     * Permet l'envoi d'un feu d'artifice
     */
    public void launchFirework(Player p, int speed) {
        Firework fw = (Firework) p.getWorld().spawn(p.getEyeLocation(), Firework.class);
        FireworkMeta meta = fw.getFireworkMeta();
        meta.setPower(1);
        meta.addEffect(FireworkEffect.builder().withColor(Color.PURPLE).withFade(Color.FUCHSIA).with(FireworkEffect.Type.BALL_LARGE).withFlicker().trail(true).withTrail().build());
        fw.setFireworkMeta(meta);
    }

    /**
     * + d'infos -> ItemBuilder
     */
    public ItemStack getCompass(){
        ItemStack compass = new ItemBuilder(Material.COMPASS).setName("§dMenu").addEnchant(Enchantment.DIG_SPEED, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).toItemStack();
        return compass;
    }

    /**
     * Permet de remplir entièrement un inventaire d'un item
     * Vachement pratique
     */
    public void setDefaultMaterial(Inventory inv, ItemStack defaultItem) {

        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null) inv.setItem(i, defaultItem);
            else if (inv.getItem(i).getType() == Material.AIR) inv.setItem(i, defaultItem);
        }
    }

    public void LobbyEffect(Player pl){
        pl.teleport(this.getLobby());
        pl.sendTitle("§dE§5v§do§5l§di§5u§dm","§5§nLobby", 20, 60, 20);
        pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1, 1);
        this.launchFirework(pl, 10);
        pl.setHealth(20);
        pl.setSaturation(20);
    }

    public String getPeriod(int i){
        if(i == 0){
            return "Préhistoire";
        }

        if(i == 1){
            return "Antiquité";
        }

        if(i == 2){
            return "Moyen-Age";
        }

        if(i == 3){
            return "Renaissance";
        }

        if(i == 4){
            return "Industriel";
        }

        if(i == 5){
            return "20s";
        }

        if(i == 6){
            return "Dieux";
        }

        return "";
    }

    public CoreProtectAPI getCoreProtect() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("CoreProtect");

        // Check that CoreProtect is loaded
        if (plugin == null || !(plugin instanceof CoreProtect)) {
            return null;
        }

        // Check that the API is enabled
        CoreProtectAPI CoreProtect = ((CoreProtect) plugin).getAPI();
        if (!CoreProtect.isEnabled()) {
            return null;
        }

        return CoreProtect;
    }

    public void createScoreboard(Player pl){
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective obj = board.registerNewObjective("EvoScoreboard", "dummy", "§dEVOLIUM");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        PlayerManager plm = EvoPlugin.getInstance().plmList.get(pl);
        int limiter =  plm.getLimiter();

        /** TODO
         * User user = Main.getInstance().LPapi.getPlayerAdapter(Player.class).getUser(pl);
         * prefix = user.getCachedData().getMetaData().getPrefix().replace("&", "§");
          */

        String period = EvoPlugin.getInstance().getComponents().getPeriod(plm.getPeriod());

        obj.getScore( "§7").setScore(11);
        obj.getScore( "      §7+-----§2Époque§7-----+").setScore(10);
        obj.getScore( "§aPériode actuel: §2" + period + " " + plm.getPeriodLimitStyleBar(limiter)).setScore(9);
        obj.getScore( "§aPoint d'Expérience: §2" + plm.getStringExp(plm.getExp())  + "/" + plm.getPeriodLimitStyle(limiter)).setScore(8);
        obj.getScore( "§aObjectifs: §2").setScore(7);
        obj.getScore( " §6").setScore(6);
        obj.getScore( "      §7+-----§6Infos§7------+").setScore(5);
        obj.getScore( "§ePseudo: §6" + pl.getDisplayName()).setScore(4);
        obj.getScore( "§eGrade: §6").setScore(3); // TODO
        obj.getScore( "§eArgent: §6").setScore(2); // TODO
        obj.getScore( " ").setScore(1);
        obj.getScore( "§aplay.evolium.fr").setScore(0);

        pl.setScoreboard(board);
    }

    public void initFarmlands(){
        RegionManager regions = container.get((com.sk89q.worldedit.world.World) Bukkit.getWorld(main.getConfig().getString("farmlands.world")));
        preRegion1 = regions.getRegion(main.getConfig().getString("farmlands.name.prehistoire")).getPoints().get(0);
        preRegion2 = regions.getRegion(main.getConfig().getString("farmlands.name.prehistoire")).getPoints().get(1);
        double xMiddle = (double) ((preRegion1.getBlockX() + preRegion2.getBlockX()) / 2);
        double zMiddle = (double) (preRegion1.getBlockZ() + preRegion2.getBlockZ()) / 2;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
            @Override
            public void run() {
                entities = 0;
                for(Entity ent : Bukkit.getWorld(main.getConfig().getString("farmlands.world")).getEntities()){
                    if(ent.getType() == EntityType.ZOMBIE || ent.getType() == EntityType.SKELETON){
                        entities = entities + 1;
                    }
                }
                if(entities >= 15){

                }else{
                    System.out.println(entities);
                    double xPos = ThreadLocalRandom.current().nextDouble(xMiddle, xMiddle + 1);
                    double zPos = ThreadLocalRandom.current().nextDouble(zMiddle, zMiddle + 1);

                    MobManager.spawnMob(xPos, zPos, Bukkit.getWorld(main.getConfig().getString("farmlands.world")));
                }
            }
        }, 0L, 300L);
    }

    @Deprecated
    public void initRecipies(){
        Iterator<Recipe> it = main.getServer().recipeIterator();
        Recipe recipe;
        while (it.hasNext()) {
            recipe = it.next();
            if (recipe != null && recipe.getResult().getType() == null) {
                it.remove();
            }
        }

        NamespacedKey key = new NamespacedKey(main, "emerald_sword");

        ShapedRecipe recipie = new ShapedRecipe(key, new ItemStack(Material.STRING));

        recipie.shape("***");

        recipie.setIngredient('*', CustomItem.fiber.getData());

        Bukkit.addRecipe(recipie);
    }
}
