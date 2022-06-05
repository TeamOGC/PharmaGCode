package com.ogc.pharmagcode.GestioneOrdini;

import com.ogc.pharmagcode.Entity.Ordine;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class InterfacciaCorrezioneOrdine {

    private Ordine daCorreggere;

    public InterfacciaCorrezioneOrdine(Ordine ordine){
        this.daCorreggere = ordine;
    }
    public TextField qtaIntegrare;
    public Button confermaBtn;

    public void correggiOrdine(ActionEvent actionEvent) {
    }
}
