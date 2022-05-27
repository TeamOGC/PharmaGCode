package com.ogc.pharmagcode;

import com.ogc.pharmagcode.GestioneFarmaci.AvvisoMancatoCaricamento;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

import java.time.Duration;

public class GestoreDiSistema {
    private int giornoUltimaChiamata=0;
    public void chiediOrario(){
        int h=Main.orologio.chiediOrario().getHour();

        if(Main.sistema==0){
            if(h==20  && giornoUltimaChiamata!=Main.orologio.chiediOrario().getDayOfMonth()){
                //Se
                Utils.cambiaInterfaccia("GestioneFarmaci/AvvisoMancatoCaricamento.fxml",new Stage(),600,400);
                giornoUltimaChiamata=Main.orologio.chiediOrario().getDayOfMonth();
            }

            if(Main.orologio.confrontaTimer()){
                //Invia segnalazione all'azienda
                System.out.println("Mancato caricamento");
            }
        }
    }
}
