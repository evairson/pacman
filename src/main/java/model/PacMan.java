package model;

import config.MazeConfig;
import geometry.IntCoordinates;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import config.Cell;

import geometry.RealCoordinates;

/**
 * Implémente PacMan comme un singleton.
 * TODO : ajouter les fonctionnalités suivantes :
 * 1. Gestion du temps d'énergie : un timer qui se décrémente à chaque tick
 *    et qui désactive l'état énergisé quand il atteint 0.
 *    (voir https://stackoverflow.com/questions/4044726/how-to-set-a-timer-in-java)
 *
 */
public final class PacMan implements Critter {

    private RealCoordinates pos;
    private Direction direction = Direction.NONE;
    private Direction nextDir = Direction.NONE;
    private final double speed = 2.;
    private boolean energized; //FIXME : remettre a false

    static final double TPINTERVAL = 0.1;

    public PacMan() {
    }

    public static final PacMan INSTANCE = new PacMan();

    // Getters/Setters
    public RealCoordinates getPos() {
        return this.pos;
    }

    public Direction getDirection() {
        return this.direction;
    }

    @Override
    public double getSpeed() {
        return isEnergized() ? 2.5 : 2.;
    }

    public void setPos(RealCoordinates pos) {
        this.pos = pos;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean isEnergized() {
        return energized;
    } 

    public void setEnergized() {
        PacMan pacman = this;
        Timer t = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                pacman.energized = false;
                Ghost.energized = false;
                t.cancel();
            }
        };
        this.energized = true;
        Ghost.energized = true;
        t.schedule(task, 10000);
    }

    //Methods
    public RealCoordinates currCellR() {
        return new RealCoordinates(Math.round((float) this.getPos().x()), Math.round((float) this.getPos().y()));
    }

    public IntCoordinates currCellI() {
        return new IntCoordinates(Math.round((float) this.pos.x()), Math.round((float) this.pos.y()));
    }


    public boolean isGoingToCenter() {
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
            default -> {
                return true;
            }
        }
    }

    @Override
    public void tpToCenter() {
        RealCoordinates currCell = this.currCellR();
        if (this.isGoingToCenter() && this.getPos().dist(currCell) < TPINTERVAL) {
            this.setPos(currCell);
//            System.out.println(this.currCellR());
        }
    }

    @Override
    public boolean isCenteredDir(Direction dir) {
        return switch (dir) {
            case EAST, WEST ->
                    Math.round(this.pos.y()) == this.pos.y(); // On peut comparer des double car ils sont censé être égaux grâce tpToCenter
            case SOUTH, NORTH -> Math.round(this.pos.x()) == this.pos.x();
            default -> true;
        };
    }

    @Override
    public boolean isCentered() {
        return (Math.round(this.getPos().x()) == this.getPos().x()) && (Math.round(this.getPos().y()) == this.getPos().y());
    }

    @Override
    public RealCoordinates getNextPos(long deltaTns, Direction dir, MazeConfig config) {
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
                        return new RealCoordinates(Math.max(nextPos.x(), Math.floor(this.getPos().x())), this.getPos().y());
                    } else {
                        return nextPos;
                    }
                case EAST :
                    if(config.getCell(this.currCellI()).eastWall()){
                        return new RealCoordinates(Math.min(nextPos.x(), Math.ceil(this.getPos().x())), this.getPos().y());
                    } else {
                        return nextPos;
                    }
                case NORTH :
                    if(config.getCell(this.currCellI()).northWall()){
                        return new RealCoordinates(this.getPos().x(), Math.max(nextPos.y(), Math.floor(this.getPos().y())));
                    } else {
                        return nextPos;
                    }
                case SOUTH :
                    if(config.getCell(this.currCellI()).southWall()){
                        return new RealCoordinates(this.getPos().x(), Math.min(nextPos.y(), Math.ceil(this.getPos().y())));
                    } else {
                        return nextPos;
                    }
                default : return this.getPos();
            }
        } else {
            return this.getPos();
        }
    }

    public boolean canSetDirection(Direction dir, MazeConfig config){
        Cell currCell = config.getCell(this.currCellI());
        return switch (dir) {
            case NORTH -> !currCell.northWall();
            case SOUTH -> !currCell.southWall();
            case EAST -> !currCell.eastWall();
            case WEST -> !currCell.westWall();
            default -> false;
        };
    }


    public void setNextDir(Direction dir){
        this.nextDir = dir;
    }
    public Direction getNextDir(){
        if(this.isCenteredDir(nextDir)){
            return nextDir;
        } else {
            return this.direction;
        }
    }
}