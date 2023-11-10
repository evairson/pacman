package gui;

/**
 * Comme son nom l'indique, cette interface permet de mettre à jour l'affichage
 * d'un objet graphique.
 *
 * update() est appelée à chaque frame, et getNode() permet de récupérer le
 * noeud JavaFX associé à l'objet graphique.
 *
 */

import javafx.scene.Node;

public interface GraphicsUpdater {
    void update();
    Node getNode();
}
