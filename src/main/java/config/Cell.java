package config;

// La classe Cell est déclarée comme un enregistrement (record) qui est une nouveauté de Java 14.

// tutur : ici essentiellement on a la liste des cellules :  le nom donne la forme et le content donne ce qui se trouve dedans (soit un
// point, soit un energizer (le super boost), soit du vide)

public record

Cell(boolean northWall, boolean eastWall, boolean southWall, boolean westWall, Cell.Content initialContent) {

    //Le contenu des cells
    public enum Content { NOTHING, DOT, ENERGIZER }

    //Fonction pour créer des murs 
    public static Cell create(boolean northWall, boolean eastWall, boolean southWall, boolean westWall, Content initialContent) {
        return new Cell(northWall,eastWall,southWall,westWall,initialContent);
    }

    public boolean isPipe(){
        return (northWall && southWall && !eastWall && !westWall) ||
                (eastWall && westWall && !northWall && !southWall);
    }
}
