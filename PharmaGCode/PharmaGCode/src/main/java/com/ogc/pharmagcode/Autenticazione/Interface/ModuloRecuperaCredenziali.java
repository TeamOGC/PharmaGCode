package com.ogc.pharmagcode.Autenticazione.Interface;

import com.ogc.pharmagcode.Autenticazione.Control.GestoreRecuperaCredenziali;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ModuloRecuperaCredenziali {

    GestoreRecuperaCredenziali grc;

    @FXML
    private TextField email;

    public ModuloRecuperaCredenziali(GestoreRecuperaCredenziali grc) {
        this.grc = grc;
    }

    @FXML
    protected void conferma() {
        grc.verificaMailEInvia(email.getText());
    }
}
