package GhostsAI;

import geometry.*;
import config.*;
import model.Direction;
import model.Ghost;

import java.util.ArrayList;


public class PinkyAI {
    public static IntCoordinates getFrontCell(IntCoordinates coordinates, Direction dir, MazeConfig config){
        Cell currCell = config.getCell(coordinates);
        switch(dir){
            case NORTH :
                if(!currCell.northWall() && (coordinates.y() > 0)){
                    if(!(config.getCell(new IntCoordinates(coordinates.x(), coordinates.y() - 1)).northWall()) && (coordinates.y() - 1 > 0)){
                        return new IntCoordinates(coordinates.x(), coordinates.y() - 2);
                    } else {
                        return new IntCoordinates(coordinates.x(), coordinates.y() - 1);
                    }
                } else {
                    return coordinates;
                }
            case SOUTH :
                if(!currCell.southWall() && (coordinates.y() < config.getHeight() - 1)){
                    if(!(config.getCell(new IntCoordinates(coordinates.x(), coordinates.y() + 1)).southWall()) && (coordinates.y() + 1 < config.getHeight() - 1)){
                        return new IntCoordinates(coordinates.x(), coordinates.y() + 2);
                    } else {
                        return new IntCoordinates(coordinates.x(), coordinates.y() + 1);
                    }
                } else {
                    return coordinates;
                }
            case EAST :
                if(!currCell.eastWall() && (coordinates.x() < config.getWidth() - 1)){
                    if(!(config.getCell(new IntCoordinates(coordinates.x() + 1, coordinates.y())).eastWall()) && (coordinates.x() + 1 < config.getWidth() - 1)){
                        return new IntCoordinates(coordinates.x() + 2, coordinates.y());
                    } else {
                        return new IntCoordinates(coordinates.x() + 1, coordinates.y());
                    }
                } else {
                    return coordinates;
                }
            case WEST :
                if(!currCell.westWall() && (coordinates.x() > 0)){
                    if(!(config.getCell(new IntCoordinates(coordinates.x() - 1, coordinates.y())).westWall()) && (coordinates.x() - 1 > 0)){
                        return new IntCoordinates(coordinates.x() - 2, coordinates.y());
                    } else {
                        return new IntCoordinates(coordinates.x() - 1, coordinates.y());
                    }
                } else {
                    return coordinates;
                }
            default : return coordinates;
        }
    }

    public static Direction getDirection(MazeConfig config, IntCoordinates pacPos, IntCoordinates ghostPos, Direction pacDir){ //Voir commentaire dans BlinkyAI.java
        if (!Ghost.PINKY.isAlive() && ghostPos.equals(Ghost.PINKY.toIntCoordinates())) {
            if (ghostPos.equals(config.getGhostHousePos())){
                Ghost.PINKY.setSpeed(Ghost.PINKY.getSpeed()/2);
                Ghost.PINKY.setIsAlive(true);
                return Direction.NORTH;
            }else{
                ArrayList<IntCoordinates> path = AStar.shortestPath(ghostPos,config.getGhostHousePos(), config);
                int pathlen = path.size();
                IntCoordinates nextPos = path.get(pathlen-1);
                return BlinkyAI.whichDir(ghostPos, nextPos);
            }
        }else {
            IntCoordinates targetPos = getFrontCell(pacPos, pacDir, config);
            if (((Math.abs(pacPos.x() - ghostPos.x()) <= 2) && (pacPos.y() == ghostPos.y())) || ((Math.abs(pacPos.y() - ghostPos.y()) <= 2) && (pacPos.x() == ghostPos.x()))) {
                return BlinkyAI.getDirection(config, pacPos, ghostPos);
            } else {
                return BlinkyAI.getDirection(config, targetPos, ghostPos);
            }
        }
    }
}
