package geometry;

import config.MazeConfig;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Comparator;

public class AStar {

    static Comparator<Noeud> comparator = new Comparator<Noeud>() {
        @Override
        public int compare(Noeud n1, Noeud n2){
            if(n1.getHeuristique() == n2.getHeuristique()){
                return 0;
            } else {
                return n1.getHeuristique() < n2.getHeuristique() ? -1 : 1;
            }
        }
    };

    public static ArrayList<IntCoordinates> convertToArray(Noeud current, Noeud goal, ArrayList<IntCoordinates> acc){
        if((current.getCoordinates().equals(goal.getCoordinates())) || current.getParent() == null){
            return acc;
        } else {
            acc.add(current.getCoordinates());
            return convertToArray(current.getParent(), goal, acc);
        }
    }

    public static boolean queueContains(LinkedList<Noeud> queue, Noeud node){
        for(Noeud n : queue){
            if(n.getCoordinates().equals(node.getCoordinates())){
                return true;
            }
        }
        return false;
    }

    public static boolean queueContainsAndSmaller(PriorityQueue<Noeud> queue, Noeud node){
        for(Noeud n : queue){
            if(n.getCoordinates().equals(node.getCoordinates())){
                if(n.compareParHeuristique(node) > 0){
                    return true;
                }
            }
        }
        return false;
    }

    public static ArrayList<IntCoordinates> shortestPath(IntCoordinates startC, IntCoordinates goalC, MazeConfig config){
        Noeud start = new Noeud(startC, null);
        Noeud goal = new Noeud(goalC, null);
        LinkedList<Noeud> closedList = new LinkedList<>();
        PriorityQueue<Noeud> openList = new PriorityQueue<Noeud>(comparator);

        openList.add(start);

        while(!openList.isEmpty()){
            Noeud u = openList.poll();
            if(u.getCoordinates().equals(goal.getCoordinates())){
                return convertToArray(u, start, new ArrayList<IntCoordinates>());
            }
            for(Noeud v : u.getVoisins(config)){
                if(!(queueContains(closedList, v) || queueContainsAndSmaller(openList, v))){
                    v.setCout(u.getCout() + 1);
                    v.setHeuristique(v.getCout() + v.manhattanDistance(goal));
                    openList.add(v);
                }
            }
            closedList.add(u);
        }
        return null;
    }
}

