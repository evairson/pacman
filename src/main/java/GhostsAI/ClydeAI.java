package GhostsAI;

import geometry.IntCoordinates;
import geometry.RealCoordinates;
import model.Direction;

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

    public static Direction getDirection(MazeConfig config, IntCoordinates intC, Direction defaultDir){
        if(isInNode(config, intC)){
            return getRandomDir();
        } else {
            return defaultDir;
        }
    }
}
