package model;

import java.util.Random;

/**
 * Définit les 4 fantômes.
 * Un comportement différent pour chaque fantôme.
 * Utiliser les données du jeu pour déterminer la direction du fantôme. (la position de PacMan par exemple)
 *
 */

import GhostsAI.*;
import config.MazeConfig;
import geometry.IntCoordinates;
import geometry.RealCoordinates;
import  java.util.Random;

public enum Ghost implements Critter {

    BLINKY, INKY, PINKY, CLYDE;

    private RealCoordinates pos;
    private Direction direction;
    private final double speed = 1.3;
    private boolean energized;

    private static final double TPINTERVAL = 0.02;

    public boolean allEnergergized(){
        return BLINKY.energized && INKY.energized && PINKY.energized && CLYDE.energized;
    }
    public static void setAllEnergizedValue(boolean value){
        BLINKY.energized = value;
        INKY.energized = value;
        PINKY.energized = value;
        CLYDE.energized = value;
    }

    // Getters/Setters
    @Override
    public RealCoordinates getPos(){
        return this.pos;
    }

    @Override
    public Direction getDirection(){
        return this.direction;
    }

    @Override
    public double getSpeed(){
        if(PacMan.INSTANCE.isEnergized()){
            return this.speed * 1.5;
        } else {
            return this.speed;
        }
    }

    public boolean isEnergized() {
        return energized;
    }

    @Override
    public void setPos(RealCoordinates pos) { this.pos = pos; }

    @Override
    public void setDirection(Direction direction) { this.direction = direction; }

    //Methods
    @Override
    public RealCoordinates currCellR(){
        return new RealCoordinates(Math.round((float) this.pos.x()), Math.round((float) this.pos.y()));
    }

    @Override
    public IntCoordinates currCellI(){
        return new IntCoordinates(Math.round((float) this.pos.x()), Math.round((float) this.pos.y()));
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

    public boolean isFakeEnergized(){
        return false;
    }

    public boolean isCentered(){
        return (Math.round(this.pos.x()) == this.pos.x()) && (Math.round(this.pos.y()) == this.pos.y());
    }

    public static Direction getRandomDirection(){
        Random rd = new Random();
        int dir = rd.nextInt(4);
        return switch (dir) {
            case 0 -> Direction.SOUTH;
            case 1 -> Direction.NORTH;
            case 2 -> Direction.EAST;
            default -> Direction.WEST;
        };
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
                        return new RealCoordinates(Math.max(nextPos.x(), Math.floor(this.pos.x())), this.pos.y());
                    } else {
                        return nextPos;
                    }
                case EAST :
                    if(config.getCell(this.currCellI()).eastWall()){
                        return new RealCoordinates(Math.min(nextPos.x(), Math.ceil(this.pos.x())), this.pos.y());
                    } else {
                        return nextPos;
                    }
                case NORTH :
                    if(config.getCell(this.currCellI()).northWall()){
                        return new RealCoordinates(this.pos.x(), Math.max(nextPos.y(), Math.floor(this.pos.y())));
                    } else {
                        return nextPos;
                    }
                case SOUTH :
                    if(config.getCell(this.currCellI()).southWall()){
                        return new RealCoordinates(this.pos.x(), Math.min(nextPos.y(), Math.ceil(this.pos.y())));
                    } else {
                        return nextPos;
                    }
                default : return this.pos;
            }
        } else {
            return this.pos;
        }
    }

    public Direction getNextDir(MazeConfig config, IntCoordinates pacPos, Direction pacDir, Boolean energized, boolean fakeEnergized){
        if (fakeEnergized){
            if (this.isCentered()){
                return getRandomDirection();
            }
            else{
                return this.direction;
            }
        } else if (energized){
            if (this.isCentered()) {
                return RunAwayAI.getDirection(config, pacPos, this.currCellI());
            } else {
                return this.direction;
            }
        } else {
            switch (this) {
                case CLYDE:
                    if (this.isCentered()) {
                        return ClydeAI.getDirection(config, this.currCellI(), this.direction);
                    } else {
                        return this.direction;
                    }
                case BLINKY:
                    if (this.isCentered()) {
                        return BlinkyAI.getDirection(config, pacPos, this.currCellI());
                    } else {
                        return this.direction;
                    }
                case PINKY:
                    if (this.isCentered()) {
                        return PinkyAI.getDirection(config, pacPos, this.currCellI(), pacDir);
                    } else {
                        return this.direction;
                    }
                case INKY:
                    if (this.isCentered()) {
                        return InkyAI.getDirection(config, pacPos, this.currCellI(), pacDir);
                    } else {
                        return this.direction;
                    }
                default:
                    return Direction.NONE;
            }
        }
    }
}