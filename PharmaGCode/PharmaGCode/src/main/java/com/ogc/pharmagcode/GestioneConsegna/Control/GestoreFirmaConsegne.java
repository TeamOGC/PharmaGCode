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
    private final GestoreVisualizzaConsegne gestoreVisualizzaConsegne;
    private final Stage stage;

    public GestoreFirmaConsegne(GestoreVisualizzaConsegne gestoreVisualizzaConsegne, Collo colloDaFirmare) {
        this.stage = new Stage();
        this.daFirmare = colloDaFirmare;
        this.gestoreVisualizzaConsegne = gestoreVisualizzaConsegne;
        Utils.cambiaInterfaccia("GestioneConsegna/FirmaConsegne.fxml", this.stage, c -> new InterfacciaFirmaConsegne(this));
    }

    public void firmaCollo(String firma) {
        if (firma.isBlank()) {
            Utils.creaPannelloErrore("Inserisci i dati richiesti");
        }
        Main.log.info("Sto firmando il collo " + daFirmare.getId_collo() + " " + firma);
        daFirmare.setFirma(firma);
        if (DBMSDaemon.queryFirmaCollo(firma, daFirmare))
            gestoreVisualizzaConsegne.aggiornaTabella(RecordCollo.fromCollo(daFirmare, "", null));
        this.stage.close();

    }
}
