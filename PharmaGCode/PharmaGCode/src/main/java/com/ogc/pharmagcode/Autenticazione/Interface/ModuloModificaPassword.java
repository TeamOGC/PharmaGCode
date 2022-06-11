package com.ogc.pharmagcode.Autenticazione.Interface;

import com.ogc.pharmagcode.Autenticazione.Control.GestoreModificaPassword;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class ModuloModificaPassword {

    GestoreModificaPassword gmp;

    public ModuloModificaPassword(GestoreModificaPassword gmp) {
        this.gmp = gmp;
    }

    @FXML
    private PasswordField old_pwd;
    @FXML
    private PasswordField new_pwd;
    @FXML
    private PasswordField re_pwd;

    @FXML
    protected void conferma() {
        gmp.modificaPassword(old_pwd.getText(), new_pwd.getText(), re_pwd.getText());
    }

    public Stage getStage() {
        return (Stage) old_pwd.getScene().getWindow();
    }

    public void annulla() {
        this.getStage().close();
    }
}
