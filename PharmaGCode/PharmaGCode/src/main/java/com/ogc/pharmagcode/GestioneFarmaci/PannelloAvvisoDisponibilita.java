package com.ogc.pharmagcode.GestioneFarmaci;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PannelloAvvisoDisponibilita {
    @FXML
    private Button cliccaConsegnaSingola,cliccaConsegnaMultipla;
    EventHandler<ActionEvent> modalitaSingola;
    EventHandler<ActionEvent> modalitaMultipla;
    public PannelloAvvisoDisponibilita(EventHandler<ActionEvent> creaOrdiniStessaData,EventHandler<ActionEvent> creaOrdiniDateSeparate){
        this.modalitaSingola=creaOrdiniStessaData;
        this.modalitaMultipla=creaOrdiniDateSeparate;
    }

    @FXML
    protected void initialize(){
        cliccaConsegnaSingola.setOnAction(modalitaSingola);
        cliccaConsegnaMultipla.setOnAction(modalitaMultipla);
    }
}
