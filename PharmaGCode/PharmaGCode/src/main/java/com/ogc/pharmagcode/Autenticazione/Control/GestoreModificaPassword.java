package com.ogc.pharmagcode.Autenticazione.Control;

import com.ogc.pharmagcode.Autenticazione.Interface.ModuloModificaPassword;
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

    /**
     * Considera una password valida se:
     * <ul>
     *     <li>È compresa tra 8 e 20 caratteri</li>
     *     <li>Contiene almeno un carattere minuscolo</li>
     *     <li>Contiene almeno un carattere maiuscolo</li>
     *     <li>Contiene almeno un numero</li>
     *     <li>La nuova password e la conferma combaciano</li>
     * </ul>
     *
     * @param new_pwd nuova password
     * @param re_pwd  conferma della password
     * @return true o false se la password rispetta i criteri
     */
    private boolean controllaPassword(String new_pwd, String re_pwd) {
        boolean isValid = new_pwd.length() <= 20 && new_pwd.length() >= 8;
        String upperCaseChars = "(.*[A-Z].*)";
        if (!new_pwd.matches(upperCaseChars)) isValid = false;
        String lowerCaseChars = "(.*[a-z].*)";
        if (!new_pwd.matches(lowerCaseChars)) isValid = false;
        String numbers = "(.*[0-9].*)";
        if (!new_pwd.matches(numbers)) isValid = false;
        return isValid && new_pwd.equals(re_pwd);
    }

    /**
     * Modifica la password se e solo se la nuova password è abbastanza robusta e se la vecchia coincide con quella presente nel DB
     * <p>
     * <ul>
     *     <li>Controlla la validità (robustezza) della password con {@link #controllaPassword(String, String)}</li>
     *     <li>Controlla la correttezza della vecchia password, ed effettua la modifica, con {@link DBMSDaemon#queryModificaPassword(String, String, String)}</li>
     * </ul>
     *
     * @param old_pwd password precedente
     * @param new_pwd password nuova
     * @param re_pwd  conferma della nuova password
     */
    public void modificaPassword(String old_pwd, String new_pwd, String re_pwd) { // magari mi faccio passare lo stage da chiudere
        if (old_pwd.isBlank() || new_pwd.isBlank() || re_pwd.isBlank()) {
            Utils.creaPannelloErrore("Inserisci tutti i dati");
            return;
        }

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
