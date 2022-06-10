package com.ogc.pharmagcode.GestioneAzienda.Control;

import com.ogc.pharmagcode.GestioneAzienda.Interface.InterfacciaModificaProduzione;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;


public class GestoreModificaProduzione {

    //    public static final Logger log = LogManager.getLogger(GestoreModificaProduzione.class);
    public GestoreModificaProduzione() {
        Utils.cambiaInterfaccia("GestioneProduzione/ModificaProduzione.fxml", new Stage(), c -> new InterfacciaModificaProduzione(this), 600, 400);

    }

    /**
     * Modifica la produzione del farmaco con una nuova quantità
     *
     * @param nome_farmaco Nome del farmaco di cui modificare la produzione
     * @param qta          La nuova quantità da produrre periodicamente
     */
    public void modificaProduzione(String nome_farmaco, int qta) {
//        DBMSDaemon db = new DBMSDaemon();
        Main.log.info("Simulando la modifica di produzione: " + nome_farmaco + ": " + qta);
    }

}