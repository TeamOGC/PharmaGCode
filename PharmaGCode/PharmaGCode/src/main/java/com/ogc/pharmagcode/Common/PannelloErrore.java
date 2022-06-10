package com.ogc.pharmagcode.Common;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class PannelloErrore extends Pannello{

    public PannelloErrore(String messaggioErrore, Stage s){
        super(messaggioErrore, s);
    }

    @FXML
    protected void conferma(){
        if(s != null) s.close();
        ((Stage) messaggio.getScene().getWindow()).close();
    }
}
