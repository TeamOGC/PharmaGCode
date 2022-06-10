package com.ogc.pharmagcode.GestioneAzienda.Control;

import com.ogc.pharmagcode.GestioneAzienda.Interface.InterfacciaModificaProduzione;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

public class GestoreModificaProduzione {

    public GestoreModificaProduzione() {
        Stage stage = new Stage();
        Utils.cambiaInterfaccia("GestioneProduzione/ModificaProduzione.fxml", stage, c -> new InterfacciaModificaProduzione(this), 600, 400);
    }

    /**
     * Modifica la produzione del farmaco con una nuova quantità
     *
     * @param nome_farmaco Nome del farmaco di cui modificare la produzione
     * @param qta          La nuova quantità da produrre periodicamente
     */
    public void modificaProduzione(String nome_farmaco, int qta) {
        Main.log.info("Simulando la modifica di produzione: " + nome_farmaco + ": " + qta);
        // TODO: Implementare Modifica Produzione
        /*
        Controllo Esistenza Farmaco(farmaco)
            Se NO: PannelloErrore
            Altrimenti: DB.ModificaProduzione(farmaco, qta)
         */
    }

}
