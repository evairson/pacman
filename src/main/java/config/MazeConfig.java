package config;

import geometry.IntCoordinates;

import static config.Cell.Content.DOT;
import static config.Cell.*;
import static config.Cell.Content.NOTHING;

public class MazeConfig {
    public MazeConfig(Cell[][] grid, IntCoordinates pacManPos, IntCoordinates blinkyPos, IntCoordinates pinkyPos,
                      IntCoordinates inkyPos, IntCoordinates clydePos) {
        this.grid = new Cell[grid.length][grid[0].length];
        for (int i = 0; i < getHeight(); i++) {
            if (getWidth() >= 0) System.arraycopy(grid[i], 0, this.grid[i], 0, getHeight());
        }
        this.pacManPos = pacManPos;
        this.blinkyPos = blinkyPos;
        this.inkyPos = inkyPos;
        this.pinkyPos = pinkyPos;
        this.clydePos = clydePos;
    }

    private final Cell[][] grid;
    private final IntCoordinates pacManPos, blinkyPos, pinkyPos, inkyPos, clydePos;

    public IntCoordinates getPacManPos() {
        return pacManPos;
    }

    public IntCoordinates getBlinkyPos() {
        return blinkyPos;
    }

    public IntCoordinates getPinkyPos() {
        return pinkyPos;
    }

    public IntCoordinates getInkyPos() {
        return inkyPos;
    }

    public IntCoordinates getClydePos() {
        return clydePos;
    }

    public int getWidth() {
        return grid[0].length;
    }

    public int getHeight() {
        return grid.length;
    }

    public Cell getCell(IntCoordinates pos) {
        return grid[Math.floorMod(pos.y(), getHeight())][Math.floorMod(pos.x(), getWidth())];
    }


    /** TODO : labyrinthes pourraient être configurés à partir d'un fichier texte
     *  1. Définir une structure de fichier texte qui représente le labyrinthe, et des caractères pour représenter les
     *     différents types de cellules. Par exemple, le fichier suivant pourrait représenter un labyrinthe :
     *     <pre>
     *         +---+---+---+---+---+---+
     *         | .   .   . | .   .   . |
     *         + .-+ .-+ .-+ .-+ .-+ .+
     *         | . | . | .   . | . | . |
     *         + .+ .+ .+ .-+ .+ .+ .+
     *         | .   . | . | . | .   . |
     *         + .-+ .+ .+ .+ .+ .-+ .+
     *         | . | .   . | .   . | . |
     *         +---+---+---+---+---+---+
     *     </pre>
     *         Dans ce fichier, les caractères utilisés sont :
     *         <ul>
     *             <li> '+' pour les coins </li>
     *             <li> '-' pour les murs horizontaux </li>
     *             <li> '|' pour les murs verticaux </li>
     *             <li> '.' pour les points </li>
     *             <li> ' ' pour les cases vides </li>
     *             <li> 'E' le Xanax de Pacman (pour ceux qui ont la réf xd) </li>
     *          </ul>
     *  2. On pourrait alors utiliser la fonction suivante pour lire un labyrinthe à partir d'un fichier :
     *     <pre>
     *         public static MazeConfig readFromFile(String filename) {
     *         // TODO
     *         }
     *     </pre>
     *  3. Ajout d'une méthode de lecture de fichier dans la classe {@link MazeConfig}
     *
     * @return
     */

    public static MazeConfig makeExample1() {
        return new MazeConfig(new Cell[][]{
                {nTee(DOT),    hPipe(DOT),     hPipe(DOT),     hPipe(DOT),     hPipe(DOT),     nTee(DOT)},
                {vPipe(DOT),    seVee(NOTHING), nTee(NOTHING),  nTee(NOTHING),  swVee(NOTHING), vPipe(DOT)},
                {vPipe(DOT),     wTee(NOTHING),  open(NOTHING),  open(NOTHING),  eTee(NOTHING),  vPipe(DOT)},
                {vPipe(DOT),    wTee(NOTHING),  open(NOTHING),  open(NOTHING),  eTee(NOTHING),  vPipe(DOT)},
                {vPipe(DOT),    neVee(NOTHING), sTee(NOTHING),  sTee(NOTHING),   nwVee(NOTHING), vPipe(DOT)},
                {neVee(DOT),    hPipe(DOT),     hPipe(DOT),     hPipe(DOT),     hPipe(DOT),     nwVee(DOT)}
        },
                new IntCoordinates(3, 0),
                new IntCoordinates(0, 3),
                new IntCoordinates(3, 5),
                new IntCoordinates(5, 5),
                new IntCoordinates(5, 1)
        );
    }
}
