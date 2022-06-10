package com.ogc.pharmagcode.GestioneAccount;

import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

public class GestoreModificaPassword {
    private ModuloModificaPassword boundary;

    public GestoreModificaPassword() {
        boundary = (ModuloModificaPassword) Utils.cambiaInterfaccia("GestioneAccount/ModificaPassword.fxml", new Stage(), e -> {
            return new ModuloModificaPassword(this);
        });
    }

    private boolean controllaPassword(String new_pwd, String re_pwd) {
        boolean isValid = new_pwd.length() <= 20 && new_pwd.length() >= 8;
        String upperCaseChars = "(.*[A-Z].*)";
        if (!new_pwd.matches(upperCaseChars))
            isValid = false;
        String lowerCaseChars = "(.*[a-z].*)";
        if (!new_pwd.matches(lowerCaseChars))
            isValid = false;
        String numbers = "(.*[0-9].*)";
        if (!new_pwd.matches(numbers))
            isValid = false;
        return isValid && new_pwd.equals(re_pwd);
    }

    public void modificaPassword(String old_pwd, String new_pwd, String re_pwd) { // magari mi faccio passare lo stage da chiudere
        if (controllaPassword(new_pwd, re_pwd)) {
            boolean modificata = DBMSDaemon.queryModificaPassword(GestoreAutenticazione.getUtente().email(), Utils.hash(new_pwd), Utils.hash(old_pwd));
            if (modificata) {
                Utils.creaPannelloConferma("Password modificata con successo!", boundary.getStage());
            } else {
                Utils.creaPannelloErrore("La vecchia password non coincide");
            }
        } else {
            Utils.creaPannelloErrore("I dati inseriti non sono validi");
        }

    }
}
