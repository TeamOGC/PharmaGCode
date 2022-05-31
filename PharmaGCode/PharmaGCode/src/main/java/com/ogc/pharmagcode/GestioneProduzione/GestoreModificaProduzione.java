package com.ogc.pharmagcode.GestioneProduzione;

import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;


public class GestoreModificaProduzione {

//    public static final Logger log = LogManager.getLogger(GestoreModificaProduzione.class);
    public GestoreModificaProduzione(Stage s){
        Utils.cambiaInterfaccia("GestioneProduzione/ModificaProduzione.fxml",s, c-> new InterfacciaModificaProduzione(this));

    }

    /**
     * Modifica la produzione del farmaco con una nuova quantità
     *
     * @param nome_farmaco Nome del farmaco di cui modificare la produzione
     * @param qta La nuova quantità da produrre periodicamente
     */
    void modificaProduzione(String nome_farmaco, int qta){
//        DBMSDaemon db = new DBMSDaemon();
//        log.info("Simulando la modifica di produzione, sium");
    }

}