package model.Items;

public class Item {
    private boolean collectable;
    private boolean active = false;
    protected String url;

    public Item(){
        this.collectable = false;
    }

    public String getUrl(){
        return url;
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
