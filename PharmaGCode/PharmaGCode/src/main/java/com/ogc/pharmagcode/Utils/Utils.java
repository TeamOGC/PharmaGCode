package com.ogc.pharmagcode.Utils;

import com.ogc.pharmagcode.Common.PannelloAvviso;
import com.ogc.pharmagcode.Common.PannelloConferma;
import com.ogc.pharmagcode.Common.PannelloErrore;
import com.ogc.pharmagcode.GestioneFarmaci.Interface.PannelloAvvisoDisponibilita;
import com.ogc.pharmagcode.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextFormatter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.function.UnaryOperator;

public class Utils {

    public static UnaryOperator<TextFormatter.Change> integerFilter = change -> {
        String newText = change.getControlNewText();
        if (newText.matches("-?([0-9]*)?")) {
            return change;
        }
        return null;
    };
    public static UnaryOperator<TextFormatter.Change> positiveIntegerFilter = change -> {
        String newText = change.getControlNewText();
        if (newText.matches("([0-9]*)?")) {
            return change;
        }
        return null;
    };

    public static UnaryOperator<TextFormatter.Change> nonZeroPositiveIntegerFilter = change -> {
        String newText = change.getControlNewText();
        if (newText.matches("([1-9][0-9]*)?")) {
            return change;
        }
        return null;
    };
    public static String hash(String pwd) {
        byte[] encoded;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            encoded = md.digest(pwd.getBytes(StandardCharsets.UTF_8));
            return new String(encoded);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return pwd;
    }

    public static FXMLLoader creaLoader(String path) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(path));
        return loader;
    }

    private static void creaInterfaccia(FXMLLoader loader, int w, int h, Stage stage) {
        if (Main.mainStage != null) {
            try {
                stage.initOwner(Main.mainStage);
                stage.initModality(Modality.APPLICATION_MODAL);
            } catch (Exception e) {
            }
        }
        stage.setResizable(false);
        try {
            Scene s = new Scene(loader.load(), w, h);
            stage.setScene(s);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void creaPannelloErrore(String messaggio) {
        creaPannelloErrore(messaggio, null);
    }

    public static void creaPannelloErrore(String messaggio, Stage daDistruggere) {
        Stage stage = new Stage();
        stage.setResizable(false);
        if (Main.mainStage != null) {
            try {
                stage.initOwner(Main.mainStage);
                stage.initModality(Modality.APPLICATION_MODAL);
            } catch (Exception e) {
            }
        }
        FXMLLoader loader = creaLoader("Pannelli/ErroreGenericoPopup.fxml");
        loader.setControllerFactory(c -> {
            return new PannelloErrore(messaggio, daDistruggere);
        });
        creaInterfaccia(loader, 600, 400, stage);
    }

    public static void creaPannelloAvvisoScadenza(EventHandler<ActionEvent> onConfirm, EventHandler<ActionEvent> onExit) {
        Stage stage = new Stage();
        stage.setResizable(false);
        if (Main.mainStage != null) {
            try {
                stage.initOwner(Main.mainStage);
                stage.initModality(Modality.APPLICATION_MODAL);
            } catch (Exception e) {
            }
        }
        FXMLLoader loader = creaLoader("Pannelli/PannelloAvvisoScadenza.fxml");
        loader.setControllerFactory(c -> new PannelloAvviso(onConfirm, onExit));
        creaInterfaccia(loader, 600, 400, stage);
    }

    public static void creaPannelloModalitaConsegna(EventHandler<ActionEvent> stessaData, EventHandler<ActionEvent> dateDiverse) {
        Stage stage = new Stage();
        stage.setResizable(false);
        if (Main.mainStage != null) {
            try {
                stage.initOwner(Main.mainStage);
                stage.initModality(Modality.APPLICATION_MODAL);
            } catch (Exception ignored) {
            }
        }
        FXMLLoader loader = creaLoader("GestioneFarmaci/ModalitaConsegnaPopup.fxml");
        loader.setControllerFactory(c -> new PannelloAvvisoDisponibilita(stessaData, dateDiverse));
        creaInterfaccia(loader, 600, 400, stage);
    }


    /**
     * Crea un nuovo pannello di conferma
     *
     * @param messaggio il messaggio da mostrare
     */
    public static void creaPannelloConferma(String messaggio) {
        creaPannelloConferma(messaggio, null);
    }

    public static void creaPannelloConferma(String messaggio, Stage daDistruggere) {
        Stage stage = new Stage();
        FXMLLoader loader = creaLoader("Pannelli/ConfermaPopup.fxml");
        loader.setControllerFactory(c -> {
            return new PannelloConferma(messaggio, daDistruggere);
        });
        creaInterfaccia(loader, 600, 400, stage);
    }

    /**
     * Cambia l'interfaccia sullo stage passato come paramentro (eventualmente creato)
     *
     * @param interfaccia percorso dell'FXML
     * @param stage
     * @param c           Callback chiamata quando viene creata l'interfaccia
     * @return L'oggetto
     */
    public static Object cambiaInterfaccia(String interfaccia, Stage stage, Callback c) {
        FXMLLoader loader = creaLoader(interfaccia);
        loader.setControllerFactory(c);
        creaInterfaccia(loader, 1280, 800, stage);
        return loader.getController();
    }

    public static Object cambiaInterfaccia(String interfaccia, Stage stage, Callback c, int w, int h) {
        FXMLLoader loader = creaLoader(interfaccia);
        loader.setControllerFactory(c);
        creaInterfaccia(loader, w, h, stage);
        return loader.getController();
    }

    public static Object cambiaInterfaccia(String interfaccia, Stage stage, int w, int h) {
        FXMLLoader loader = creaLoader(interfaccia);
        creaInterfaccia(loader, w, h, stage);
        return loader.getController();
    }
}
