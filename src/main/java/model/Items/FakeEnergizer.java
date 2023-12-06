package model.Items;

import model.Ghost;
import model.PacMan;

import java.util.ArrayList;

public class FakeEnergizer extends Item {

    private final static ArrayList<FakeEnergizer> itemList = new ArrayList<>();
    public int frameActivity;

    public FakeEnergizer(){
        super.setCollectable(true);
        this.frameActivity = 0;
        itemList.add(this);
    }

    public void setActive(boolean b){
        super.setActive(b);
        this.frameActivity = 0;

        PacMan.INSTANCE.setFakeEnergized(b);
    }

    public static boolean isOneActive(){
        for(FakeEnergizer e : itemList){
            if(e != null){
                if(e.isActive()){ return true; }
            }
        }
        return false;
    }
}
