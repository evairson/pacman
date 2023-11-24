package config;

import model.Items.Item;

// La classe Cell est déclarée comme un enregistrement (record) qui est une nouveauté de Java 14.

// tutur : ici essentiellement on a la liste des cellules :  le nom donne la forme et le Item donne ce qui se trouve dedans (soit un
// point, soit un energizer (le super boost), soit du vide)

public record Cell(boolean northWall, boolean eastWall, boolean southWall, boolean westWall, Item initialItem) {


    /** TO DO : Modification avec une suele méthode de création 'create" qui prend comme paramètres les valeurs des murs dans chaque direction/
     * ainsi que le contenu initial de la cellule. Les autres méthodes de création sont supprimées.
     *
     */

    // open cells
    public static Cell open(Item c) { return new Cell(false, false, false, false, c); }
    public static Cell closed(Item c) { return new Cell(true, true, true, true, c); }
    // straight pipes
    public static Cell hPipe(Item c) { return new Cell(true, false, true, false, c); }
    public static Cell vPipe(Item c) { return new Cell(false, true, false, true, c); }
    // corner cells
    public static Cell swVee(Item c) { return new Cell(true, true, false, false, c); }
    public static Cell nwVee(Item c) { return new Cell(false, true, true, false, c); }
    public static Cell neVee(Item c) { return new Cell(false, false, true, true, c); }
    public static Cell seVee(Item c) { return new Cell(true, false, false, true, c); }
    // T-shaped cells
    public static Cell nU(Item c) { return new Cell(false, true, true, true, c); }
    public static Cell eU(Item c) { return new Cell(true, false, true, true, c); }
    public static Cell sU(Item c) { return new Cell(true, true, false, true, c); }
    public static Cell wU(Item c) { return new Cell(true, true, true, false, c); }
    // U-shaped cells
    public static Cell nTee(Item c) { return new Cell(true, false, false, false, c); }
    public static Cell eTee(Item c) { return new Cell(false, true, false, false, c); }
    public static Cell sTee(Item c) { return new Cell(false, false, true, false, c); }
    public static Cell wTee(Item c) { return new Cell(false, false, false, true, c); }
    public static Cell create(boolean northWall, boolean eastWall, boolean southWall, boolean westWall, Item initialItem) {
        return new Cell(northWall,eastWall,southWall,westWall,initialItem);
    }

    public boolean isPipe(){
        return (northWall && southWall && !eastWall && !westWall) ||
                (eastWall && westWall && !northWall && !southWall);
    }
}
