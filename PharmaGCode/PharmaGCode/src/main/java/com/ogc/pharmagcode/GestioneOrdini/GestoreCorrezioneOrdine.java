package com.ogc.pharmagcode.GestioneOrdini;

import com.ogc.pharmagcode.Entity.Ordine;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

public class GestoreCorrezioneOrdine {

    private final InterfacciaCorrezioneOrdine boundary;
    private Ordine daCorreggere;

    public GestoreCorrezioneOrdine(Ordine daCorreggere){
        this.boundary=(InterfacciaCorrezioneOrdine) Utils.cambiaInterfaccia("GestioneOrdini/CorrezioneOrdine.fxml",
                new Stage(),
                c->{return new InterfacciaCorrezioneOrdine(daCorreggere);});
        this.daCorreggere = daCorreggere;
    }
}


