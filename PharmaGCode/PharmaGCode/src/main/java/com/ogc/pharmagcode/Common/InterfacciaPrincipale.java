package com.ogc.pharmagcode.Common;

import com.ogc.pharmagcode.BoundaryDiSistema;
import com.ogc.pharmagcode.Autenticazione.Control.GestoreModificaPassword;
import com.ogc.pharmagcode.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class InterfacciaPrincipale {
    String nomeMostrato;
    BoundaryDiSistema b=new BoundaryDiSistema();
    @FXML
    protected Label nomeUtente;
    @FXML
    protected Label orologio;

    public InterfacciaPrincipale(String nomeUtente){
        nomeMostrato=nomeUtente;
    }

    @FXML
    protected void initialize(){
        nomeUtente.setText(nomeMostrato);
        Main.orologio.setOrologio(e->{
            orologio.setText(Main.orologio.chiediOrarioFormattato());
            b.chiediOrario();
        });
    }

    @FXML
    protected void cliccaModificaPassword(){
        new GestoreModificaPassword();
    }
}
