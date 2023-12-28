package model.Items;

import model.Ghost;
import model.PacMan;
import java.util.ArrayList;

/**
 * @see Item
 * 
 * Cette Item permet de se transformer pendant un certain temps en fantôme 
 * afin de ne plus être suivi par les autres fantômes
 * Changement de l'apparence de pacMan 
 * Item Collectable
 * 
 */

public class FakeEnergizer extends Item { // TODO: changer le nom de la classe peut compréhensible

    private final static ArrayList<FakeEnergizer> itemList = new ArrayList<>();
    public int frameActivity;

    public FakeEnergizer(){
        super.setCollectable(true);
        this.frameActivity = 0;
        itemList.add(this);
        this.url = (FakeEnergizer.class.getResource("FakeGhost.jpg")).toString();
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
