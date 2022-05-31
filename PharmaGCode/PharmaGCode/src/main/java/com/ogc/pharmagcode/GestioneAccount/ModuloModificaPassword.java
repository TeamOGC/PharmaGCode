package com.ogc.pharmagcode.GestioneAccount;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class ModuloModificaPassword {

    GestoreModificaPassword gmp;
    public  ModuloModificaPassword(GestoreModificaPassword gmp){
        this.gmp=gmp;
    }

    @FXML
    private PasswordField old_pwd;
    @FXML
    private PasswordField new_pwd;
    @FXML
    private PasswordField re_pwd;

    @FXML
    protected void conferma(){
        gmp.modificaPassword(old_pwd.getText(),new_pwd.getText(),re_pwd.getText());
    }
}
