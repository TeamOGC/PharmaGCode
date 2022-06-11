package com.ogc.pharmagcode.GestioneConsegna.Control;

import com.ogc.pharmagcode.Common.RecordCollo;
import com.ogc.pharmagcode.Entity.Collo;
import com.ogc.pharmagcode.GestioneConsegna.Interface.InterfacciaFirmaConsegne;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

public class GestoreFirmaConsegne {
    public Collo daFirmare;
    private final Stage stage;

    public GestoreFirmaConsegne(Collo colloDaFirmare) {
        this.stage = new Stage();
        this.daFirmare = colloDaFirmare;
        Utils.cambiaInterfaccia("GestioneConsegna/FirmaConsegne.fxml", this.stage, c -> new InterfacciaFirmaConsegne(this));
    }

    public void firmaCollo(String firma) {
        if (firma.isBlank()) {
            Utils.creaPannelloErrore("Inserisci i dati richiesti");
        }
        Main.log.info("Sto firmando il collo " + daFirmare.getId_collo() + " " + firma);
        daFirmare.setFirma(firma);
        if (DBMSDaemon.queryFirmaCollo(firma, daFirmare))
            GestoreVisualizzaConsegne.aggiornaTabella(RecordCollo.fromCollo(daFirmare));
        this.stage.close();

    }
}
