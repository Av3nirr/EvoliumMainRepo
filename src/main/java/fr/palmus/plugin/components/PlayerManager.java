package fr.palmus.plugin.components;

import fr.palmus.plugin.EvoPlugin;
import fr.palmus.plugin.listeners.custom.PlayerExpChangeEvent;
import fr.palmus.plugin.listeners.custom.PlayerPeriodChangeEvent;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.io.IOException;

public class PlayerManager {

    EvoPlugin main = EvoPlugin.getInstance();

    private Player pl;

    private int experience;

    private int period;

    public PlayerManager(Player p, int exp){
        this.pl = p;
        this.experience = exp;
        main.playerExp.add(pl);
        period = main.cfg.getInt(pl.getDisplayName() + ".period");
    }

    public Player getPlayer() {
        return this.pl;
    }

    public void addExp(int exp) {
        experience = experience + exp;
        if(experience >= getPeriodLimit(getLimiter())){
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
            PlayerExpChangeEvent event = new PlayerExpChangeEvent(pl, experience,ExpAction.ADD);
            Bukkit.getServer().getPluginManager().callEvent(event);
            return;
        }
        pl.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§5§kII§r §d+§5" + exp + " §davancement: §5" + getExp() + "§7/§5" + getPeriodLimitStyle(getLimiter()) + " §r§5§kII"));
    }

    public int getPeriod(){
        return (int) main.cfg.get(pl.getDisplayName() + ".period");
    }

