package config;

import model.Items.Item;

// La classe Cell est déclarée comme un enregistrement (record) qui est une nouveauté de Java 14.

public record Cell(boolean northWall, boolean eastWall, boolean southWall, boolean westWall, Item initialItem) {


    public static Cell create(boolean northWall, boolean eastWall, boolean southWall, boolean westWall, Item initialItem) {
        return new Cell(northWall,eastWall,southWall,westWall,initialItem);

    }

    public boolean isPipe(){
        return (northWall && southWall && !eastWall && !westWall) ||
                (eastWall && westWall && !northWall && !southWall);
    }

}
