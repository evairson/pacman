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
    public RealCoordinates getPos();

    public Direction getDirection();

    public double getSpeed();

    public void setPos(RealCoordinates realCoordinates);

    public void setDirection(Direction direction);

    //Methods

    /**
     * @param deltaTNanoSeconds time since the last update in nanoseconds
     * @return the next position if there is no wall
     */
    public default RealCoordinates nextPos(long deltaTNanoSeconds) {
        return getPos().plus((switch (getDirection()) {
            case NONE -> RealCoordinates.ZERO;
            case NORTH -> RealCoordinates.NORTH_UNIT;
            case EAST -> RealCoordinates.EAST_UNIT;
            case SOUTH -> RealCoordinates.SOUTH_UNIT;
            case WEST -> RealCoordinates.WEST_UNIT;
        }).times(getSpeed()*deltaTNanoSeconds * 1E-9));
    }

    public RealCoordinates currNode(MazeConfig config);

    public boolean isGoingToNode(RealCoordinates node);

    public void tpToCenter(MazeConfig config);

    public boolean isCentered(Direction dir);

    public boolean checkLegalDir(MazeConfig config, Direction dir);
}
