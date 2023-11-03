package GhostsAI;

import model.Critter;
import model.Ghost;
import geometry.*;
import config.*;
import model.MazeState;
import model.Direction;
import GhostsAI.BlinkyAI;
import java.util.ArrayList;
import java.util.Collections;

import config.MazeConfig;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import static java.lang.Math.*;

import javax.naming.NoPermissionException;

import java.util.HashSet;
import java.util.Comparator;

public class RunAwayAI {
    
    public static ArrayList<IntCoordinates> fuirDePacMan(IntCoordinates startC, IntCoordinates pacManC, MazeConfig config) {
    Noeud start = new Noeud(startC, null);
    Noeud pacMan = new Noeud(pacManC, null);
    LinkedList<Noeud> closedList = new LinkedList<>();
    PriorityQueue<Noeud> openList = new PriorityQueue<Noeud>(new Comparator<Noeud>() {
        @Override
        public int compare(Noeud n1, Noeud n2) {
            // Inverser la comparaison pour sélectionner le nœud avec le coût total le plus élevé
            if (n1.getHeuristique() == n2.getHeuristique()) {
                return 0;
            } else {
                return n1.getHeuristique() > n2.getHeuristique() ? -1 : 1;
            }
        }
    });

    openList.add(start);

    while (!openList.isEmpty()) {
        Noeud u = openList.poll();
        if (u.getCoordinates().equals(pacMan.getCoordinates())) {
            return AStar.buildPath(start, u);
        }
        for (Noeud v : u.getVoisins(config)) {
            if (!(AStar.queueContains(closedList, v) || AStar.queueContainsAndSmaller(openList, v))) {
                v.setCout(u.getCout() + 1);
                // Calculer l'heuristique comme la distance de v à Pac-Man
                v.setHeuristique(v.manhattanDistance(pacMan));
                openList.add(v);
            }
        }
        closedList.add(u);
    }
    return null;
}
public static Direction decideDirection(IntCoordinates currentPos, IntCoordinates nextPos) {
    int dx = nextPos.x() - currentPos.x();
    int dy = nextPos.y() - currentPos.y();

    if (dx > 0) {
        return Direction.EAST;
    } else if (dx < 0) {
        return Direction.WEST;
    } else if (dy > 0) {
        return Direction.SOUTH;
    } else if (dy < 0) {
        return Direction.NORTH;
    } else {
        // Aucun mouvement nécessaire, les positions sont identiques
        return Direction.NONE;
    }
}





}
