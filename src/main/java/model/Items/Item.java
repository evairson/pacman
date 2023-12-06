package model.Items;

import javafx.scene.image.ImageView;

public class Item {
    private boolean collectable;
    private boolean active = false;
    protected String url;
    protected ImageView image;

    public Item(){
        this.collectable = false;
    }

    public String getUrl(){
        return url;
    }

    public ImageView getImage(){
        return image;
    }

    public void setImage(ImageView image){
        this.image = image;
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
