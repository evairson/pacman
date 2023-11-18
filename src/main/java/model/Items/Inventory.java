package model.Items;

public class Inventory {
    private static final int size = 8;
    private Item[] content = new Item[size];
    private int freeSlots;

    public Inventory(){
        this.freeSlots = size;
    }

    public void add(Item item){
        if(freeSlots > 0 && item.isCollectable()){
            for(int i = 0; i < size; i++){
                if(content[i] == null){
                    content[i] = item;
                    break;
                }
            }
        }
    }

    public void remove(int index){
        content[index] = null;
    }
}
