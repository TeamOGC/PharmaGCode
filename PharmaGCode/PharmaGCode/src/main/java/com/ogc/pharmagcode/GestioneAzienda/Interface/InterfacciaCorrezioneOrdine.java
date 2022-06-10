package com.ogc.pharmagcode.GestioneAzienda.Interface;

import com.ogc.pharmagcode.Entity.Ordine;
import com.ogc.pharmagcode.GestioneAzienda.Control.GestoreCorrezioneOrdine;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class InterfacciaCorrezioneOrdine {

    private GestoreCorrezioneOrdine control;

    public InterfacciaCorrezioneOrdine(GestoreCorrezioneOrdine control, Ordine ordine) {
        this.control = control;
    }

    public TextField qtaIntegrare;
    public Button confermaBtn;

    public void initialize() {
        this.qtaIntegrare.setTextFormatter(new TextFormatter<>(Utils.positiveIntegerFilter));
    }

    public void correggiOrdine() {
        int qta;
        try {
            qta = Integer.parseInt(qtaIntegrare.getText());
        } catch (Exception ignored) {
            return;
        }
        this.control.correggiOrdine(qta);
    }
}
