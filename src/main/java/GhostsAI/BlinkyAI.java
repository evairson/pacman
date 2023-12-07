package GhostsAI;

import geometry.*;
import model.Direction;
import config.MazeConfig;
import java.util.ArrayList;
import model.Ghost;
import config.*;

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
            if (Ghost.BLINKY.getAlreadyArrivedAtHome()){
                if (ghostPos.equals(new IntCoordinates(config.getGhostHousePos().x(),config.getGhostHousePos().y()-1))){ //Si Blinky est déjà passé par la maison et qu'il se trouve au dessus de la maison des fantomes alors il peut revenir en vie
                    Ghost.BLINKY.setIsAlive(true);
                    Ghost.BLINKY.setAlreadyArrivedAtHome(false);
                    Ghost.BLINKY.setSpeed(Ghost.BLINKY.getSpeed()/1.5);
                    return getDirection(config,pacPos,ghostPos);
                }else{ //S'il n'y est pas encore arrivé alors il continu de "traquer" la maison des fantomes
                    ArrayList<IntCoordinates> path = AStar.shortestPath(ghostPos, new IntCoordinates(config.getGhostHousePos().x(),config.getGhostHousePos().y()-1), config);
                    int pathlen = path.size();
                    IntCoordinates nextPos = path.get(pathlen-1);
                    return whichDir(ghostPos, nextPos);

                }
            }
            if (ghostPos.equals(config.getGhostHousePos())) {
                Ghost.BLINKY.setAlreadyArrivedAtHome(true);
                return Direction.NORTH; //Permet à Blinky de sortir de la maison des fantomes, il ne faut pas le ramener à la vie sinon il sera coincé car les murs seront réactivés
            } else if (ghostPos.equals(new IntCoordinates(config.getGhostHousePos().x(),config.getGhostHousePos().y()-1))) {
                return Direction.SOUTH; //Permet à Blinky de rentrer dans la maison des fantomes

            } else{
                ArrayList<IntCoordinates> path = AStar.shortestPath(ghostPos, new IntCoordinates(config.getGhostHousePos().x(),config.getGhostHousePos().y()-1), config); //Blinky traque la case au dessus de la maison des fantomes car la méthode AStar.shortestPath ne peut pas faire le plus court chemin d'une case inaccessible
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
