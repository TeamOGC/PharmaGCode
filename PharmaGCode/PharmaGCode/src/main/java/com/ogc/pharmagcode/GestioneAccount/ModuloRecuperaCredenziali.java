package com.ogc.pharmagcode.GestioneAccount;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

public class ModuloRecuperaCredenziali {

    GestoreRecuperaCredenziali grc;

    @FXML
    private TextField email;
    public ModuloRecuperaCredenziali(GestoreRecuperaCredenziali grc){
        this.grc=grc;
    }
    @FXML
    protected void conferma(){
        grc.verificaMailEInvia(email.getText());
    }
}
