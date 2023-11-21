package model.Items;

import model.Critter;
import model.Ghost;
import model.PacMan;

import java.util.ArrayList;

public class Energizer extends Item {

    public int frameActivity;
    private static final ArrayList<Energizer> itemList = new ArrayList<>();

    public Energizer(){
        super.setCollectable(false);
        this.frameActivity = 0;
        itemList.add(this);
    }

    public static boolean isOneActive(){
        for(Energizer e : itemList){
            if(e != null){
                if(e.isActive()){ return true; }
            }
        }
        return false;
    }

    public /*static*/ void setActive(boolean b){
        super.setActive(b);
        this.frameActivity = 0;
        PacMan.INSTANCE.setEnergized(Energizer.isOneActive());
        Ghost.energized = Energizer.isOneActive();
    }
}
