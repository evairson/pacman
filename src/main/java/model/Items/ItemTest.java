package model.Items;

public class ItemTest extends Item{

    public static boolean active;
    public static int frameActivity;
    private static final boolean collectable = true;

    public static void setActive(boolean b){
        active = b;
        frameActivity = 0;
    }
}
