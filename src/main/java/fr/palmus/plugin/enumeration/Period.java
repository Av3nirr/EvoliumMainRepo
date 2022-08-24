package fr.palmus.plugin.enumeration;

public enum Period {
    PREHISTOIRE, ANTIQUITE, MOYENAGE, RENNAISSANCE, INDUSTRIEL, ACTUELLE, FUTUR;

    public static Period getNextPeriod(Period period){
        if(period == FUTUR){
            return FUTUR;
        }
        boolean isPeriod = false;
        for(Period per : Period.values()){
            if(isPeriod){
                return per;
            }
            if(per == period){
                isPeriod = true;
            }
        }
        return null;
    }

    public static Period getBelowPeriod(Period period){
        if(period == PREHISTOIRE){
            return PREHISTOIRE;
        }
        Period currentPeriod = null;
        for(Period per : Period.values()){
            if(per == period){
                return currentPeriod;
            }
            currentPeriod = per;
        }
        return null;
    }
}
