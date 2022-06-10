package com.ogc.pharmagcode.common.pannelli;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class PannelloConferma extends Pannello{

    public PannelloConferma(String messaggioConferma, Stage s){
        super(messaggioConferma, s);
    }
    @FXML
    protected void conferma(){
        if(s != null) s.close();
        ((Stage) messaggio.getScene().getWindow()).close();
    }
}