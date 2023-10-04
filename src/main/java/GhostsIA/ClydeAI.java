package GhostsIA;

import model.Direction;
import geometry.RealCoordinates;
import model.Critter;
import java.util.Random;
import model.Ghost;

public class ClydeAI {

    public static boolean checkLegalDir(Critter critter, Direction dir){
        switch(dir){
            case SOUTH, NORTH: return critter.getPos().isIntCoordinate(Direction.WEST);
            case EAST, WEST: return critter.getPos().isIntCoordinate(Direction.NORTH);
            default : return false;
        }
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

    public static Direction getDirection(){
        Direction dir = getRandomDirection();
        if(checkLegalDir(Ghost.CLYDE, dir)){
            return dir;
        } else {
            return Direction.NONE;
        }
    }
}
