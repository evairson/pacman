//package ghostAI;
//
//import model.Critter;
//import model.Direction;
//import model.Ghost;
//import model.MazeState;
//import geometry.IntCoordinates;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//
//public class BlinkyAI {
//    private static final Random random = new Random();
//    private static final int MOVE_INTERVAL = 100; // intervalle de déplacement souhaité en millisecondes
//    private long lastMoveTime = 0;
//
//
//
//    public void updateBlinky(MazeState mazeState) {
//        long currentTime = System.currentTimeMillis();
//        if (currentTime - lastMoveTime >= MOVE_INTERVAL) {
//            lastMoveTime = currentTime;
//
//            Ghost blinky = Ghost.BLINKY;
//            IntCoordinates pacManPos = mazeState.getPacManPosition();
//            IntCoordinates blinkyPos = blinky.getPos().round();
//
//            // logique d'ia ici
//            // Par exemple, pour suivre Pac-Man, on calcule la direction vers laquelle aller
//            // en fonction des positions de Pac-Man et de Blinky.
//
//            Direction nextDirection = calculateDirectionToFollowPacMan(blinkyPos, pacManPos);
//
//            // faut que la direction calculée soit valide et ne conduit pas à une collision avec un mur
//            if (isValidDirection(blinkyPos, nextDirection, mazeState)) {
//                blinky.setDirection(nextDirection);
//            }
//        }
//    }
//
//    private Direction calculateDirectionToFollowPacMan(IntCoordinates blinkyPos, IntCoordinates pacManPos) {
//        // la logique pour suivre Pac-Man comme une ombre.
//        // on peut calculer la direction en comparant les positions de Blinky et de Pac-Man.
//        // Par exemple, choisir la direction qui rapproche Blinky de Pac-Man.
//
//        // Exemple simple : Si Pac-Man est à gauche de Blinky, aller à gauche.
//        if (pacManPos.x() < blinkyPos.x()) {
//            return Direction.WEST;
//        }
//        // Ajoute d'autres conditions pour gérer d'autres cas de direction.
//
//        // Par défaut, retourne une direction aléatoire en cas de doute.
//        return getRandomDirection();
//    }
//
//    private boolean isValidDirection(IntCoordinates pos, Direction direction, MazeState mazeState) {
//
//
//        // Exemple simple : Vérifie si la nouvelle position est dans une cellule vide.
//        IntCoordinates nextPos = pos.plus(direction.toRealCoordinates());
//        return !mazeState.isWall(nextPos);
//    }
//
//    private Direction getRandomDirection() {
//        List<Direction> directions = List.of(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);
//        return directions.get(random.nextInt(directions.size()));
//    }
//}
