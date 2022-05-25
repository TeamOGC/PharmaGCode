package com.ogc.pharmagcode;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("PharmaGC");
        new GestoreAutenticazione(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}