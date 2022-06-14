package com.ogc.pharmagcode.GestioneFarmaci.Control;

import com.ogc.pharmagcode.GestioneFarmaci.Interface.InterfacciaScaricoMerci;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

public class GestoreScaricoMerci {
    private InterfacciaScaricoMerci i;
    private Stage stage;

    public GestoreScaricoMerci() {
        this.stage= new Stage();
        i = (InterfacciaScaricoMerci) Utils.cambiaInterfaccia("GestioneFarmaci/ScaricaMerci.fxml", this.stage, c -> {
            return new InterfacciaScaricoMerci(this);
        }, 600, 400);
    }

    /**
     * Scarico merci inserendo il codice lotto e la quantità venduta
     * Inoltre se le scorte sono inferiori ad un limite inferiore prestabilito (5), notifica il farmacista
     *
     * @param codiceLotto codice del lotto
     * @param quantita quantita venduta
     */
    public void scaricoMerci(int codiceLotto, int quantita) {
        // TODO: Doccia pensiero: Cosa succede se carica un farmaco che non aveva ordinato?
        int s = DBMSDaemon.queryScaricaMerci(codiceLotto, Main.idFarmacia, quantita);
        if(s<0){
            Utils.creaPannelloErrore("Il farmaco non è stato trovato o è avvenuto un errore con il Database");
        }
        else if (s <= 5) {
            Utils.creaPannelloConferma("Merce scaricata correttamente!\nATTENZIONE: il farmaco scaricato è in esaurimento!", this.stage);
        }
        else
            Utils.creaPannelloConferma("Merce scaricata correttamente!", this.stage);
    }
}
