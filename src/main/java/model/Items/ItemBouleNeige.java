package model.Items;

/**
 * @see Item
 * 
 * Cette Item permet de lancer une boule de neige 
 * @see BouleNeige 
 * 
 */

public class ItemBouleNeige extends Item {
    public ItemBouleNeige(){
        super.setCollectable(true);
        url = (ItemBouleNeige.class.getResource("bouleNeige.png")).toString();
    }

    @Override
    public void setActive(boolean b){
        super.setActive(b);
        BouleNeige.INSTANCE.lancer();
    }

    
}
