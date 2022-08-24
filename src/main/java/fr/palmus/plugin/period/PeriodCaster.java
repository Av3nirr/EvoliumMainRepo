package fr.palmus.plugin.period;

import fr.palmus.plugin.EvoPlugin;
import fr.palmus.plugin.enumeration.Period;
import fr.palmus.plugin.period.key.StorageKey;

public class PeriodCaster {

    EvoPlugin main = EvoPlugin.getInstance();

    public String getLimiterString(int i){
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

    public int getIntPeriodLimit(int i){
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

    public String getStringPeriodLimit(int i){
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

    public int getIntPeriodFromEnum(Period i){
        if(i == Period.PREHISTOIRE){
            return 0;
        }

        if(i == Period.ANTIQUITE){
            return 1;
        }

        if(i == Period.MOYENAGE){
            return 2;
        }

        if(i == Period.RENNAISSANCE){
            return 3;
        }

        if(i == Period.INDUSTRIEL){
            return 4;
        }

        if(i == Period.ACTUELLE){
            return 5;
        }

        if(i == Period.FUTUR){
            return 6;
        }
        return 0;
    }

    public String getPeriod(Period period){
        if(period == Period.PREHISTOIRE){
            return "Préhistoire";
        }

        if(period == Period.ANTIQUITE){
            return "Antiquité";
        }

        if(period == Period.MOYENAGE){
            return "Moyen-Age";
        }

        if(period == Period.RENNAISSANCE){
            return "Renaissance";
        }

        if(period == Period.INDUSTRIEL){
            return "Industriel";
        }

        if(period == Period.ACTUELLE){
            return "20s";
        }

        if(period == Period.FUTUR){
            return "Dieux";
        }

        return "";
    }

    public StorageKey getStorageKey(Period period, Integer limiter){
        Integer perInt = main.getPeriodCaster().getIntPeriodFromEnum(period) * 10;

        return new StorageKey(perInt + limiter);
    }
}
