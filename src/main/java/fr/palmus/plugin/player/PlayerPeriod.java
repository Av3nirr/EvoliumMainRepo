package fr.palmus.plugin.player;

import fr.palmus.plugin.EvoPlugin;
import fr.palmus.plugin.enumeration.Period;
import fr.palmus.plugin.listeners.custom.PlayerExpChangeEvent;
import fr.palmus.plugin.listeners.custom.PlayerPeriodChangeEvent;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.io.IOException;

public class PlayerPeriod {

    EvoPlugin main = EvoPlugin.getInstance();

    private Player pl;

    private int experience;

    private Period period;

    public PlayerPeriod(Player p, int exp){
        this.pl = p;
        this.experience = exp;
        main.playerExp.add(pl);
        period = main.getPeriodCaster().getEnumPeriodFromInt(main.cfg.getInt(pl.getUniqueId() + ".period"));
    }

    public Player getPlayer() {
        return this.pl;
    }

    public void addExp(int exp) {
        experience = experience + exp;
        if(experience >= main.getPeriodCaster().getIntPeriodLimit(getLimiter())){
            try{
                periodUpgrade();
            }catch (IOException e){
                e.printStackTrace();
            }
            try{
                setExp(0);
            }catch (IOException e){
                e.printStackTrace();
            }
            PlayerExpChangeEvent event = new PlayerExpChangeEvent(pl, experience,ExpAction.ADD, main);
            Bukkit.getServer().getPluginManager().callEvent(event);
            return;
        }
        PlayerExpChangeEvent event = new PlayerExpChangeEvent(pl, experience,ExpAction.ADD, main);
        Bukkit.getServer().getPluginManager().callEvent(event);
        pl.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§5§kII§r §d+§5" + exp + " §davancement: §5" + getExp() + "§7/§5" + main.getPeriodCaster().getStringPeriodLimit(getLimiter()) + " §r§5§kII"));
    }

