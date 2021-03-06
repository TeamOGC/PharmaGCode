package com.ogc.pharmagcode;

import com.ogc.pharmagcode.Autenticazione.Control.GestoreAutenticazione;
import com.ogc.pharmagcode.Utils.Orologio;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static java.lang.System.exit;

public class Main extends Application {

    public static final Logger log = LogManager.getLogger(Main.class);
    public static Stage mainStage = null;
    public static Orologio orologio = new Orologio();
    public static int sistema, idFarmacia;

    public static String nomeFarmacia;
    public static boolean debug;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        stage.setTitle("PharmaGC");
        log.info("App partita");
        stage.setOnCloseRequest(c -> {
            System.exit(0);
        });
        new GestoreAutenticazione(stage);
    }

    private static void risolviSistema(String[] args) {
        //Definisce quale tipo di sistema si sta usando (0 Farmacia, 1 Corriere, 2 Azienda)
        //e definisce un ID Farmacia (utilizzato solo se il sistema è destinato a una farmacia)
        try {
            if (args.length >= 1) {
                sistema = Integer.parseInt(args[0]);
                if (sistema < 0 || sistema > 3) {
                    throw new NumberFormatException("Tipo di sistema non valido");
                }
            }
            if (args.length >= 3) {
                nomeFarmacia=args[1];
                idFarmacia = Integer.parseInt(args[2]);
            }
            if (args.length >= 4) {
                debug = Integer.parseInt(args[3]) == 1;
            }
        } catch (NumberFormatException e) {
            System.err.println(e.getMessage());
            exit(1);
        }
    }

    public static void main(String[] args) {
        risolviSistema(args);
        orologio.start();
//        DBMSDaemon.connect(); //potenzialmente inutile farlo qui, o comunque meglio in un altro thread?

        launch();
        try {
            orologio.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}