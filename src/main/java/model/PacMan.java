package model;

import config.MazeConfig;
import geometry.IntCoordinates;
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
    private Direction direction;
    private Direction lastDirection;
    private double speed;
    private boolean energized;

    static final double EPSILON = 0.1;

    private PacMan() {
    }

    public static final PacMan INSTANCE = new PacMan();

    // Getters/Setters
    public RealCoordinates getPos(){
        return this.pos;
    }

    public Direction getDirection(){
        return this.direction;
    }

    public double getSpeed(){
        return this.speed;
    }

    public void setPos(RealCoordinates pos) { this.pos = pos; }

    public void setDirection(Direction direction) { this.direction = direction; }

    public boolean isEnergized() { return energized; } //TODO : handle timeout !

    public void setEnergized(boolean energized) {
        this.energized = energized;
    }

    //Methods
    public void tpToCenter(MazeConfig config){
        if(this.pos.isInNode(config)){
            RealCoordinates node = this.currNode(config);
            if(this.isGoingToNode(node) && this.getPos().dist(node) < EPSILON){
                this.setPos(node);
                this.direction = Direction.NONE;
            }
        }
    }

    public RealCoordinates currNode(MazeConfig config){ // Renvoie les coordonnées du noeud sur lequel on est.
        if(this.pos.isInNode(config)){
            return new RealCoordinates(Math.round((float) this.getPos().x()), Math.round((float) this.getPos().y()));
        } else {
            return new RealCoordinates(0, 0);
        }
    }

    public boolean isGoingToNode(RealCoordinates node){ // Renvoie true si le critter se dirgige vers le centre du noeud sur lequel il est.
        switch (this.direction) {
            case NORTH -> {
                return this.pos.y() >= node.y();
            }
            case SOUTH -> {
                return this.pos.y() <= node.y();
            }
            case EAST -> {
                return this.pos.x() >= node.x();
            }
            case WEST -> {
                return this.pos.x() <= node.x();
            }
            default -> { return true; }
        }
    }

    public boolean isCentered(Direction dir){ // Vérifie qu'on est centré sur la coordonnée sur laquelle on ne se déplace pas.
        return switch (dir) {
            case EAST, WEST ->
                    Math.floor(this.getPos().y()) == this.getPos().y(); // On peut comparer des double car ils sont censé être égaux avec tpToCenter
            case SOUTH, NORTH -> Math.floor(this.getPos().x()) == this.getPos().x();
            default -> true;
        };
    }

    public boolean checkLegalDir(MazeConfig config, Direction dir){ // Vérifie qu'on est centré et qu'il n'y a pas de mur dans la direction.
        if(this.isCentered(dir)){
            return switch (dir) {
                case WEST -> config.getCell(new IntCoordinates((int) Math.floor(this.getPos().x()), (int) Math.floor(this.getPos().y()))).westWall();
                case EAST -> config.getCell(new IntCoordinates((int) Math.floor(this.getPos().x()), (int) Math.floor(this.getPos().y()))).eastWall();
                case NORTH -> config.getCell(new IntCoordinates((int) Math.floor(this.getPos().x()), (int) Math.floor(this.getPos().y()))).northWall();
                case SOUTH -> config.getCell(new IntCoordinates((int) Math.floor(this.getPos().x()), (int) Math.floor(this.getPos().y()))).southWall();
                default -> true;
            };
        } else {
            return false;
        }
    }

}
