package com.ogc.pharmagcode;

import javafx.stage.Stage;

import java.util.Random;

public class GestoreRegistrazione {
    private String otp;
    public GestoreRegistrazione(Stage stage){
        Utils.cambiaInterfaccia("GestioneAccount/Registrazione.fxml",stage, c->{return new ModuloRegistrazione(this);});
    }
    public void registraAccount(String nominativo, String password, String rePassword){}
    public void controllaValiditaPassword(){}
    public void controllaMail(String mail){}
    public void inviaMailOTP(String mail){
        Random r=new Random();
        otp=""+r.nextInt(1000000);
        MailUtils.inviaMail("Il tuo codice OTP è: "+otp, mail);
    }
    public void inserisciOTP(){}
    public void controllaValiditaOTP(String otp){
        if(this.otp.equals(otp)){
            System.out.println("otpCorretto");
        }
    }
}
