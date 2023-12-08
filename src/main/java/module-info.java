module pacman {
    requires java.desktop;
    requires javafx.controls;
    requires javafx.media;
    requires javafx.fxml;
    requires javafx.graphics;
    exports gui;
    exports config;
    exports GhostsAI;
    exports model;
    exports geometry;
}