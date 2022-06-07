package com.ogc.pharmagcode.GestioneFarmaci;

import com.ogc.pharmagcode.Entity.Ordine;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

public class GestoreModificaOrdine {
    private InterfacciaModificaOrdine i;
    private Ordine ordine;
    public GestoreModificaOrdine(Ordine ordine){
        this.ordine=ordine;
        i=(InterfacciaModificaOrdine) Utils.cambiaInterfaccia("GestioneFarmaci/ModificaOrdine.fxml",new Stage(), c->{
            return new InterfacciaModificaOrdine(this,ordine);
        }, 600, 400);
    }

    public void modificaOrdine(int nuovaQuantita){ //LocalDate date
        if(DBMSDaemon.queryAggiornaQuantitaOrdine(ordine,nuovaQuantita)==-1)
            Utils.creaPannelloErrore("Errore");
    }
}
