package com.ogc.pharmagcode.GestioneAccount;

import com.ogc.pharmagcode.Entity.Corriere;
import com.ogc.pharmagcode.Entity.Impiegato;
import com.ogc.pharmagcode.GestioneConsegna.InterfacciaPrincipaleCorriere;
import com.ogc.pharmagcode.GestioneFarmaci.InterfacciaPrincipaleFarmacista;
import com.ogc.pharmagcode.GestioneProduzione.InterfacciaPrincipaleImpiegato;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Entity.Utente;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

public class GestoreAutenticazione {
    private static Utente utente;
    private static Utente def=new Utente("Giovanni","Muciaccia","test1@sium.it");
    private static Utente def2=new Corriere(11,"Giovanni","Muciaccia","test1@sium.it","PasswordTest");
    private static Utente def3=new Impiegato(22,"Giovanni","Muciaccia","test1@sium.it","PasswordTest");
    public GestoreAutenticazione(Stage stage){
        if(Main.debug)
            switch(Main.sistema) {
                case 0:
                    utente = def;
                    break;
                case 1:
                    utente=def2;
                    break;
                case 2:
                    utente=def3;
                    break;
            }
        Utils.cambiaInterfaccia("GestioneAccount/Login.fxml",stage, c->{return new ModuloLogin(this);});
    }
    public void controlloCredenziali(String email, String password, Stage s) {
        if(utente==null)
            utente=DBMSDaemon.queryControllaCredenziali(email,Utils.hash(password));
        if (Main.sistema == 0) {
            if(utente!=null)
                Utils.cambiaInterfaccia("GestioneFarmaci/InterfacciaFarmacista.fxml", s
                    , c -> {
                        return new InterfacciaPrincipaleFarmacista(GestoreAutenticazione.utente.nome(),GestoreAutenticazione.utente.cognome());
                    });
            else
                Utils.creaPannelloErrore("Credenziali non corrette");

        } else if (Main.sistema == 1) {
            Corriere corriere=(Corriere) utente;
            Utils.cambiaInterfaccia("GestioneConsegna/InterfacciaCorriere.fxml", s
                    , c -> {
                        return new InterfacciaPrincipaleCorriere(corriere.nome(),corriere.cognome(), corriere.getId_corriere()+"");
                    });
        } else if (Main.sistema == 2) {
            Impiegato impiegato=(Impiegato) utente;
            Utils.cambiaInterfaccia("GestioneProduzione/InterfacciaAzienda.fxml", s
                    , c -> {
                        return new InterfacciaPrincipaleImpiegato(impiegato.nome(),impiegato.cognome(), impiegato.getId_impiegato()+"");
                    });
        }
    }
    public static Utente getUtente(){
        return utente;
    }

    public void creaPannelloErrore(){ //da cambiare l'accesso
        Utils.creaPannelloErrore("NOOOOO");
    }

    public void sostituisciInterfaccia(){

    }

    public void creaInterfacciaPrincipale(Stage stage){
    }
}
