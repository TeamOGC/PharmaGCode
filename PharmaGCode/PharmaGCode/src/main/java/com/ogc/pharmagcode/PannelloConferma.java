package com.ogc.pharmagcode;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class PannelloConferma extends Pannello{

    public PannelloConferma(String messaggioConferma, Stage s){
        super(messaggioConferma, s);
    }
    @FXML
    protected void conferma(){
        s.close();
    }
}