package GhostsAI;

import geometry.*;
import config.*;
import model.Direction;

import java.util.Random;

public class InkyAI {

    public static Direction getDirection(MazeConfig config, IntCoordinates pacPos, IntCoordinates ghostPos, Direction pacDir){
        Random rd = new Random();
        int n = rd.nextInt(4);
        if (n == 0) {
            Direction tempDir = BlinkyAI.getDirection(config, pacPos, ghostPos);
            switch(tempDir){
                case NORTH -> { return Direction.SOUTH; }
                case SOUTH -> { return Direction.NORTH; }
                case WEST -> { return Direction.EAST; }
                case EAST -> { return Direction.WEST; }
                default -> { return Direction.NONE; }
            }
        }
        else { return PinkyAI.getDirection(config, pacPos, ghostPos, pacDir); }
    }
}