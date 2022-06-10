package com.ogc.pharmagcode.GestioneAzienda.Control;

import com.ogc.pharmagcode.Entity.Ordine;
import com.ogc.pharmagcode.GestioneAzienda.Interface.InterfacciaCorrezioneOrdine;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

public class GestoreCorrezioneOrdine {

    private final InterfacciaCorrezioneOrdine boundary;
    private Ordine daCorreggere;

    public GestoreCorrezioneOrdine(Ordine daCorreggere) {
        Main.log.info("Tentando di correggere  l'ordine " + daCorreggere.getData_consegna());
        this.boundary = (InterfacciaCorrezioneOrdine) Utils.cambiaInterfaccia("GestioneOrdini/CorrezioneOrdine.fxml",
                new Stage(),
                c -> new InterfacciaCorrezioneOrdine(this, daCorreggere),
                600,
                400
        );
        this.daCorreggere = daCorreggere;
    }

    public void correggiOrdine(int qtaDaAggiungere) {
        int qtaGiaCaricata = DBMSDaemon.queryControllaQuantita(daCorreggere);
        if (qtaGiaCaricata == -1) {
            Main.log.error("Per qualche motivo la quantità caricata non è stata presa...");
            return;
        }
        Main.log.info("Quantità già caricata: " + qtaGiaCaricata);
        if (qtaDaAggiungere > (daCorreggere.getQuantita() - qtaGiaCaricata)) {
            // ho voluto aggiungere più farmaci di quanti ne mancano
            Utils.creaPannelloErrore("Non puoi correggere l'ordine inserendo più farmaci di quanti ne mancano");
            return;
        }

//        DBMSDaemon.queryCorreggiOrdine(qtaDaAggiungere, qtaGiaCaricata, daCorreggere);
        daCorreggere.setStato("Corretto");

        Main.log.info("Ordine corretto");
        ((Stage) boundary.qtaIntegrare.getScene().getWindow()).close();
    }
}


