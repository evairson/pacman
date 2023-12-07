package model.Items;

import model.PacMan;

public class ItemBouleNeige extends Item {
    public ItemBouleNeige(){
        super.setCollectable(true);
        url="bouleNeige.png";
    }

    @Override
    public void setActive(boolean b){
        super.setActive(b);
        BouleNeige.INSTANCE.lancer();
    }

    
}
