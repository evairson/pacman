package geometry;

/**
 * Représente un point dans le plan avec des coordonnées entières, utilisé pour la position des fantômes.
 */
public record IntCoordinates(int x, int y) {
    public RealCoordinates toRealCoordinates(double scale) {
        return new RealCoordinates(x * scale, y * scale);
    }

    public boolean equals(IntCoordinates coordinates){
        return (this.x == coordinates.x) && (this.y == coordinates.y);
    }
}


