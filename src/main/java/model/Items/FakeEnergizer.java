package model.Items;

import model.Ghost;
import model.PacMan;

public class FakeEnergizer extends Item {
    
    private static boolean energized;
    public boolean special;
    public static int frameEnergizer;

    public FakeEnergizer(boolean energized , boolean special , int frameEnergizer){
        this.energized=energized;
        this.special=special;
        this.frameEnergizer=frameEnergizer;
        url = "FakeGhost.jpg";
    } 

    public boolean isSpecial(){
        return this.special;
    }

    public static boolean isFakeEnergized(){
        return energized;
    }

    public static void setFakeEnergized(boolean b){
        energized = b;
        frameEnergizer = 0;
        PacMan.INSTANCE.setFakeEnergized(b);
        //Ghost.energized = false;
    }
}
