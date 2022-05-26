package com.ogc.pharmagcode.Utils;

import javafx.fxml.FXML;
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
