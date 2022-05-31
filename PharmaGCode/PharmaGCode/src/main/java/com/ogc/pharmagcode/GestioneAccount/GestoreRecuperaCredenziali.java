package com.ogc.pharmagcode.GestioneAccount;

import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.MailUtils;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

public class GestoreRecuperaCredenziali {

    public GestoreRecuperaCredenziali(){
        Utils.cambiaInterfaccia("GestioneAccount/RecuperaCredenziali.fxml",new Stage(),c->{return new ModuloRecuperaCredenziali(this);});
    }
    private String generaPassword(){
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<6;i++){
            sb.append((char) (97+Math.random()*25));
        }
        sb.append((int)(Math.random()*1000));
        return sb.toString();
    }
    public void verificaMail(String email){
        if(DBMSDaemon.verificaEsistenzaMail(email)){
            String new_pwd=generaPassword();
            DBMSDaemon.aggiornaPassword(email,Utils.hash(new_pwd));
            Main.log.info(new_pwd);
            MailUtils.inviaMail("La nuova password è "+new_pwd,email,"Nuova Password PharmaGC");
        }
    }
}
