package GhostsAI;

import config.Cell;
import geometry.AStar;
import geometry.IntCoordinates;
import geometry.RealCoordinates;
import model.Direction;
import model.Ghost;

import java.util.ArrayList;
import java.util.Random;

import config.MazeConfig;

public class ClydeAI{

    public static Direction getRandomDir(){ // Renvoie une direction au hasard.
        Random rd = new Random();
        int n = rd.nextInt(4);
        return switch (n) {
            case 0 -> Direction.NORTH;
            case 1 -> Direction.SOUTH;
            case 2 -> Direction.EAST;
            case 3 -> Direction.WEST;
            default -> Direction.NONE;
        };
    }

    public static boolean isInNode(MazeConfig config, IntCoordinates intC){ // Vérifie que la case courante est une intersection (càd pas une pipe)
        return !config.getCell(intC).isPipe();
    }

    public static Direction getDirection(MazeConfig config, IntCoordinates intC, Direction defaultDir,IntCoordinates ghostPos) { //Voir commentaire dans BlinkyAI.java
        if (!Ghost.CLYDE.isAlive()) {
            if (ghostPos.equals(new IntCoordinates(config.getGhostHousePos().x(),config.getGhostHousePos().y()))){
                Ghost.CLYDE.setSpeed(Ghost.CLYDE.getSpeed()/1.5);
                Ghost.CLYDE.setIsAlive(true);
                return Direction.NORTH;
            }else{
                ArrayList<IntCoordinates> path = AStar.shortestPath(ghostPos, new IntCoordinates(config.getGhostHousePos().x(),config.getGhostHousePos().y()), config);
                int pathlen = path.size();
                IntCoordinates nextPos = path.get(pathlen-1);
                return BlinkyAI.whichDir(ghostPos, nextPos);
            }
        } else {
            if (isInNode(config, intC)) {
                return getRandomDir();
            } else {
                return defaultDir;
            }
        }
    }
}
