package model.Items;

import model.Ghost;
import model.PacMan;

public class Energizer extends Item {

    private static boolean energized;
    public static int frameEnergizer;



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
