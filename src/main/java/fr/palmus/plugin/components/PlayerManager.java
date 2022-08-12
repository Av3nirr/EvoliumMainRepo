package fr.palmus.plugin.components;

import fr.palmus.plugin.EvoPlugin;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
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
            return;
        }
        pl.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§5§kII§r §d+§5" + exp + " §davancement: §5" + getExp() + "§7/§5" + getPeriodLimitStyle(getLimiter()) + "§r§5§kII"));
    }

    public int getPeriod(){
        return (int) main.cfg.get(pl.getDisplayName() + ".period");
    }

    public void setExp(int exp) throws IOException {
        experience = exp;
    }

    public void periodUpgrade() throws IOException {
        if(getLimiter() == 3) {
            pl.sendTitle("§a" + main.getComponents().getPeriod(period + 1) + "I", "§2---------------", 20, 60, 20);
            pl.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§2UPGRADE §2: §a" + main.getComponents().getPeriod(period) + " " + getLimiterStyle() + "§2 >> §a" + main.getComponents().getPeriod(period + 1) + "I"));
            pl.playSound(pl.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1, 1);
            pl.playSound(pl.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_TWINKLE, 1, 1);
            //TODO Main.craftconomy.getAccountManager().getAccount(pl.getDisplayName(), false).deposit(10000, "world", "Electrum");
            pl.sendMessage(main.getComponents().getPrefix("") + "10000$ §dont été ajouté à votre compte pour être monté " + main.getComponents().getPeriod(period + 1) + " I");
            main.cfg.set(pl.getDisplayName() + ".period", period += 1);
            main.cfg.set(pl.getDisplayName() + ".rank", 1);
            main.cfg.save(main.file);
            return;
        }
        pl.sendTitle("§a" + main.getComponents().getPeriod(period) + " " + getLimiterStyle() + "I", "§2---------------",20, 60, 20);
        pl.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§2UPGRADE §2: §a" + main.getComponents().getPeriod(period) + " " +getLimiterStyle() + "§2 >> §a" + main.getComponents().getPeriod(period) + " " + getLimiterStyle() + "I"));
        pl.playSound(pl.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1, 1);
        pl.playSound(pl.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_TWINKLE, 1, 1);
        //TODO Main.craftconomy.getAccountManager().getAccount(pl.getDisplayName(), false).deposit(5000, "world", "Electrum");
        pl.sendMessage(main.getComponents().getPrefix("") + "5000$ §dont été ajouté à votre compte pour être monté " + main.getComponents().getPeriod(period) + " I");
        main.cfg.set(pl.getDisplayName() + ".rank", getLimiter() + 1);
        main.cfg.save(main.file);
    }

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
            return;
        }
        main.cfg.set(pl.getDisplayName() + ".rank", getLimiter() - 1);
        main.cfg.save(main.file);
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
}
