package model.Items;

import model.PacMan;

public class ItemBouleNeige extends Item {
    public ItemBouleNeige(){
        super.setCollectable(true);
        url="BouleNeige.png";
    }


    @Override
    public void setActive(boolean b){
        super.setActive(b);
        BouleNeige.INSTANCE.lancer();
    }

    
}
