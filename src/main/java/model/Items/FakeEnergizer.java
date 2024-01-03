package model.Items;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * @see Item
 * 
 * Cette item est un faux energizer et handicap le joueur quand il le récupère
 * Les murs sont alors invisibles (mais toujours présents)
 * Il n'est pas collectable et s'active directement en le récupérant
 * 
 */

public class FakeEnergizer extends Item{ 

    public final static ArrayList<FakeEnergizer> itemList = new ArrayList<>();
    public int frameActivity;

    public FakeEnergizer(){
        super.setCollectable(false);
        this.frameActivity = 0;
        itemList.add(this);
    }

    /*public boolean isCollectable(){
        return super.isCollectable();
    }

    public boolean isActive(){
        return super.isActive();
    }*/

    public void setActive(boolean b){
        super.setActive(b);
        this.frameActivity = 0;
    }

    public static boolean isOneActive(){
        for(FakeEnergizer e : itemList){
            if(e != null){
                if(e.isActive()){ return true; }
            }
        }
        return false;
    }

    public String toString(){
        return "itemtest";
    }
}