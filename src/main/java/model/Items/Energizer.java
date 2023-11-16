package model.Items;

import model.Ghost;
import model.PacMan;

public class Energizer extends Item {

    private static boolean energized;
    private boolean special;
    public static int frameEnergizer;

    public Energizer(boolean energized , boolean special , int frameEnergizer){
        this.energized=energized;
        this.special=special;
        this.frameEnergizer=frameEnergizer;
    }

    public boolean isSpecial(){
        return this.special;
    }

    public static boolean isEnergized(){
        return energized;
    }

    public static void setEnergized(boolean b){
        energized = b;
        frameEnergizer = 0;
        PacMan.INSTANCE.setEnergized(true);
        Ghost.energized = true;
    }
}
