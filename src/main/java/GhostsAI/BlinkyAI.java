package GhostsAI;

import geometry.*;
import model.Direction;
import config.MazeConfig;
import java.util.ArrayList;
import model.Ghost;

public class BlinkyAI {

    public static Direction whichDir(IntCoordinates ghostPos, IntCoordinates nextPos){
        int gx = ghostPos.x();
        int gy = ghostPos.y();
        int nx = nextPos.x();
        int ny = nextPos.y();
        if (gx == nx) {
            if (gy < ny) { return Direction.SOUTH; }
            else { return Direction.NORTH; }
        }
        else if (gy == ny) {
            if (gx < nx) { return Direction.EAST; }
            else { return Direction.WEST; }
        }
        else { return Direction.NONE; }
    }

    //Fonction classique commune à toutes les IA : getDirection
    public static Direction getDirection(MazeConfig config, IntCoordinates pacPos, IntCoordinates ghostPos){
        if (!Ghost.BLINKY.isAlive()) {
            if (ghostPos.equals(config.getGhostHousePos())) {
                return Direction.NONE;
            }else{
                ArrayList<IntCoordinates> path = AStar.shortestPath(ghostPos, config.getGhostHousePos(), config);
                int pathlen = path.size();
                IntCoordinates nextPos = path.get(pathlen-1);
                System.out.println(nextPos);
                return whichDir(ghostPos, nextPos);
            }
        }else{
            ArrayList<IntCoordinates> path = AStar.shortestPath(ghostPos, pacPos, config);
            int pathLen = path.size();
            IntCoordinates nextPos = path.get(pathLen-1);
            return whichDir(ghostPos, nextPos);
        }
    }

}
