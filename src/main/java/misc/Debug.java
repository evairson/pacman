package misc;

/**
 * Classe pour afficher des messages de debug
 * out() est public static pour pouvoir être appelée de n'importe où
 * à utiliser TREEEES fréquement pour débugger
 */

public class Debug {
    public static void out(String message) {
        // comment this out if you do not want to see the debug messages
        System.out.println(">> DEBUG >> " + message);
    }
}
