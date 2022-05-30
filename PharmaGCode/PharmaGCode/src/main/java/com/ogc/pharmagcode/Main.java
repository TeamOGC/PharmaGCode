package com.ogc.pharmagcode;

import com.ogc.pharmagcode.GestioneAccount.GestoreAutenticazione;
import com.ogc.pharmagcode.Utils.Orologio;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.System.exit;

public class Main extends Application {
    public static Orologio orologio=new Orologio();
    public static int sistema,idFarmacia;
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("PharmaGC");
        stage.setOnCloseRequest(c->{System.exit(0);});
        new GestoreAutenticazione(stage);
    }

    public static void risolviSistema(String[] args){
        //Definisce quale tipo di sistema si sta usando (0 Farmacia, 1 Corriere, 2 Azienda)
        //e definisce un ID Farmacia (utilizzato solo se il sistema è destinato a una farmacia)
        try {
            if (args.length >= 1) {
                sistema = Integer.parseInt(args[0]);
                if (sistema < 0 || sistema > 3) {
                    throw new NumberFormatException("Tipo di sistema non valido");
                }
            }
            if (args.length >= 2) {
                idFarmacia = Integer.parseInt(args[1]);
            }
        }catch (NumberFormatException e){
            System.err.println(e.getMessage());
            exit(1);
        }
    }

    public static void main(String[] args) {
        risolviSistema(args);
        orologio.start();
        launch();
        try {
            orologio.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}