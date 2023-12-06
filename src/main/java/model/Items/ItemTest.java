package model.Items;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ItemTest extends Item{

    private final static ArrayList<ItemTest> itemList = new ArrayList<>();
    public int frameActivity;

    public ItemTest(){
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
        for(ItemTest e : itemList){
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