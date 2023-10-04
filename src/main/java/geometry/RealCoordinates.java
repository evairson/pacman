package geometry;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Représente un point dans le plan avec des coordonnées réelles, utilisé pour la position du joueur.
 *
 */
public record RealCoordinates(double x, double y) {

    //  Constantes pour représenter des coordonnées réelles spécifiques (les points cardinaux globalement et l'origine)
    public static final RealCoordinates ZERO = new RealCoordinates(0, 0);
    public static final RealCoordinates NORTH_UNIT = new RealCoordinates(0, -1);
    public static final RealCoordinates EAST_UNIT = new RealCoordinates(1, 0);
    public static final RealCoordinates SOUTH_UNIT = new RealCoordinates(0, 1);
    public static final RealCoordinates WEST_UNIT = new RealCoordinates(-1, 0);

    // Deux méthodes pour ajouter et multiplier des coordonnées réelles
    public RealCoordinates plus(RealCoordinates other) {
        return new RealCoordinates(x + other.x, y + other.y);
    }

    public RealCoordinates times(double multiplier) {
        return new RealCoordinates(x * multiplier, y * multiplier);
    }

    /**
     * @return the coordinates of all integer squares that a unit square with current coordinates would intersect
     */
    public Set<IntCoordinates> intNeighbours() {
        return new HashSet<>(List.of(
                new IntCoordinates((int) Math.floor(x), (int) Math.floor(y)),
                new IntCoordinates((int) Math.floor(x), (int) Math.ceil(y)),
                new IntCoordinates((int) Math.ceil(x), (int) Math.floor(y)),
                new IntCoordinates((int) Math.ceil(x), (int) Math.ceil(y))
        )
        );
    }

    // Méthode pour arrondir les coordonnées réelles à des coordonnées entières
    public IntCoordinates round() {
        return new IntCoordinates((int) Math.round(x), (int) Math.round(y));
    }

    // Méthodes pour arrondir les coordonnées réelles avec des parties fractionnaires arrondies.
    public RealCoordinates floorX() {
        return new RealCoordinates((int) Math.floor(x), y);
    }

    public RealCoordinates floorY() {
        return new RealCoordinates(x, (int) Math.floor(y));
    }

    public RealCoordinates ceilX() {
        return new RealCoordinates((int) Math.ceil(x), y);
    }

    public RealCoordinates ceilY() {
        return new RealCoordinates(x, (int) Math.ceil(y));
    }

    // Méthode pour calculer la distance entre deux coordonnées réelles
    public RealCoordinates warp(int width, int height) {
        double rx = (x + width) % width;
        double ry = (y + height) % height;
        return new RealCoordinates(rx, ry);
    }
}
