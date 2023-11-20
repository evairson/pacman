package GhostsAI;

import geometry.*;
import model.Direction;
import config.MazeConfig;
import java.util.ArrayList;
import GhostsAI.BlinkyAI;
import model.Ghost;

public class



RunAwayAI {

    public static ArrayList<IntCoordinates> voisins_les_plus_loins(IntCoordinates PacmanPos , IntCoordinates GhostPos 
    , MazeConfig config ){//on choisit parmi les 4 cases voisines du ghost , celles qui sont les plus éloignées du pacman 

        ArrayList<IntCoordinates> voisins_plus_loins = new ArrayList<>();
        
        Noeud Pacman_noeud = new Noeud(PacmanPos,null);
        Noeud noeud_actuel = new Noeud(GhostPos,null);

        ArrayList<Noeud> voisins_noeud_actuel=noeud_actuel.getVoisins(config);

        int max = Integer.MIN_VALUE;
        for (Noeud noeud : voisins_noeud_actuel){
            if (noeud.manhattanDistance(Pacman_noeud)>max){
                max = noeud.manhattanDistance(Pacman_noeud);
            }
        }

        for (Noeud noeud : voisins_noeud_actuel){
            if (noeud.manhattanDistance(Pacman_noeud)==max){
                voisins_plus_loins.add(noeud.getCoordinates());
            }
        }

        return voisins_plus_loins ; 
    }

    public static ArrayList<IntCoordinates> voisins_plus_proches_des_plus_loins(IntCoordinates PacmanPos , 
    IntCoordinates GhostPos , MazeConfig config){
        //parmi les cases les plus éloignées du pacman ,on choisit celles qui sont les plus proches du ghost

        ArrayList<IntCoordinates> voisins_plus_loins=voisins_les_plus_loins(PacmanPos, GhostPos, config);

        int min = Integer.MAX_VALUE;
        
        ArrayList<IntCoordinates> voisins_plus_proches = new ArrayList<IntCoordinates>();

        for (IntCoordinates voisin : voisins_plus_loins){

            ArrayList<IntCoordinates> chemin = AStar.shortestPath(GhostPos,voisin,config);

            int longu_chemin = chemin.size();

            if (longu_chemin < min  ){
                min = longu_chemin;
                voisins_plus_proches=chemin;

            }

        }
        return voisins_plus_proches ;
    }

    public static Direction getDirection(MazeConfig config, IntCoordinates pacPos, IntCoordinates ghostPos){
        //on choisit la direction adéquate selon la position à suivre

        ArrayList<IntCoordinates> path = voisins_plus_proches_des_plus_loins(pacPos, ghostPos, config);
        int pathLen = path.size();
        IntCoordinates nextPos = path.get(pathLen-1);
        return BlinkyAI.whichDir(ghostPos, nextPos);
    }

    
}