    public void setExp(int exp) throws IOException {
        int oldExp = experience;
        experience = exp;

        if(exp > oldExp){
            PlayerExpChangeEvent event = new PlayerExpChangeEvent(pl, experience,ExpAction.ADD);
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
        if(exp < oldExp){
            PlayerExpChangeEvent event = new PlayerExpChangeEvent(pl, experience,ExpAction.SUBSTRACT);
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
    }

    public void resetExp() {
        experience = 0;
        PlayerExpChangeEvent event = new PlayerExpChangeEvent(pl, experience,ExpAction.RESET);
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    public void periodUpgrade() throws IOException {
        if(getLimiter() == 3) {
            pl.sendTitle("§a" + main.getComponents().getPeriod(period + 1) + " I", "§2---------------", 20, 60, 20);
            pl.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§2UPGRADE §2: §a" + main.getComponents().getPeriod(period) + " " + getLimiterStyle() + "§2 >> §a" + main.getComponents().getPeriod(period + 1) + "I"));
            pl.playSound(pl.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1, 1);
            pl.playSound(pl.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_TWINKLE, 1, 1);
            main.econ.getPlayerEcon(pl).addMoney(10000);
            pl.sendMessage(main.getComponents().getPrefix("") + "10000$ §dont été ajouté à votre compte pour être monté " + main.getComponents().getPeriod(period + 1) + " I");
            main.cfg.set(pl.getDisplayName() + ".period", period += 1);
            main.cfg.set(pl.getDisplayName() + ".rank", 1);
            main.cfg.save(main.file);
            PlayerPeriodChangeEvent event = new PlayerPeriodChangeEvent(getPlayer(), main.cfg.getInt(pl.getDisplayName() + ".period"), getLimiter(), PeriodAction.UPGRADE);
            Bukkit.getServer().getPluginManager().callEvent(event);
            return;
        }
        pl.sendTitle("§a" + main.getComponents().getPeriod(period) + " " + getLimiterStyle() + "I", "§2---------------",20, 60, 20);
        pl.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§2UPGRADE §2: §a" + main.getComponents().getPeriod(period) + " " +getLimiterStyle() + "§2 >> §a" + main.getComponents().getPeriod(period) + " " + getLimiterStyle() + "I"));
        pl.playSound(pl.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1, 1);
        pl.playSound(pl.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_TWINKLE, 1, 1);
        main.econ.getPlayerEcon(pl).addMoney(5000);
        pl.sendMessage(main.getComponents().getPrefix("") + "5000$ §dont été ajouté à votre compte pour être monté " + main.getComponents().getPeriod(period) + " I");
        main.cfg.set(pl.getDisplayName() + ".rank", getLimiter() + 1);
        main.cfg.set(pl.getDisplayName() + ".period", period);
        main.cfg.save(main.file);
        PlayerPeriodChangeEvent event = new PlayerPeriodChangeEvent(getPlayer(), main.cfg.getInt(pl.getDisplayName() + ".period"), getLimiter(), PeriodAction.UPGRADE);
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    public void resetPeriod() throws IOException {
        pl.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§cRESET §2: §a" + main.getComponents().getPeriod(period) + " " + getLimiterStyle() + "§2 >> §a" + main.getComponents().getPeriod(0) + " I"));
        pl.playSound(pl.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
        pl.playSound(pl.getLocation(), Sound.ENTITY_BAT_DEATH, 1, 1);
        main.cfg.set(pl.getDisplayName() + ".rank", 1);
        main.cfg.set(pl.getDisplayName() + ".limiter", 0);
        main.cfg.set(pl.getDisplayName() + ".period", 0);
        main.cfg.save(main.file);
        PlayerPeriodChangeEvent event = new PlayerPeriodChangeEvent(getPlayer(), main.cfg.getInt(pl.getDisplayName() + ".period"), getLimiter(), PeriodAction.RESET);
        Bukkit.getServer().getPluginManager().callEvent(event);    }

    public int getLimiter(){
        if(EvoPlugin.getInstance().cfg.get(pl.getDisplayName() + ".rank") == null){
            main.cfg.set(pl.getDisplayName() + ".limiter", 0);
            return 0;
        }
        int limiter =  EvoPlugin.getInstance().cfg.getInt(pl.getDisplayName() + ".rank");
        return limiter;
    }

    public String getLimiterStyle(){
        int i =  EvoPlugin.getInstance().cfg.getInt(pl.getDisplayName() + ".rank");
        if(i == 1){
            return "I";
        }

        if(i == 2){
            return "II";
        }

        if(i == 3){
            return "III";
        }

        return "";
    }

    public void periodDowngrade() throws IOException {
        if(getLimiter() == 1) {
            main.cfg.set(pl.getDisplayName() + ".period", period -= 1);
            main.cfg.set(pl.getDisplayName() + ".rank", 3);
            main.cfg.save(main.file);
            PlayerPeriodChangeEvent event = new PlayerPeriodChangeEvent(getPlayer(), main.cfg.getInt(pl.getDisplayName() + ".period"), getLimiter(), PeriodAction.DOWNGRADE);
            Bukkit.getServer().getPluginManager().callEvent(event);
            return;
        }
        main.cfg.set(pl.getDisplayName() + ".rank", getLimiter() - 1);
        main.cfg.save(main.file);
        PlayerPeriodChangeEvent event = new PlayerPeriodChangeEvent(getPlayer(), main.cfg.getInt(pl.getDisplayName() + ".period"), getLimiter(), PeriodAction.DOWNGRADE);
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    public void saveExp(){
        main.cfg.set(pl.getDisplayName() + ".exp", getExp());
    }

    public int getExp(){
        return experience;
    }

    public String getStringExp(int exp){
        if(exp <= 999){
            return String.valueOf(exp);
        }
        if(exp <= 9999){
            int expConvert = exp / 100;
            char[] digits1 = String.valueOf(expConvert).toCharArray();
            return digits1[0] + "." + digits1[1] + "k";
        }
        int expConvert = exp / 100;
        char[] digits1 = String.valueOf(expConvert).toCharArray();
        return digits1[0] +"" + digits1[1] + "." + digits1[2] + "k";
    }

    public int getPeriodLimit(int i){
        if(i == 1){
            return 20000;
        }

        if(i == 2){
            return 30000;
        }

        if(i == 3){
            return 450000;
        }

        return 0;
    }

    public String getPeriodLimitStyle(int i){
        if(i == 1){
            return "20k";
        }

        if(i == 2){
            return "30k";
        }

        if(i == 3){
            return "45k";
        }

        return "";
    }

    public String getPeriodLimitStyleBar(int i){
        if(i == 1){
            return "I";
        }

        if(i == 2){
            return "II";
        }

        if(i == 3){
            return "III";
        }

        return "";
    }

    public String getEntirePeriodStyle(){
        return main.getComponents().getPeriod(period) + " " + getLimiterStyle();
    }

    public enum PeriodAction{
        UPGRADE, DOWNGRADE, RESET;
    }

    public enum ExpAction{
        ADD, SUBSTRACT, RESET;
    }

    public enum Period{
        PREHISTOIRE, ANTIQUITE, MOYENAGE, RENNAISSANCE, INDUSTRIEL, ACTUELLE, FUTUR;
    }

    public Period getEnumPeriodFromInt(int i){
        if(i == 0){
            return Period.PREHISTOIRE;
        }

        if(i == 1){
            return Period.ANTIQUITE;
        }

        if(i == 2){
            return Period.MOYENAGE;
        }

        if(i == 3){
            return Period.RENNAISSANCE;
        }

        if(i == 4){
            return Period.INDUSTRIEL;
        }

        if(i == 5){
            return Period.ACTUELLE;
        }

        if(i == 6){
            return Period.FUTUR;
        }
        return null;
    }
}
