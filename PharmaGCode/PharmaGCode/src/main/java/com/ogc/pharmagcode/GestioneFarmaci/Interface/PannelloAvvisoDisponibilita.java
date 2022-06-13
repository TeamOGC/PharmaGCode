package com.ogc.pharmagcode.GestioneFarmaci.Interface;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PannelloAvvisoDisponibilita {
    @FXML
    private Button cliccaConsegnaSingola, cliccaConsegnaMultipla;
    EventHandler<ActionEvent> modalitaSingola;
    EventHandler<ActionEvent> modalitaMultipla;

    public PannelloAvvisoDisponibilita(EventHandler<ActionEvent> creaOrdiniStessaData, EventHandler<ActionEvent> creaOrdiniDateSeparate) {
        this.modalitaSingola = creaOrdiniStessaData;
        this.modalitaMultipla = creaOrdiniDateSeparate;
    }
    @FXML
    protected void initialize() {
        cliccaConsegnaSingola.addEventHandler(ActionEvent.ACTION, modalitaSingola);
        cliccaConsegnaSingola.addEventHandler(ActionEvent.ACTION, distruggiQuesta -> {((Stage)this.cliccaConsegnaMultipla.getScene().getWindow()).close();});
        cliccaConsegnaMultipla.addEventHandler(ActionEvent.ACTION, modalitaMultipla);
        cliccaConsegnaMultipla.addEventHandler(ActionEvent.ACTION, distruggiQuesta -> {((Stage)this.cliccaConsegnaMultipla.getScene().getWindow()).close();});

    }
}