    public void setExp(int exp) throws IOException {
        int oldExp = experience;
        experience = exp;

        if(exp > oldExp){
            PlayerExpChangeEvent event = new PlayerExpChangeEvent(pl, experience,ExpAction.ADD, main);
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
        if(exp < oldExp){
            PlayerExpChangeEvent event = new PlayerExpChangeEvent(pl, experience,ExpAction.SUBSTRACT, main);
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
    }

    public void resetExp() {
        experience = 0;
        PlayerExpChangeEvent event = new PlayerExpChangeEvent(pl, experience,ExpAction.RESET, main);
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    public void periodUpgrade() throws IOException {
        if(getLimiter() == 3) {
            pl.sendTitle("§a" + main.getPeriodCaster().getPeriod(Period.getNextPeriod(period)) + " I", "§2---------------", 20, 60, 20);
            pl.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§2UPGRADE §2: §a" + main.getPeriodCaster().getPeriod(period) + " " + main.getPeriodCaster().getLimiterString(EvoPlugin.getInstance().cfg.getInt(pl.getUniqueId() + ".rank")) + "§2 >> §a" + main.getPeriodCaster().getPeriod(Period.getNextPeriod(period)) + "I"));
            pl.playSound(pl.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1, 1);
            pl.playSound(pl.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_TWINKLE, 1, 1);
            main.econ.getPlayerEcon(pl).addMoney(10000);
            pl.sendMessage(main.getComponents().getPrefix("") + "10000$ §dont été ajouté à votre compte pour être monté " + main.getPeriodCaster().getPeriod(Period.getNextPeriod(period)) + " I");
            main.cfg.set(pl.getUniqueId() + ".period", main.getPeriodCaster().getIntPeriodFromEnum(period) + 1);
            main.cfg.set(pl.getUniqueId() + ".rank", 1);
            main.cfg.save(main.file);
            PlayerPeriodChangeEvent event = new PlayerPeriodChangeEvent(getPlayer(), main.cfg.getInt(pl.getUniqueId() + ".period"), getLimiter(), PeriodAction.UPGRADE, main);
            Bukkit.getServer().getPluginManager().callEvent(event);
            return;
        }
        pl.sendTitle("§a" + main.getPeriodCaster().getPeriod(period) + " " + main.getPeriodCaster().getLimiterString(EvoPlugin.getInstance().cfg.getInt(pl.getUniqueId() + ".rank")) + "I", "§2---------------",20, 60, 20);
        pl.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§2UPGRADE §2: §a" + main.getPeriodCaster().getPeriod(period) + " " + main.getPeriodCaster().getLimiterString(EvoPlugin.getInstance().cfg.getInt(pl.getUniqueId() + ".rank")) + "§2 >> §a" + main.getPeriodCaster().getPeriod(period) + " " + main.getPeriodCaster().getLimiterString(EvoPlugin.getInstance().cfg.getInt(pl.getUniqueId() + ".rank")) + "I"));
        pl.playSound(pl.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1, 1);
        pl.playSound(pl.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_TWINKLE, 1, 1);
        main.econ.getPlayerEcon(pl).addMoney(5000);
        pl.sendMessage(main.getComponents().getPrefix("") + "5000$ §dont été ajouté à votre compte pour être monté " + main.getPeriodCaster().getPeriod(period) + " I");
        main.cfg.set(pl.getUniqueId() + ".rank", getLimiter() + 1);
        main.cfg.set(pl.getUniqueId() + ".period", period);
        main.cfg.save(main.file);
        PlayerPeriodChangeEvent event = new PlayerPeriodChangeEvent(getPlayer(), main.cfg.getInt(pl.getUniqueId() + ".period"), getLimiter(), PeriodAction.UPGRADE, main);
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    public void resetPeriod() throws IOException {
        pl.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§cRESET §2: §a" + main.getPeriodCaster().getPeriod(period) + " " + main.getPeriodCaster().getLimiterString(EvoPlugin.getInstance().cfg.getInt(pl.getUniqueId() + ".rank")) + "§2 >> §a" + main.getPeriodCaster().getPeriod(Period.PREHISTOIRE) + " I"));
        pl.playSound(pl.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
        pl.playSound(pl.getLocation(), Sound.ENTITY_BAT_DEATH, 1, 1);
        main.cfg.set(pl.getUniqueId() + ".rank", 1);
        main.cfg.set(pl.getUniqueId() + ".limiter", 0);
        main.cfg.set(pl.getUniqueId() + ".period", 0);
        main.cfg.save(main.file);
        experience = 0;
        period = Period.PREHISTOIRE;
        PlayerPeriodChangeEvent event = new PlayerPeriodChangeEvent(getPlayer(), main.cfg.getInt(pl.getUniqueId() + ".period"), getLimiter(), PeriodAction.RESET, main);
        Bukkit.getServer().getPluginManager().callEvent(event);    }

    public int getLimiter(){
        if(EvoPlugin.getInstance().cfg.get(pl.getUniqueId() + ".rank") == null){
            main.cfg.set(pl.getUniqueId() + ".limiter", 0);
            return 0;
        }
        int limiter =  EvoPlugin.getInstance().cfg.getInt(pl.getUniqueId() + ".rank");
        return limiter;
    }

    public void periodDowngrade() throws IOException {
        if(getLimiter() == 1) {
            main.cfg.set(pl.getUniqueId() + ".period", main.getPeriodCaster().getIntPeriodFromEnum(Period.getBelowPeriod(period)));
            main.cfg.set(pl.getUniqueId() + ".rank", 3);
            main.cfg.save(main.file);
            PlayerPeriodChangeEvent event = new PlayerPeriodChangeEvent(getPlayer(), main.cfg.getInt(pl.getUniqueId() + ".period"), getLimiter(), PeriodAction.DOWNGRADE, main);
            Bukkit.getServer().getPluginManager().callEvent(event);
            return;
        }
        main.cfg.set(pl.getUniqueId() + ".rank", getLimiter() - 1);
        main.cfg.save(main.file);
        PlayerPeriodChangeEvent event = new PlayerPeriodChangeEvent(getPlayer(), main.cfg.getInt(pl.getUniqueId() + ".period"), getLimiter(), PeriodAction.DOWNGRADE, main);
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    public void saveExp(){
        main.cfg.set(pl.getUniqueId() + ".exp", getExp());
    }

    public int getExp(){
        return experience;
    }

    public String getEntirePeriodStyle(){
        return main.getPeriodCaster().getPeriod(period) + " " + main.getPeriodCaster().getLimiterString(EvoPlugin.getInstance().cfg.getInt(pl.getUniqueId() + ".rank"));
    }

    public enum PeriodAction{
        UPGRADE, DOWNGRADE, RESET;
    }

    public enum ExpAction{
        ADD, SUBSTRACT, RESET;
    }

    public Period getPeriod(){
        return period;
    }

}
