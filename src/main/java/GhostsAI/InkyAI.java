package GhostsAI;

import geometry.*;
import config.*;
import model.Direction;
import GhostsAI.BlinkyAI;
import GhostsAI.PinkyAI;
import model.Ghost;


import java.util.ArrayList;
import java.util.Random;

public class InkyAI { //TODO : mettre des commentaires

    public static Direction getDirection(MazeConfig config, IntCoordinates pacPos, IntCoordinates ghostPos, Direction pacDir){ //Voir commentaire dans BlinkyAI.java
        if (!Ghost.INKY.isAlive()) {
            ArrayList<IntCoordinates> path = AStar.shortestPath(ghostPos, config.getGhostHousePos(), config);
            int pathLen = path.size();
            IntCoordinates nextPos = path.get(pathLen - 1);
            return BlinkyAI.whichDir(ghostPos, nextPos);
        }else{
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
}