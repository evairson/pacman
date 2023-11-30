package model.Items;

import config.MazeConfig;
import geometry.IntCoordinates;
import geometry.RealCoordinates;
import model.Critter;
import model.Direction;
import model.MazeState;

public class BouleNeige implements Critter {
    private RealCoordinates pos;
    private Direction direction;
    private final double speed = 2;

    private static final double TPINTERVAL = 0.02;

    BouleNeige(RealCoordinates pos, Direction direction){
        this.pos = pos;
        this.direction = direction;
        MazeState.addCritter(this);
    }

    public Direction getDirection(){
        return direction;
    }

    public boolean isCentered(){
        return (Math.round(this.pos.x()) == this.pos.x()) && (Math.round(this.pos.y()) == this.pos.y());
    }

    @Override
    public boolean isGoingToCenter(){
        RealCoordinates center = this.currCellR();
        switch (this.direction) {
            case NORTH -> {
                return this.pos.y() > center.y();
            }
            case SOUTH -> {
                return this.pos.y() < center.y();
            }
            case EAST -> {
                return this.pos.x() < center.x();
            }
            case WEST -> {
                return this.pos.x() > center.x();
            }
            default -> { return true; }
        }
    }
    @Override
    public void tpToCenter(){
        RealCoordinates currCell = this.currCellR();
        if(this.isGoingToCenter() && this.pos.dist(currCell) < TPINTERVAL) {
            this.setPos(currCell);
        }
    }

    @Override
    public boolean isCenteredDir(Direction dir){
        return switch (dir) {
            case EAST, WEST ->
                    Math.round(this.pos.y()) == this.pos.y(); // On peut comparer des double car ils sont censé être égaux grâce tpToCenter
            case SOUTH, NORTH -> Math.round(this.pos.x()) == this.pos.x();
            default -> true;
        };
    }

    @Override
    public RealCoordinates currCellR(){
        return new RealCoordinates(Math.round((float) this.pos.x()), Math.round((float) this.pos.y()));
    }

    @Override
    public IntCoordinates currCellI(){
        return new IntCoordinates(Math.round((float) this.pos.x()), Math.round((float) this.pos.y()));
    }

    public boolean isFakeEnergized(){
        return false;
    }

    public void setDirection(Direction d){
        this.direction = d;
    }

    public void setPos(RealCoordinates pos){
        this.pos= pos;
    }

    public double getSpeed(){
        return speed;
    }

    public RealCoordinates getPos(){
        return pos;
    }

    public boolean isEnergized(){
        return false;
    }

    public RealCoordinates getNextPos(long l, Direction d, MazeConfig m){
        return null;
    }


}
