package com.ogc.pharmagcode.GestioneAccount;

import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.MailUtils;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;
import org.apache.logging.log4j.util.PropertySource;

import java.util.Random;

public class GestoreRegistrazione {
    private String otp;
    private String nome,cognome,email,password;
    private Stage s;
    public GestoreRegistrazione(Stage stage){
        this.s=stage;
        Utils.cambiaInterfaccia("GestioneAccount/Registrazione.fxml",stage, c->{return new ModuloRegistrazione(this);});
    }
    public void controllaValiditaPassword(){}
    public void controllaMail(String mail){}
    public void inviaMailOTP(String mail){
        if(!DBMSDaemon.queryVerificaEsistenzaMail(mail)) {
            otp=""+(int)(Math.random()*1000000);
            MailUtils.inviaMail("Il tuo codice OTP è: "+otp, mail, "Codice OTP");
        }
    }
    public void inserisciOTP(String otp){
        if(controllaValiditaOTP(otp)) {
            if(DBMSDaemon.queryRegistraUtente(nome,cognome,email,Utils.hash(password))) {
                Utils.creaPannelloConferma("Utente registrato correttamente");
                s.close();
            }
        }else{
            Utils.creaPannelloErrore("Codice OTP errato");
        }
    }
    public boolean controllaValiditaOTP(String otp){
        if(this.otp.equals(otp)){
            Main.log.info("otpCorretto");
            return true;
        }
        return false;
    }
    private boolean controllaValiditaPwd(String password,String re_pwd){
        return password.equals(re_pwd);
    }
    public boolean registraAccount(String nome,String cognome, String email, String password,String re_pwd){
        if(DBMSDaemon.queryVerificaEsistenzaMail(email)){
            Utils.creaPannelloErrore("Esiste già un account collegato a questa e-mail");
            return false;
        }
        if(!controllaValiditaPwd(password,re_pwd)){
            Utils.creaPannelloErrore("Le password non combaciano");
            return false;
        }
        this.nome=nome;
        this.cognome=cognome;
        this.email=email;
        this.password=password;
        inviaMailOTP(email);
        return true;
    }
}
