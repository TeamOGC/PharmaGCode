package com.ogc.pharmagcode;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PannelloErrore extends Pannello{

    public PannelloErrore(String messaggioErrore, Stage s){
        super(messaggioErrore, s);
    }

    @FXML
    protected void conferma(){
        s=(Stage) messaggio.getScene().getWindow();
        s.close();
    }
}
