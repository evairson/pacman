package model.Items;

public class Item {
    private boolean collectable;
    private boolean active = false;

    public Item(){
        this.collectable = false;
    }

    public boolean isCollectable(){
        return collectable;
    }

    public void setCollectable(boolean b){
        this.collectable = b;
    }

    public void setActive(boolean b){
        this.active = b;
    }

    public boolean isActive(){
        return this.active;
    }

    public String toString(){
        return "a";
    }
}
