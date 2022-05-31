package com.ogc.pharmagcode;

import com.ogc.pharmagcode.GestioneFarmaci.AvvisoMancatoCaricamento;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

import java.io.*;
import java.time.Duration;

public class GestoreDiSistema implements Serializable{
    private int giornoUltimaChiamata=0;

    public GestoreDiSistema(){

    }
    public void chiediOrario(){
        int h=Main.orologio.chiediOrario().getHour();

        if(Main.sistema==0){
            if(h==20  && giornoUltimaChiamata!=Main.orologio.chiediOrario().getDayOfMonth()){
                //Se
                Utils.cambiaInterfaccia("GestioneFarmaci/AvvisoMancatoCaricamento.fxml",new Stage(),600,400);
                giornoUltimaChiamata=Main.orologio.chiediOrario().getDayOfMonth();
                serializza();
            }

            if(Main.orologio.confrontaTimer()){
                //Invia segnalazione all'azienda
                System.out.println("Mancato caricamento");
            }
        }
    }

    public void serializza(){
        try {
            FileOutputStream fout=new FileOutputStream("gds.time");
            ObjectOutputStream out=new ObjectOutputStream(fout);
            out.writeObject(this);
        } catch (FileNotFoundException e) {
            System.out.println("File non esistente");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}