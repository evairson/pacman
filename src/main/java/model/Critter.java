package model;

/**
 * Interface Java sealed pour implémenter Critter et ses sous-classes PacMan et Ghost
 * @see Ghost
 * @see PacMan
 *
 */

import geometry.IntCoordinates;
import geometry.RealCoordinates;
import config.MazeConfig;

public interface Critter {

    //Getters/Setters
    RealCoordinates getPos();

    Direction getDirection();

    double getSpeed();

    void setPos(RealCoordinates realCoordinates);

    void setDirection(Direction direction);

    //Methods

    /**
     * @param deltaTNanoSeconds time since the last update in nanoseconds
     * @return the next position if there is no wall
     */
    default RealCoordinates nextPos(long deltaTNanoSeconds) {
        return getPos().plus((switch (getDirection()) {
            case NONE -> RealCoordinates.ZERO;
            case NORTH -> RealCoordinates.NORTH_UNIT;
            case EAST -> RealCoordinates.EAST_UNIT;
            case SOUTH -> RealCoordinates.SOUTH_UNIT;
            case WEST -> RealCoordinates.WEST_UNIT;
        }).times(getSpeed() * deltaTNanoSeconds * 1E-9));
    }

    RealCoordinates currCellR(); // Renvoie les coordonnées REELLES de la cellule sur laquelle le critter est.

    IntCoordinates currCellI(); // Renvoie les coordonnées ENTIERES de la cellule sur la laquelle le critter est.

    boolean isGoingToCenter(); // Renvoie true si le critter se dirige vers le centre de la cellule.

    void tpToCenter(); // Téléporte le critter au centre de la cellule s'il est suffisament proche.

    boolean isCenteredDir(Direction dir); // Vérifie que le critter est centré sur l'axe sur lequel on ne se déplace pas.

    boolean isCentered(); // Vérifie que le critter est au centre de la cellule.

    RealCoordinates getNextPos(long deltaTns, Direction dir, MazeConfig config); // Calcule la position atteinte si le critter va dans la direction dir.

    Direction getNextDir();
}