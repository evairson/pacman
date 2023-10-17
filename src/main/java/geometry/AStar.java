package geometry;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class AStar {
    public static List<Cell> findShortestPath(MazeConfig maze, RealCoordinates start, RealCoordinates goal) {
        // Initialisation des listes ouvertes et fermées
        Set<Cell> closedSet = new HashSet<>();
        PriorityQueue<Cell> openSet = new PriorityQueue<>(Comparator.comparingDouble(cell -> cell.getF()));

        // Recherche des cellules correspondant aux coordonnées réelles de départ et d'arrêt
        Cell startCell = maze.getCellAt(start.round());
        Cell goalCell = maze.getCellAt(goal.round());

        // Mettre la cellule de départ dans la liste ouverte
        openSet.add(startCell);

        // Initialiser les valeurs G et H de la cellule de départ
        startCell.setG(0);
        startCell.setH(heuristic(startCell, goalCell));

        while (!openSet.isEmpty()) {
            // Sélectionner la cellule avec le coût F le plus bas depuis la liste ouverte
            Cell current = openSet.poll();

            // Si nous avons atteint la cellule d'objectif, reconstruire le chemin et le retourner
            if (current.equals(goalCell)) {
                return reconstructPath(current);
            }

            // Déplacer la cellule courante de la liste ouverte vers la liste fermée
            closedSet.add(current);

            // Parcourir les voisins de la cellule courante
            for (Cell neighbor : getNeighbors(maze, current)) {
                // Si le voisin est dans la liste fermée, ignorer
                if (closedSet.contains(neighbor)) {
                    continue;
                }

                // Calculer le coût G pour le voisin
                double tentativeG = current.getG() + 1;  // Coût de déplacement égal à 1 (pour un déplacement vers un voisin)

                // Si le voisin n'est pas dans la liste ouverte ou si le nouveau coût G est inférieur
                if (!openSet.contains(neighbor) || tentativeG < neighbor.getG()) {
                    // Mettre à jour les valeurs G, H et parent du voisin
                    neighbor.setG(tentativeG);
                    neighbor.setH(heuristic(neighbor, goalCell));
                    neighbor.setParent(current);

                    // Ajouter le voisin à la liste ouverte
                    openSet.add(neighbor);
                }
            }
        }

        // Aucun chemin trouvé, retourner une liste vide
        return new ArrayList<>();
    }

    private static List<Cell> reconstructPath(Cell current) {
        List<Cell> path = new ArrayList<>();
        while (current != null) {
            path.add(current);
            current = current.getParent();
        }
        return path;
    }

    private static double heuristic(Cell a, Cell b) {
        // Utilisez une heuristique simple, par exemple, la distance de Manhattan
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    private static List<Cell> getNeighbors(MazeConfig maze, Cell cell) {
        List<Cell> neighbors = new ArrayList<>();
        int x = cell.getX();
        int y = cell.getY();

        // Ajoutez ici la logique pour obtenir les voisins de la cellule dans votre configuration de labyrinthe
        // En fonction de vos murs et de vos coordonnées, vous devrez ajuster cette partie.

        return neighbors;
    }
}
