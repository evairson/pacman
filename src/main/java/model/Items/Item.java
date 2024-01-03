package model.Items;

import javafx.scene.image.ImageView;

/**
 * Pacman peut récolter et utiliser des items 
 * Les items basiques sont les energizers et les pac-gommes qui sont activés automatiquement 
 * @see Energizer
 * @see Dot
 * Certains items sont collectables dans l'inventaire pour les utiliser plus tard
 * @see ItemBouleNeige
 * @see PacManGhost
 * 
 */

public class Item {
    private boolean collectable;
    private boolean active = false;
    public String url;
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
