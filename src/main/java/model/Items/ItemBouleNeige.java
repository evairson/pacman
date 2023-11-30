package model.Items;

import model.PacMan;

public class ItemBouleNeige extends Item {
    public ItemBouleNeige(){
        super.setCollectable(true);
    }


    @Override
    public void setActive(boolean b){
        super.setActive(b);
        new BouleNeige(PacMan.INSTANCE.getPos(), PacMan.INSTANCE.getDirection());
    }

    
}
