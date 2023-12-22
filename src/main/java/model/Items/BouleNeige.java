package model.Items;

import config.MazeConfig;
import geometry.IntCoordinates;
import geometry.RealCoordinates;
import model.Critter;
import model.Direction;
import model.MazeState;
import model.PacMan;

public final class BouleNeige implements Critter {
    private RealCoordinates pos;
    private Direction direction;
    private final double speed = 5;
    private boolean active;
    private int warp;

    private static final double TPINTERVAL = 0.02;

    public static final BouleNeige INSTANCE = new BouleNeige();

    public boolean isActive(){
        return active;
    }

    public void lancer(){
        pos = PacMan.INSTANCE.getPos();
        direction = PacMan.INSTANCE.getDirection();
        active = true;
        warp = 0;
    }

    public void detruire(){
        active=false;
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
        double test = this.pos.dist(currCell);
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

    public RealCoordinates getNextPos(long deltaTns, Direction dir, MazeConfig config){
        if(this.isCenteredDir(dir)){
            RealCoordinates nextPos = // Calcul de la position suivante
                    getPos().plus((switch(dir){
                        case NONE -> RealCoordinates.ZERO;
                        case NORTH -> RealCoordinates.NORTH_UNIT;
                        case EAST -> RealCoordinates.EAST_UNIT;
                        case SOUTH -> RealCoordinates.SOUTH_UNIT;
                        case WEST -> RealCoordinates.WEST_UNIT;}).times(this.getSpeed() * deltaTns * 1E-9));
            switch(dir){ // Ajustement en fonction des murs, on ne veut pas dépasser un mur
                case WEST :
                    if(config.getCell(this.currCellI()).westWall()){
                        return null;
                    } else {
                        if (config.isWarp(config.getCell(this.currCellI()),dir) && Math.round(nextPos.x())<=0) {
                            if(warp>2){
                                detruire();
                            }
                            else {

                                nextPos = new RealCoordinates((config.getWidth() - 0.6),this.getPos().y()); // Warp !!!
                        
                                warp ++;
                            }
                        }
                        return nextPos;
                    }
                case EAST :
                    if(config.getCell(this.currCellI()).eastWall()){
                        return null;
                    } else {
                        if (config.isWarp(config.getCell(this.currCellI()),dir)  && Math.round(nextPos.x())>= config.getWidth()) {
                            if(warp>2){
                                detruire();
                            }
                            else {

                                nextPos = new RealCoordinates((TPINTERVAL),this.getPos().y());

                                warp++;
                            }
                        }
                        return nextPos;
                    }
                case NORTH :
                    if(config.getCell(this.currCellI()).northWall()){
                        return null;
                    } else {
                        if (config.isWarp(config.getCell(this.currCellI()),dir) && Math.round(nextPos.y())<=0) {
                            if(warp>2){
                                detruire();
                            }
                            else {
                                
                                nextPos = new RealCoordinates(this.getPos().x(),(config.getHeight() - 0.6));
                                warp++;
                            }
                           }
                        return nextPos;
                    }
                case SOUTH :
                    if(config.getCell(this.currCellI()).southWall()){
                        return null;
                    } else {
                        if (config.isWarp(config.getCell(this.currCellI()),dir) && Math.round(nextPos.y())>=config.getHeight()) {
                            if(warp>2){
                                detruire();
                            }
                            else {
                                nextPos = new RealCoordinates(this.getPos().x(),TPINTERVAL);
                                warp++;
                            }
                        }
                        return nextPos;
                    }
                default : return this.pos;
            }
        } else {
            return this.pos;
        }
    }
}
