package model;

/**
 * Pas grand chose à détailler, ça définit simplement les 4 fantômes.
 * CEPENDANT, il faut implémenter un comportement différent pour chaque fantôme.
 * Chaque fantome devrait avoir sa propre classe ou méthode qui implémente sa logique de déplacement.
 * une classe BlinkyAI, InkyAI, PinkyAI, ClydeAI qui implémentent une interface GhostAI ???
 * Utiliser les données du jeu pour déterminer la direction du fantôme. (la position de PacMan par exemple)
 *
 */

import config.MazeConfig;
import geometry.IntCoordinates;
import geometry.RealCoordinates;

public enum Ghost implements Critter {

    // TODO: implement a different AI for each ghost, according to the description in Wikipedia's page
    BLINKY, INKY, PINKY, CLYDE;

    private RealCoordinates pos;
    private Direction direction;
    private Direction lastDirection;
    private double speed;
    private boolean energized;

    private static final double EPSILON = 0.1;

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
        return this.speed;
    }

    @Override
    public void setPos(RealCoordinates pos) { this.pos = pos; }

    @Override
    public void setDirection(Direction direction) { this.direction = direction; }

    //Methods
    @Override
    public void tpToCenter(MazeConfig config){
        if(this.pos.isInNode(config)){
            RealCoordinates node = this.currNode(config);
            if(this.isGoingToNode(node) && this.getPos().dist(node) < EPSILON){
                this.setPos(node);
                this.direction = Direction.NONE;
            }
        }
    }

    @Override
    public RealCoordinates currNode(MazeConfig config){ // Renvoie les coordonnées du noeud sur lequel on est.
        if(this.pos.isInNode(config)){
            return new RealCoordinates(Math.round((float) this.getPos().x()), Math.round((float) this.getPos().y()));
        } else {
            return new RealCoordinates(0, 0);
        }
    }

    @Override
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

    @Override
    public boolean isCentered(Direction dir){ // Vérifie qu'on est centré sur la coordonnée sur laquelle on ne se déplace pas.
        return switch (dir) {
            case EAST, WEST ->
                    Math.floor(this.getPos().y()) == this.getPos().y(); // On peut comparer des double car ils sont censé être égaux avec tpToCenter
            case SOUTH, NORTH -> Math.floor(this.getPos().x()) == this.getPos().x();
            default -> true;
        };
    }

    @Override
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