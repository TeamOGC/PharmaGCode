package com.ogc.pharmagcode.Autenticazione.Control;

import com.ogc.pharmagcode.Autenticazione.Interface.ModuloRecuperaCredenziali;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.MailUtils;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

public class GestoreRecuperaCredenziali {
    private Stage stage;

    public GestoreRecuperaCredenziali() {
        stage = new Stage();
        Utils.cambiaInterfaccia("GestioneAccount/RecuperaCredenziali.fxml", stage, c -> {
            return new ModuloRecuperaCredenziali(this);
        });
    }

    /**
     * @return password generata con caratteri casuali che rispetta i {@link GestoreModificaPassword#controllaPassword(String, String) criteri minimi}
     */
    private String generaPassword() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append((char) (97 + Math.random() * 25));
            sb.append((char) (65 + Math.random() * 25));
        }
        sb.append((int) (Math.random() * 1000) + 1);
        return sb.toString();
    }

    /**
     * Controlla che l'email abbia un utente associato e, se esiste, manda una mail con la sua nuova password, {@link DBMSDaemon#queryAggiornaPassword(String, String) aggiornandola opportunamente} anche sul DB opportuno
     *
     * @param email dell'utente da recuperare
     */
    public void verificaMailEInvia(String email) {
        if (email.isBlank()) {
            Utils.creaPannelloErrore("Inserisci tutti i dati");
            return;
        }
        if (DBMSDaemon.queryVerificaEsistenzaMail(email)) {
            String new_pwd = generaPassword();
            DBMSDaemon.queryAggiornaPassword(email, Utils.hash(new_pwd));
            Main.log.info("Aggiornata la mail di " + email + "con la seguente nuova password: " + new_pwd);
            MailUtils.inviaMail("La tua nuova password è " + new_pwd, email + "  -  Assicurati di cambiarla al più presto!", "Nuova Password PharmaGC");
            Utils.creaPannelloConferma("E' stata inviata una mail con la nuova password all'indirizzo " + email, stage);
        } else {
            Utils.creaPannelloErrore("Non esiste nessun utente associato a questa mail");
        }
    }
}

