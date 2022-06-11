package com.ogc.pharmagcode.GestioneAzienda.Control;

import com.ogc.pharmagcode.GestioneAzienda.Interface.InterfacciaModificaProduzione;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

public class GestoreModificaProduzione {

    public GestoreModificaProduzione() {
        Stage stage = new Stage();
        Utils.cambiaInterfaccia("GestioneProduzione/ModificaProduzione.fxml", stage, c -> new InterfacciaModificaProduzione(this), 600, 400);
    }

    /**
     * Modifica la produzione del farmaco con una nuova quantità, a patto che esso esista
     *
     * @param nome_farmaco Nome del farmaco di cui modificare la produzione
     * @param qta          La nuova quantità da produrre periodicamente
     */
    public void modificaProduzione(String nome_farmaco, int qta) {
        Main.log.info("Simulando la modifica di produzione: " + nome_farmaco + ": " + qta);
        int id_farmaco = DBMSDaemon.queryControlloEsistenzaFarmaco(nome_farmaco);
        if(id_farmaco != -1){
            DBMSDaemon.queryModificaProduzione(id_farmaco, qta);
            Utils.creaPannelloConferma("Produzione modificata con successo");
        } else{
            Utils.creaPannelloErrore("Il farmaco non esiste");
        }
    }
}
