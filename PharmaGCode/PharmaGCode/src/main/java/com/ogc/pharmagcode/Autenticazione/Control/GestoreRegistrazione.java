package com.ogc.pharmagcode.Autenticazione.Control;

import com.ogc.pharmagcode.Autenticazione.Interface.ModuloRegistrazione;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.MailUtils;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

public class GestoreRegistrazione {
    private String otp;
    private String nome, cognome, email, password;
    private Stage stage;

    public GestoreRegistrazione() {
        this.stage = new Stage();
        Utils.cambiaInterfaccia("GestioneAccount/Registrazione.fxml", stage, c -> new ModuloRegistrazione(this));
    }

    public void inviaMailOTP(String mail) {
        otp = String.format("%06d", (int) (Math.random() * 1000000));
        MailUtils.inviaMail("Il tuo codice OTP è: " + otp, mail, "Codice OTP");
    }

    public void inserisciOTP(String otp) {
        if (controllaValiditaOTP(otp)) {
            if (DBMSDaemon.queryRegistraUtente(nome, cognome, email, Utils.hash(password))) {
                Utils.creaPannelloConferma("Utente registrato correttamente");
                stage.close();
            }
        } else {
            Utils.creaPannelloErrore("Codice OTP errato");
        }
    }

    public boolean controllaValiditaOTP(String otp) {
        if (this.otp.equals(otp)) {
            Main.log.info("otpCorretto");
            return true;
        }
        return false;
    }

    private boolean controllaValiditaPwd(String password, String re_pwd) {
        boolean isValid = password.length() <= 20 && password.length() >= 8;
        String upperCaseChars = "(.*[A-Z].*)";
        if (!password.matches(upperCaseChars))
            isValid = false;
        String lowerCaseChars = "(.*[a-z].*)";
        if (!password.matches(lowerCaseChars))
            isValid = false;
        String numbers = "(.*[0-9].*)";
        if (!password.matches(numbers))
            isValid = false;
        String rfcCompliantMailPattern = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        if (!email.matches(rfcCompliantMailPattern)){
            isValid = false;
        }
        return isValid && password.equals(re_pwd);
    }

    public boolean registraAccount(String nome, String cognome, String email, String password, String re_pwd) {
        if(nome.isBlank() || cognome.isBlank() || email.isBlank() || password.isBlank()){
            Utils.creaPannelloErrore("Inserisci tutti i dati");
            return false;
        }
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        if (!controllaValiditaPwd(password, re_pwd)) {
            Utils.creaPannelloErrore("I dati inseriti non sono validi");
            return false;
        }
        if (DBMSDaemon.queryVerificaEsistenzaMail(email)) {
            Utils.creaPannelloErrore("Esiste già un account collegato a questa e-mail");
            return false;
        }

        this.password = password;
        inviaMailOTP(email);
        return true;
    }
}
